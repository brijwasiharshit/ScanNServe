package com.app.ScanNServe.service.impl;

import com.app.ScanNServe.dto.request.UserRequestDTO;
import com.app.ScanNServe.dto.response.UserResponseDTO;
import com.app.ScanNServe.service.IUserService;
import org.springframework.stereotype.Service;

@Service
public class UserService  implements IUserService {

    @Override
    public UserResponseDTO createAdmin(UserRequestDTO requestDTO) {
        return null;
    }
}
