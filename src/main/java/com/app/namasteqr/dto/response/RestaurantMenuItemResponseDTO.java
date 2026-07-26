package com.app.namasteqr.dto.response;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RestaurantMenuItemResponseDTO {

    private Long restaurantMenuItemId;

    private Long restaurantId;

    private Long itemId;

    private String itemName;

    private Long categoryId;

    private String categoryName;

    private BigDecimal price;

    private String image;

    private Boolean available;

    private LocalDateTime createdAt;
}
