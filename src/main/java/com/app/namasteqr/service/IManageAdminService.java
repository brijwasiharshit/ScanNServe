package com.app.namasteqr.service;

import com.app.namasteqr.dto.request.UserRequestDTO;
import com.app.namasteqr.dto.response.UserResponseDTO;

public interface IManageAdminService {
    UserResponseDTO createSuperAdmin(UserRequestDTO userRequestDTO);
}
