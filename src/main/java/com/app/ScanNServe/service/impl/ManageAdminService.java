package com.app.ScanNServe.service.impl;

import com.app.ScanNServe.domain.entity.UserEntity;
import com.app.ScanNServe.domain.repository.IUserRespository;
import com.app.ScanNServe.dto.request.UserRequestDTO;
import com.app.ScanNServe.dto.response.UserResponseDTO;
import com.app.ScanNServe.service.IManageAdminService;
import com.app.ScanNServe.utils.validations.ValidateUser;
import com.app.ScanNServe.transformer.UserTransformer;
import lombok.Data;
import org.springframework.stereotype.Service;

@Service
@Data
public class ManageAdminService implements IManageAdminService {

    private final IUserRespository userRespository;

    private final UserTransformer userTx;

    @Override
    public UserResponseDTO createSuperAdmin(UserRequestDTO userRequestDTO) {
        var name=  userRequestDTO.getName();
        var email = userRequestDTO.getEmailAddress();
        var password = userRequestDTO.getPassword();
        var confirmPassword = userRequestDTO.getConfirmPassword();
        var address = userRequestDTO.getAddress();
        var contactNumber = userRequestDTO.getContactNumber();
        String role = String.valueOf(userRequestDTO.getRole());
        //validate if details entered are valid
        ValidateUser.validate(email,password,confirmPassword,name,address,contactNumber);
        //validate if super-admin doesn't already exists
        UserEntity e = userRespository.findByRole(role);
      return null;
        //generate hashedPassword

    }
}
