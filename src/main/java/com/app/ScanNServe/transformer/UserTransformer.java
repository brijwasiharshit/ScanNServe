package com.app.ScanNServe.transformer;

import com.app.ScanNServe.domain.entity.RestaurantEntity;
import com.app.ScanNServe.domain.entity.UserEntity;
import com.app.ScanNServe.dto.request.AdminRequestDTO;
import com.app.ScanNServe.dto.response.AdminResponseDTO;
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
                .restaurantId(entity.getRestaurant().getRestaurantId())
                .createdAt(entity.getCreatedAt())
                .build();
    }
}
