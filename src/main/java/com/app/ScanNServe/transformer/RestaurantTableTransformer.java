package com.app.ScanNServe.transformer;

import com.app.ScanNServe.domain.entity.RestaurantEntity;
import com.app.ScanNServe.domain.entity.RestaurantTableEntity;
import com.app.ScanNServe.dto.response.RestaurantTableResponseDTO;
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
