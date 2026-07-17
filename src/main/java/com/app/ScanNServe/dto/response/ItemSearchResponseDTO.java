package com.app.ScanNServe.dto.response;

import com.app.ScanNServe.utils.enums.FoodType;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemSearchResponseDTO {

    private Long itemId;

    private String itemName;

    private Long categoryId;

    private String categoryName;

    private FoodType foodType;

    private String defaultImage;
}