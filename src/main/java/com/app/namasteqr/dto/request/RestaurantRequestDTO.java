package com.app.namasteqr.dto.request;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantRequestDTO {

    @NotBlank(message = "Restaurant name is required")
    private String name;

    @NotBlank(message = "Logo URL is required")
    private String logo;

    @NotBlank(message = "Theme color is required")
    private String theme;

    @NotBlank(message = "Address is required")
    private String address;

    private String description;

    @NotBlank(message = "Phone number is required")
    @Pattern(
            regexp = "^[+]?[0-9]{10,15}$",
            message = "Invalid phone number"
    )
    private String phoneNumber;

    @NotNull(message = "Subscription expiry is required")
    @Future(message = "Subscription expiry must be in the future")
    private LocalDateTime subscriptionExpiry;

}
