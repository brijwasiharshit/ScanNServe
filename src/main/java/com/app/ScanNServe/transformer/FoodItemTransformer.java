package com.app.ScanNServe.transformer;

import com.app.ScanNServe.domain.entity.FoodCategoryEntity;
import com.app.ScanNServe.domain.entity.FoodItemEntity;
import com.app.ScanNServe.dto.request.FoodItemRequestDTO;
import com.app.ScanNServe.dto.response.FoodItemResponseDTO;
import com.app.ScanNServe.dto.response.ItemSearchResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class FoodItemTransformer {
    public FoodItemEntity toEntity(
            FoodItemRequestDTO dto,
            FoodCategoryEntity category,
            String normalizedName
    ) {

        if (dto == null) {
            return null;
        }

        return FoodItemEntity.builder()
                .category(category)
                .name(normalizedName)
                .foodType(dto.getFoodType())
                .defaultImage(dto.getDefaultImage())
                .build();
    }

    public FoodItemResponseDTO toDto(
            FoodItemEntity entity
    ) {

        if (entity == null) {
            return null;
        }

        return FoodItemResponseDTO.builder()
                .itemId(entity.getItemId())
                .categoryId(entity.getCategory().getCategoryId())
                .categoryName(entity.getCategory().getName())
                .name(entity.getName())
                .foodType(entity.getFoodType())
                .defaultImage(entity.getDefaultImage())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
    public ItemSearchResponseDTO toSearchDto(
            FoodItemEntity entity
    ) {

        if (entity == null) {
            return null;
        }

        return ItemSearchResponseDTO.builder()
                .itemId(entity.getItemId())
                .itemName(entity.getName())
                .foodType(entity.getFoodType())
                .defaultImage(entity.getDefaultImage())
                .categoryId(entity.getCategory().getCategoryId())
                .categoryName(entity.getCategory().getName())
                .build();
    }
}
