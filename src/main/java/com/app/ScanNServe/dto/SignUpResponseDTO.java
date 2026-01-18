package com.app.ScanNServe.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Data
public class SignUpResponseDTO {
    private UUID id;
    private String firstName;
    private String lastName;
    private String email;
    private LocalDateTime timestamp;
}
