package com.app.ScanNServe.dto.response;

import lombok.*;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Component
public class RestaurantDTO {

    private String name;

    private String logo;

    private String theme;

    private String description;

    private String address;

    private String phoneNumber;
}