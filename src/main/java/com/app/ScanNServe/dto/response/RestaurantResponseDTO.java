package com.app.ScanNServe.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantResponseDTO {

    private Long restaurantId;

    private String name;

    private String logo;

    private String theme;

    private String address;

    private String description;

    private String phoneNumber;

    private LocalDateTime subscriptionExpiry;

    private Boolean isActive;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}