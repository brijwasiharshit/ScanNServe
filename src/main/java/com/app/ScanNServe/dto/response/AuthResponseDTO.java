package com.app.ScanNServe.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Data
@AllArgsConstructor
public class AuthResponseDTO {
    private String accessToken;
    private String role;
    private Long userId;
}
