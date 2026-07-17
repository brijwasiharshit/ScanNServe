package com.app.ScanNServe.transformer;

import com.app.ScanNServe.domain.entity.FoodItemEntity;
import com.app.ScanNServe.domain.entity.RestaurantEntity;
import com.app.ScanNServe.domain.entity.RestaurantMenuItemEntity;
import com.app.ScanNServe.dto.request.RestaurantMenuItemRequestDTO;
import com.app.ScanNServe.dto.request.UpdateRestaurantMenuItemRequestDTO;
import com.app.ScanNServe.dto.response.RestaurantMenuItemResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class RestaurantMenuTransformer {
    public RestaurantMenuItemEntity toEntity(
            RestaurantMenuItemRequestDTO dto,
            RestaurantEntity restaurant,
            FoodItemEntity foodItem
    ) {

        return RestaurantMenuItemEntity.builder()
                .restaurant(restaurant)
                .foodItem(foodItem)
                .price(dto.getPrice())
                .customImage(dto.getCustomImage())
                .available(dto.getAvailable())
                .build();
    }
    public RestaurantMenuItemResponseDTO toDto(
            RestaurantMenuItemEntity entity
    ) {

        return RestaurantMenuItemResponseDTO.builder()
                .restaurantMenuItemId(entity.getRestaurantMenuItemId())
                .restaurantId(entity.getRestaurant().getRestaurantId())
                .itemId(entity.getFoodItem().getItemId())
                .itemName(entity.getFoodItem().getName())
                .categoryId(entity.getFoodItem().getCategory().getCategoryId())
                .categoryName(entity.getFoodItem().getCategory().getName())
                .price(entity.getPrice())
                .image(
                        entity.getCustomImage() != null
                                ? entity.getCustomImage()
                                : entity.getFoodItem().getDefaultImage()
                )
                .available(entity.getAvailable())
                .createdAt(entity.getCreatedAt())
                .build();
    }

    public void updateEntity(
            RestaurantMenuItemEntity entity,
            UpdateRestaurantMenuItemRequestDTO dto
    ) {

        if (dto.getPrice() != null) {
            entity.setPrice(dto.getPrice());
        }

        if (dto.getCustomImage() != null) {
            entity.setCustomImage(dto.getCustomImage().trim());
        }

        if (dto.getAvailable() != null) {
            entity.setAvailable(dto.getAvailable());
        }
    }

    public void softDelete(
            RestaurantMenuItemEntity entity
    ) {

        entity.setIsDeleted(true);
    }
}
