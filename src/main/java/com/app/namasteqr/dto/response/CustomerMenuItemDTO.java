package com.app.namasteqr.dto.response;

import com.app.namasteqr.utils.enums.FoodType;
import lombok.*;

import java.math.BigDecimal;
import com.app.namasteqr.utils.enums.ItemTag;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerMenuItemDTO {

    private Long itemId;

    private Long categoryId;

    private String categoryName;

    private String name;

    private FoodType foodType;

    private BigDecimal price;

    private String image;

    private ItemTag tag;
}
