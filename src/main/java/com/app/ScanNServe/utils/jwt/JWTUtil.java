package com.app.ScanNServe.utils.jwt;

import com.app.ScanNServe.domain.entity.RefreshToken;
import com.app.ScanNServe.domain.entity.UserEntity;
import com.app.ScanNServe.domain.repository.IRefreshTokenRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;


@Component
@AllArgsConstructor
public class JWTUtil {
   private final IRefreshTokenRepository refreshTokenRepository;
    private static final String SECRET_KEY = "my-super-secure-secret-key-my-super-secure-secret-key";
    private static final long EXPIRATION_TIME = 1000 * 60 * 60*3;
    private static final long ACCESS_TOKEN_EXPIRATION_TIME = 1000* 60 * 15;
    private static final long REFRESH_TOKEN_EXPIRATION_DAYS_30 = 30;
    private static final long REFRESH_TOKEN_EXPIRATION_DAYS_1 = 1;


    private Key getSignKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    public String generateAccessToken(UserEntity user) {
        return Jwts.builder()
                .setSubject(user.getEmailAddress())
                .claim("userId", user.getId())
                .claim("username", user.getUsername())
                .claim("role", user.getRole().name())
                .claim(
                        "restaurantId",
                        user.getRestaurant() != null
                                ? user.getRestaurant().getRestaurantId()
                                : null
                )
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_EXPIRATION_TIME))
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }
    public RefreshToken generateRefreshToken(
            UserEntity user,
            Boolean rememberMe
    ) {
        RefreshToken token = new RefreshToken();
        token.setToken(UUID.randomUUID().toString());
        token.setUser(user);
        token.setExpiryDate(LocalDateTime.now().plusDays(Boolean.TRUE.equals(rememberMe) ? REFRESH_TOKEN_EXPIRATION_DAYS_30 : REFRESH_TOKEN_EXPIRATION_DAYS_1));

        return refreshTokenRepository.save(token);
    }

    // 🔹 Extract Username - email
    public String extractEmail(String token) {
        return extractClaims(token).getSubject();
    }

    // 🔹 Extract All Claims
    public Claims extractClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // 🔹 Validate Token
    public boolean validateToken(String token, String email) {
        final String extractedEmail = extractEmail(token);
        return extractedEmail.equals(email)
                && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractClaims(token).getExpiration().before(new Date());
    }

    public String extractRole(String token) {
        return extractClaims(token).get("role", String.class);
    }
    public Long extractUserId(String token) {
        return extractClaims(token).get("userId", Long.class);
    }
}
