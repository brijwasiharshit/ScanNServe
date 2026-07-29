package com.app.namasteqr.dto.request;

import com.app.namasteqr.utils.enums.FoodType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class FoodItemRequestDTO {

    private Long categoryId;

    private String categoryName;

    @NotBlank(message = "Item name is required")
    private String name;

    @NotNull
    private FoodType foodType;

    @NotBlank(message = "Default image URL is required")
    private String defaultImage;
}
