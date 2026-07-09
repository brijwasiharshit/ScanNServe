package com.app.ScanNServe.controller;

import com.app.ScanNServe.domain.entity.AuthRequest;
import com.app.ScanNServe.dto.response.AuthResponseDTO;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestBody;

public interface IAuthController {

    AuthResponseDTO generateToken(@RequestBody AuthRequest authRequest, HttpServletResponse response);
    public AuthResponseDTO refreshToken(@CookieValue(value = "refreshToken", required = false) String refreshToken);
    public ResponseEntity<String> logout(@CookieValue(value = "refreshToken", required = false) String refreshToken, HttpServletResponse response);
}

