package com.app.ScanNServe.dto.response;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdminResponseDTO {

    private Long userId;
    private String username;
    private String emailAddress;
    private String contactNumber;
    private String address;
    private Long restaurantId;
    private LocalDateTime createdAt;
}