package com.app.ScanNServe.service.impl;

import com.app.ScanNServe.domain.entity.FoodCategoryEntity;
import com.app.ScanNServe.domain.repository.IFoodCategoryRepository;
import com.app.ScanNServe.dto.request.FoodCategoryRequestDTO;
import com.app.ScanNServe.dto.response.FoodCategoryResponseDTO;
import com.app.ScanNServe.service.IFoodCategoryService;
import com.app.ScanNServe.transformer.FoodCategoryTransformer;
import com.app.ScanNServe.utils.validations.ValidateFoodCategory;
import lombok.Data;
import org.springframework.stereotype.Service;

@Service
@Data
public class FoodCategoryService implements IFoodCategoryService {

    private final IFoodCategoryRepository foodCategoryRepository;
    private final FoodCategoryTransformer foodCategoryTransformer;

    @Override
    public FoodCategoryResponseDTO createCategory(FoodCategoryRequestDTO foodCategoryRequestDTO) {
        ValidateFoodCategory.validateCategoryName(foodCategoryRequestDTO.getName());

        String normalizedName = foodCategoryRequestDTO.getName().trim();
        if (foodCategoryRepository.existsByName(normalizedName)) {
            throw new IllegalArgumentException("Food category with this name already exists");
        }

        foodCategoryRequestDTO.setName(normalizedName);
        FoodCategoryEntity entity = foodCategoryTransformer.toEntity(foodCategoryRequestDTO);
        FoodCategoryEntity saved = foodCategoryRepository.save(entity);

        return foodCategoryTransformer.toDto(saved);
    }
}
