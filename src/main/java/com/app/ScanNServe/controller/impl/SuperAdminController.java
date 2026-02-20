package com.app.ScanNServe.controller.impl;

import com.app.ScanNServe.controller.ISuperAdminController;
import com.app.ScanNServe.dto.request.UserRequestDTO;
import com.app.ScanNServe.dto.response.UserResponseDTO;
import com.app.ScanNServe.service.IManageAdminService;
import com.app.ScanNServe.utils.api.StandardResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/super")
@AllArgsConstructor
public class SuperAdminController implements ISuperAdminController {

    private final IManageAdminService manageAdminService;
    public ResponseEntity<StandardResponse<UserResponseDTO>> createSuperAdmin(UserRequestDTO userRequestDTO) {
        UserResponseDTO userResponseDTO = manageAdminService.createSuperAdmin(userRequestDTO);
        StandardResponse<UserResponseDTO> response =
                StandardResponse.<UserResponseDTO>builder()
                        .data(userResponseDTO)
                        .success(true)
                        .message("User Created Successfully!")
                        .httpStatus(HttpStatus.CREATED)
                        .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/getAdmin")
    @Override
    public String getAllAdmins() {
        System.out.println("Heyy");
        return "Hello!";
    }

    @Override
    public ResponseEntity<StandardResponse<UserResponseDTO>> getAdminByPropertyName(String name) {
        return null;
    }

    @Override
    public ResponseEntity<StandardResponse<UserResponseDTO>> getAdminByEmail(String email) {
        return null;
    }

    @Override
    public ResponseEntity<StandardResponse<UserResponseDTO>> createAdmin(UserRequestDTO requestDto) {
        return null;
    }

    @Override
    public ResponseEntity<StandardResponse<UserResponseDTO>> updateAdmin(Long id, UserRequestDTO requestDto) {
        return null;
    }

    @Override
    public ResponseEntity<StandardResponse<Void>> deleteAdmin(Long id) {
        return null;
    }
}
