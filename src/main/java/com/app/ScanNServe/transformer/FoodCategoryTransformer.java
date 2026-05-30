package com.app.ScanNServe.transformer;

import com.app.ScanNServe.domain.entity.FoodCategoryEntity;
import com.app.ScanNServe.dto.request.FoodCategoryRequestDTO;
import com.app.ScanNServe.dto.response.FoodCategoryResponseDTO;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class FoodCategoryTransformer {

    public FoodCategoryEntity toEntity(FoodCategoryRequestDTO d) {
        FoodCategoryEntity e = new FoodCategoryEntity();
        e.setName(d.getName().trim());
        return e;
    }

    public FoodCategoryResponseDTO toDto(FoodCategoryEntity e) {
        FoodCategoryResponseDTO d = new FoodCategoryResponseDTO();
        d.setId(e.getId());
        d.setName(e.getName());
        return d;
    }
}
