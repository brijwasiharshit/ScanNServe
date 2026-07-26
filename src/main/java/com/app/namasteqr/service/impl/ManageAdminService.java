package com.app.namasteqr.service.impl;

import com.app.namasteqr.domain.entity.UserEntity;
import com.app.namasteqr.domain.repository.IUserRespository;
import com.app.namasteqr.dto.request.UserRequestDTO;
import com.app.namasteqr.dto.response.UserResponseDTO;
import com.app.namasteqr.service.IManageAdminService;
import com.app.namasteqr.utils.validations.ValidateUser;
//import com.app.namasteqr.transformer.UserTransformer;
import lombok.Data;
import org.springframework.stereotype.Service;

@Service
@Data
public class ManageAdminService implements IManageAdminService {

    private final IUserRespository userRespository;

//    private final UserTransformer userTx;

    @Override
    public UserResponseDTO createSuperAdmin(UserRequestDTO userRequestDTO) {
        var username = userRequestDTO.getUsername();
        var email = userRequestDTO.getEmailAddress();
        var password = userRequestDTO.getPassword();
        var confirmPassword = userRequestDTO.getConfirmPassword();
        var address = userRequestDTO.getAddress();
        var contactNumber = userRequestDTO.getContactNumber();
        String role = String.valueOf(userRequestDTO.getRole());
        //validate if details entered are valid
        ValidateUser.validate(email, password, confirmPassword, username, role, contactNumber);
        //validate if super-admin doesn't already exists
        userRespository.findByUsernameAndEmailAddress(username, email)
                .ifPresent(u -> { throw new IllegalArgumentException("User already exists with this username and email"); });
        UserEntity e = userRespository.findFirstByRole(com.app.namasteqr.utils.enums.Role.valueOf(role)).orElse(null);
      return null;
        //generate hashedPassword

    }
}
