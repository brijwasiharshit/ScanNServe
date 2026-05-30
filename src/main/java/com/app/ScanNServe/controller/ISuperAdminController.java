package com.app.ScanNServe.controller;

import com.app.ScanNServe.dto.request.PropertyRequestDTO;
import com.app.ScanNServe.dto.request.UserRequestDTO;
import com.app.ScanNServe.dto.response.PropertyResponseDTO;
import com.app.ScanNServe.dto.response.UserResponseDTO;
import com.app.ScanNServe.utils.api.StandardResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


public interface ISuperAdminController {


    String getAllAdmins();

    ResponseEntity<StandardResponse<UserResponseDTO>> getAdminByPropertyName(
            @PathVariable("name") String name
    );
    ResponseEntity<StandardResponse<UserResponseDTO>> getAdminByEmail(
            @PathVariable String email
    );

    ResponseEntity<StandardResponse<UserResponseDTO>> createAdmin(
            @RequestBody UserRequestDTO requestDto
    );
    ResponseEntity<StandardResponse<UserResponseDTO>> updateAdmin(
            @PathVariable Long id,
            @RequestBody UserRequestDTO requestDto
    );
    ResponseEntity<StandardResponse<Void>> deleteAdmin(
            @PathVariable Long id
    );
    ResponseEntity<StandardResponse<PropertyResponseDTO>> createProperty(
            @RequestBody PropertyRequestDTO propertyRequestDTO
    );
}
