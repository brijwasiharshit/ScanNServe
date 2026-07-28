package com.app.namasteqr.transformer;

import com.app.namasteqr.domain.entity.FoodItemEntity;
import com.app.namasteqr.domain.entity.RestaurantEntity;
import com.app.namasteqr.domain.entity.RestaurantMenuItemEntity;
import com.app.namasteqr.dto.request.RestaurantMenuItemRequestDTO;
import com.app.namasteqr.dto.request.UpdateRestaurantMenuItemRequestDTO;
import com.app.namasteqr.dto.response.RestaurantMenuItemResponseDTO;
import org.springframework.stereotype.Component;
import java.util.ArrayList;

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
                .tag(dto.getTag())
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
                .tag(entity.getTag())
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

        if (dto.getTag() != null) {
            entity.setTag(dto.getTag());
        }
    }

    public void softDelete(
            RestaurantMenuItemEntity entity
    ) {

        entity.setIsDeleted(true);
    }
}
