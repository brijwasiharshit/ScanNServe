package com.app.ScanNServe.service.impl;

import com.app.ScanNServe.domain.entity.FoodCategoryEntity;
import com.app.ScanNServe.domain.entity.FoodItemEntity;
import com.app.ScanNServe.domain.repository.IFoodCategoryRepository;
import com.app.ScanNServe.domain.repository.IFoodItemRepository;
import com.app.ScanNServe.dto.request.FoodItemRequestDTO;
import com.app.ScanNServe.dto.response.FoodItemResponseDTO;
import com.app.ScanNServe.service.IFoodItemService;
import com.app.ScanNServe.transformer.FoodItemTransformer;
import com.app.ScanNServe.utils.validations.ValidateProperty;
import lombok.Data;
import org.springframework.stereotype.Service;

@Service
@Data
public class FoodItemService implements IFoodItemService {

    private final IFoodItemRepository foodItemRepository;
    private final IFoodCategoryRepository foodCategoryRepository;
    private final FoodItemTransformer foodItemTransformer;

    @Override
    public FoodItemResponseDTO addFoodItemByCategory(FoodItemRequestDTO foodItemRequestDTO) {

        if (foodItemRequestDTO.getImgLink() != null) {
            ValidateProperty.validateLink(foodItemRequestDTO.getImgLink(), "Food item img_link");
        }
        // add validation for name of the food Item also.
        Long categoryId = foodItemRequestDTO.getFoodCategoryIdFk();

        FoodCategoryEntity category = foodCategoryRepository.findById(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("Food category not found with id: " + categoryId));

        FoodItemEntity entity = foodItemTransformer.toEntity(foodItemRequestDTO, category);

        FoodItemEntity saved = foodItemRepository.save(entity);

        return foodItemTransformer.toDto(saved);
    }
}

