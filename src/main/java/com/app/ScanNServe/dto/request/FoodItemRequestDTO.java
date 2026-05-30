package com.app.ScanNServe.dto.request;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class FoodItemRequestDTO {
    private String name;
    private String imgLink;
    private Boolean isVeg;
    private Long foodCategoryIdFk;
}

