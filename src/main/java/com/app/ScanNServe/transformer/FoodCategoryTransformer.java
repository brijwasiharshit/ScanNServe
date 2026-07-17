package com.app.ScanNServe.transformer;


import com.app.ScanNServe.domain.entity.FoodCategoryEntity;
import com.app.ScanNServe.dto.request.FoodCategoryRequestDTO;
import com.app.ScanNServe.dto.response.FoodCategoryResponseDTO;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
    public class FoodCategoryTransformer {

        public FoodCategoryEntity toEntity(FoodCategoryRequestDTO dto) {

            if (dto == null) {
                return null;
            }

            return FoodCategoryEntity.builder()
                    .name(dto.getName())
                    .build();
        }

    public FoodCategoryResponseDTO toDto(FoodCategoryEntity entity) {

        if (entity == null) {
            return null;
        }

        return FoodCategoryResponseDTO.builder()
                .categoryId(entity.getCategoryId())
                .name(entity.getName())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

    public void updateEntity(
            FoodCategoryEntity entity,
            String normalizedName
    ) {

        entity.setName(normalizedName);
    }


    public List<FoodCategoryResponseDTO> toDtoList(
            List<FoodCategoryEntity> entities
    ) {

        if (entities == null || entities.isEmpty()) {
            return Collections.emptyList();
        }

        return entities.stream()
                .map(this::toDto)
                .toList();
    }

    }

