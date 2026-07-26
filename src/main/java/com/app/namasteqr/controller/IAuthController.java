package com.app.namasteqr.controller;

import com.app.namasteqr.domain.entity.AuthRequest;
import com.app.namasteqr.dto.response.AuthResponseDTO;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestBody;

public interface IAuthController {

    AuthResponseDTO generateToken(@RequestBody AuthRequest authRequest, HttpServletResponse response);
    public AuthResponseDTO refreshToken(@CookieValue(value = "refreshToken", required = false) String refreshToken);
    public ResponseEntity<String> logout(@CookieValue(value = "refreshToken", required = false) String refreshToken, HttpServletResponse response);
}

