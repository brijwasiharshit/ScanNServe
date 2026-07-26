package com.app.namasteqr.controller.impl;

import com.app.namasteqr.controller.IAuthController;
import com.app.namasteqr.domain.entity.AuthRequest;
import com.app.namasteqr.domain.entity.RefreshToken;
import com.app.namasteqr.domain.entity.UserEntity;
import com.app.namasteqr.domain.repository.IUserRespository;
import com.app.namasteqr.dto.response.AuthResponseDTO;
import com.app.namasteqr.service.RefreshTokenService;
import com.app.namasteqr.utils.jwt.JWTUtil;
import com.app.namasteqr.utils.jwt.UserPrincipal;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Data;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1/super")
@Data
public class AuthController implements IAuthController {
    private final AuthenticationManager authenticationManager;
    private final JWTUtil jwtUtil;
    private final IUserRespository userRespository;
    private final RefreshTokenService refreshTokenService;
    @PostMapping("/authenticate")
    @Override
    public AuthResponseDTO generateToken(@RequestBody AuthRequest authRequest, HttpServletResponse response){
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authRequest.getUserName(),
                            authRequest.getPassword()
                    )
            );

            UserEntity user = userRespository
                    .findByEmailAddress(authRequest.getUserName())
                    .orElseThrow(() -> new UsernameNotFoundException("Invalid User"));

            UserPrincipal userPrincipal = UserPrincipal.fromEntity(user);
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(
                            userPrincipal,
                            null,
                            userPrincipal.getAuthorities()
                    );
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);

            String accessToken = jwtUtil.generateAccessToken(user);
            RefreshToken refreshToken = jwtUtil.generateRefreshToken(user,authRequest.getRememberMe());
        Duration duration = Duration.between(LocalDateTime.now(), refreshToken.getExpiryDate());
            ResponseCookie cookie = ResponseCookie.from("refreshToken", refreshToken.getToken()).
                    httpOnly(true).secure(false).sameSite("Lax")
                    .path("/").maxAge(duration).build();

            response.addHeader(
                    HttpHeaders.SET_COOKIE,
                    cookie.toString()
            );
            return new AuthResponseDTO(
                    accessToken,
                    user.getRole().name(),
                    user.getId()
            );
    }

    @Override
    @PostMapping("/refresh-token")
    public AuthResponseDTO refreshToken(
            @CookieValue(value = "refreshToken", required = false) String refreshToken
    ) {

        if (refreshToken == null) {
            throw new RuntimeException("Refresh token missing");
        }

        RefreshToken token = refreshTokenService.verifyRefreshToken(refreshToken);

        UserEntity user = token.getUser();

        String accessToken = jwtUtil.generateAccessToken(user);

        return new AuthResponseDTO(
                accessToken,
                user.getRole().name(),
                user.getId()
        );
    }

    @Override
    @PostMapping("/logout")
    public ResponseEntity<String> logout(
            @CookieValue(value = "refreshToken", required = false) String refreshToken,
            HttpServletResponse response
    ) {

        if (refreshToken != null) {
            refreshTokenService.deleteByToken(refreshToken);
        }

        SecurityContextHolder.clearContext();

        ResponseCookie cookie = ResponseCookie
                .from("refreshToken", "")
                .httpOnly(true)
                .secure(false)
                .sameSite("Lax")
                .path("/")
                .maxAge(0)
                .build();

        response.addHeader(
                HttpHeaders.SET_COOKIE,
                cookie.toString()
        );

        return ResponseEntity.ok("Logged out successfully");
    }
}
