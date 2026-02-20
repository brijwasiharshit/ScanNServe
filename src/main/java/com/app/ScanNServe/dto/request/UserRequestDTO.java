package com.app.ScanNServe.dto.request;
import com.app.ScanNServe.utils.enums.Role;
import lombok.Data;

@Data
public class UserRequestDTO {
    private String name;

    private Role role;

    private Long propertyIdFk;

    private String address;

    private String emailAddress;

    private String contactNumber;

    private String password;

    private String confirmPassword;
}
