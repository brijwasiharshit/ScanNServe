package com.app.namasteqr.service;

import com.app.namasteqr.dto.request.AdminRequestDTO;
import com.app.namasteqr.dto.request.UserRequestDTO;
import com.app.namasteqr.dto.response.AdminResponseDTO;
import com.app.namasteqr.dto.response.UserResponseDTO;
import org.springframework.stereotype.Service;


public interface IUserService {

    AdminResponseDTO createAdmin(AdminRequestDTO requestDTO);
    java.util.List<AdminResponseDTO> getAllAdmins();
}
