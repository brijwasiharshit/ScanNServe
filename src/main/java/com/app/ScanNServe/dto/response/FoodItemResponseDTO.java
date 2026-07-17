package com.app.ScanNServe.dto.response;

import com.app.ScanNServe.utils.enums.FoodType;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FoodItemResponseDTO {

    private Long itemId;

    private Long categoryId;

    private String categoryName;

    private String name;

    private FoodType foodType;

    private String defaultImage;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}