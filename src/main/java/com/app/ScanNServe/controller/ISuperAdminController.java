package com.app.ScanNServe.controller;

import com.app.ScanNServe.dto.SignUpRequestDTO;
import com.app.ScanNServe.dto.SignUpResponseDTO;
import com.app.ScanNServe.utils.api.StandardResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

public interface ISuperAdminController {
    ResponseEntity<StandardResponse<SignUpResponseDTO>> createSadmin( @RequestBody SignUpRequestDTO signUpRequestDTO);

}
