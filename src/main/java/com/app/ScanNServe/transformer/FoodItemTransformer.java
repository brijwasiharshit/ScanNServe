package com.app.ScanNServe.transformer;

import com.app.ScanNServe.domain.entity.FoodCategoryEntity;
import com.app.ScanNServe.domain.entity.FoodItemEntity;
import com.app.ScanNServe.dto.request.FoodItemRequestDTO;
import com.app.ScanNServe.dto.response.FoodItemResponseDTO;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class FoodItemTransformer {

    public FoodItemEntity toEntity(FoodItemRequestDTO d, FoodCategoryEntity category) {
        FoodItemEntity e = new FoodItemEntity();
        e.setName(d.getName());
        e.setImgLink(d.getImgLink());
        e.setIsVeg(d.getIsVeg());
        e.setFoodCategory(category);
        return e;
    }

    public FoodItemResponseDTO toDto(FoodItemEntity e) {
        FoodItemResponseDTO d = new FoodItemResponseDTO();
        d.setId(e.getId());
        d.setImgLink(e.getImgLink());
        d.setIsVeg(e.getIsVeg());
        d.setCategoryName(e.getFoodCategory().getName());
        return d;
    }
}

