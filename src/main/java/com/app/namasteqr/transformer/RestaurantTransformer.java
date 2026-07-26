package com.app.namasteqr.transformer;

import com.app.namasteqr.domain.entity.RestaurantEntity;
import com.app.namasteqr.dto.request.RestaurantRequestDTO;
import com.app.namasteqr.dto.response.RestaurantResponseDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class RestaurantTransformer {

    public RestaurantEntity toEntity(
            RestaurantRequestDTO dto,
            String normalizedName
    ) {

        if (dto == null) {
            return null;
        }

        return RestaurantEntity.builder()
                .name(normalizedName)
                .logo(dto.getLogo().trim())
                .themeColor(dto.getTheme().trim())
                .address(dto.getAddress().trim())
                .description(dto.getDescription())
                .phoneNumber(dto.getPhoneNumber().trim())
                .subscriptionExpiry(dto.getSubscriptionExpiry())
                .build();
    }
    public RestaurantResponseDTO toDto(
            RestaurantEntity entity
    ) {

        if (entity == null) {
            return null;
        }

        return RestaurantResponseDTO.builder()
                .restaurantId(entity.getRestaurantId())
                .name(entity.getName())
                .logo(entity.getLogo())
                .theme(entity.getThemeColor())
                .address(entity.getAddress())
                .description(entity.getDescription())
                .phoneNumber(entity.getPhoneNumber())
                .subscriptionExpiry(entity.getSubscriptionExpiry())
                .isActive(entity.getIsActive())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
}
