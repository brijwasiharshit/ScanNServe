package com.app.ScanNServe.dto.request;

import com.app.ScanNServe.utils.enums.FoodType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class FoodItemRequestDTO {

    @NotNull(message = "Category Id is required")
    private Long categoryId;

    @NotBlank(message = "Item name is required")
    private String name;

    @NotNull
    private FoodType foodType;

    @NotBlank(message = "Default image URL is required")
    private String defaultImage;
}