package com.app.ScanNServe.controller;

import com.app.ScanNServe.domain.entity.AuthRequest;
import org.springframework.web.bind.annotation.RequestBody;

public interface IAuthController {

    String generateToken(@RequestBody AuthRequest authRequest);
}

