package com.app.namasteqr.dto.request;

import jakarta.validation.constraints.Positive;
import lombok.*;

import java.math.BigDecimal;
import com.app.namasteqr.utils.enums.ItemTag;

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

    private ItemTag tag;
}
