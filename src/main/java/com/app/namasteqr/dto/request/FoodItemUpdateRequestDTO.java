package com.app.namasteqr.dto.request;

import com.app.namasteqr.utils.enums.FoodType;
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
