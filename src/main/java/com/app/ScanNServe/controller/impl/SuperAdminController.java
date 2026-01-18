package com.app.ScanNServe.controller.impl;

import com.app.ScanNServe.controller.ISuperAdminController;
import com.app.ScanNServe.dto.SignUpRequestDTO;
import com.app.ScanNServe.dto.SignUpResponseDTO;
import com.app.ScanNServe.service.ISignUpService;
import com.app.ScanNServe.utils.api.StandardResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/super")
@AllArgsConstructor
public class SuperAdminController implements ISuperAdminController {
    final ISignUpService signUpService;


    @PostMapping("/create")
    @Override
    public ResponseEntity<StandardResponse<SignUpResponseDTO>> createSadmin(
            @RequestBody SignUpRequestDTO signUpRequestDTO
    ) {
        SignUpResponseDTO res = signUpService.signUp(signUpRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                StandardResponse.<SignUpResponseDTO>builder().data(res).
                        success(true).message("Super-Admin created Successfully").
                        httpStatus(HttpStatus.CREATED).build());
    }
}
