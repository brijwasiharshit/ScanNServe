package com.app.namasteqr.dto.request;
import com.app.namasteqr.utils.enums.Role;
import lombok.Data;

@Data
public class UserRequestDTO {
    private String username;

    private Role role;

    private Long propertyIdFk;

    private String address;

    private String emailAddress;

    private String contactNumber;

    private String password;

    private String confirmPassword;
}
