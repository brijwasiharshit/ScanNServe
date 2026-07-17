package com.app.ScanNServe.dto.request;

import com.app.ScanNServe.utils.enums.FoodType;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FoodItemUpdateRequestDTO {

    private String name;

    private FoodType foodType;

    private String defaultImage;

}