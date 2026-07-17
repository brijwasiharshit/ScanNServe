package com.app.ScanNServe.service;

import com.app.ScanNServe.dto.request.AdminRequestDTO;
import com.app.ScanNServe.dto.request.UserRequestDTO;
import com.app.ScanNServe.dto.response.AdminResponseDTO;
import com.app.ScanNServe.dto.response.UserResponseDTO;
import org.springframework.stereotype.Service;


public interface IUserService {

    AdminResponseDTO createAdmin(AdminRequestDTO requestDTO);

}
