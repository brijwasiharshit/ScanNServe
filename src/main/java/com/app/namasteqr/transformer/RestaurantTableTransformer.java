package com.app.namasteqr.transformer;

import com.app.namasteqr.domain.entity.RestaurantEntity;
import com.app.namasteqr.domain.entity.RestaurantTableEntity;
import com.app.namasteqr.dto.response.RestaurantTableResponseDTO;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class RestaurantTableTransformer {
    public RestaurantTableEntity toEntity(
            RestaurantEntity restaurant,
            String tableNumber
    ) {

        return RestaurantTableEntity.builder()
                .restaurant(restaurant)
                .tableNumber(tableNumber)
                .tableToken(UUID.randomUUID().toString())
                .build();
    }
    public RestaurantTableResponseDTO toDto(
            RestaurantTableEntity entity
    ) {

        return RestaurantTableResponseDTO.builder()
                .tableId(entity.getTableId())
                .tableNumber(entity.getTableNumber())
                .tableToken(entity.getTableToken())
                .createdAt(entity.getCreatedAt())
                .build();
    }
}
