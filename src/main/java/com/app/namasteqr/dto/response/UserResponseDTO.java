package com.app.namasteqr.dto.response;

import com.app.namasteqr.utils.enums.Role;
import lombok.Data;

@Data
public class UserResponseDTO {
    private String username;

    private Role role;

    private Long propertyIdFk;

    private String address;

    private String emailAddress;

    private String contactNumber;
}
