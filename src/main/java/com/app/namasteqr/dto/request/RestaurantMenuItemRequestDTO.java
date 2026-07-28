package com.app.namasteqr.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.math.BigDecimal;
import com.app.namasteqr.utils.enums.ItemTag;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RestaurantMenuItemRequestDTO {

    @NotNull(message = "Item Id is required")
    private Long itemId;

    @NotNull(message = "Price is required")
    @Positive(message = "Price must be greater than zero")
    private BigDecimal price;

    private String customImage;

    @Builder.Default
    private Boolean available = true;

    private ItemTag tag;
}
