package com.app.ScanNServe.dto.request;

import jakarta.validation.constraints.Positive;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateRestaurantMenuItemRequestDTO {

    @Positive(message = "Price must be greater than zero")
    private BigDecimal price;

    private String customImage;

    private Boolean available;
}