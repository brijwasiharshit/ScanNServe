package com.app.namasteqr.transformer;

import com.app.namasteqr.domain.entity.RestaurantEntity;
import com.app.namasteqr.domain.entity.UserEntity;
import com.app.namasteqr.dto.request.AdminRequestDTO;
import com.app.namasteqr.dto.response.AdminResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class UserTransformer {
    public UserEntity toEntity(
            AdminRequestDTO dto,
            RestaurantEntity restaurant
    ) {

        if (dto == null) {
            return null;
        }

        UserEntity user = new UserEntity();

        user.setUsername(dto.getUsername().trim());
        user.setEmailAddress(dto.getEmailAddress().trim().toLowerCase());
        user.setContactNumber(dto.getContactNumber().trim());
        user.setAddress(dto.getAddress());
        user.setRestaurant(restaurant);

        return user;
    }
    public AdminResponseDTO toDto(
            UserEntity entity
    ) {

        if (entity == null) {
            return null;
        }

        return AdminResponseDTO.builder()
                .userId(entity.getId())
                .username(entity.getUsername())
                .emailAddress(entity.getEmailAddress())
                .contactNumber(entity.getContactNumber())
                .address(entity.getAddress())
                .restaurantId(entity.getRestaurant() != null ? entity.getRestaurant().getRestaurantId() : null)
                .restaurantName(entity.getRestaurant() != null ? entity.getRestaurant().getName() : null)
                .createdAt(entity.getCreatedAt())
                .build();
    }
}
