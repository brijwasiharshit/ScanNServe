package com.app.ScanNServe.service;

import com.app.ScanNServe.dto.request.UserRequestDTO;
import com.app.ScanNServe.dto.response.UserResponseDTO;
import org.springframework.stereotype.Service;


public interface IUserService {

    UserResponseDTO createAdmin(UserRequestDTO requestDTO);

}
