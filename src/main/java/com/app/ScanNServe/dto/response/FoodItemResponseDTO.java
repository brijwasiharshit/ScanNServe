package com.app.ScanNServe.dto.response;

import lombok.Data;

@Data
public class FoodItemResponseDTO {
    private Long id;
    private String name;
    private String imgLink;
    private Boolean isVeg;
    private String categoryName;
}

