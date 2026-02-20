package com.app.ScanNServe.service;

import com.app.ScanNServe.dto.request.UserRequestDTO;
import com.app.ScanNServe.dto.response.UserResponseDTO;

public interface IManageAdminService {
    UserResponseDTO createSuperAdmin(UserRequestDTO userRequestDTO);
}
