package com.app.ScanNServe.dto.response;

import com.app.ScanNServe.utils.enums.Role;
import lombok.Data;

@Data
public class UserResponseDTO {
    private Long id;

    private String name;

    private Role role;

    private Long propertyIdFk;

    private String address;

    private String emailAddress;

    private String contactNumber;
}
