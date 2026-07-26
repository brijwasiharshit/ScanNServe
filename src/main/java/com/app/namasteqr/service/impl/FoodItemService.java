package com.app.namasteqr.service.impl;

import com.app.namasteqr.domain.entity.FoodCategoryEntity;
import com.app.namasteqr.domain.entity.FoodItemEntity;
import com.app.namasteqr.domain.repository.IFoodCategoryRepository;
import com.app.namasteqr.domain.repository.IFoodItemRepository;
import com.app.namasteqr.dto.request.FoodItemRequestDTO;
import com.app.namasteqr.dto.request.FoodItemUpdateRequestDTO;
import com.app.namasteqr.dto.response.FoodItemResponseDTO;
import com.app.namasteqr.dto.response.ItemSearchResponseDTO;
import com.app.namasteqr.exception.ResourceNotFoundException;
import com.app.namasteqr.exception.ResourseAlreadyExistsException;
import com.app.namasteqr.service.IFoodItemService;
import com.app.namasteqr.transformer.FoodItemTransformer;
import com.app.namasteqr.utils.validations.ValidateProperty;
import jakarta.transaction.Transactional;
import lombok.Data;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Collections;
import java.util.List;

@Service
@Data
public class FoodItemService implements IFoodItemService {

    private final IFoodItemRepository foodItemRepository;
    private final IFoodCategoryRepository foodCategoryRepository;
    private final FoodItemTransformer foodItemTransformer;

    @Override
    @Transactional
    public FoodItemResponseDTO createFoodItem(
            FoodItemRequestDTO requestDTO
    ) {

        String normalizedName = requestDTO.getName()
                .trim()
                .replaceAll("\\s+", " ");

        FoodCategoryEntity category = foodCategoryRepository
                .findByCategoryIdAndIsDeletedFalse(requestDTO.getCategoryId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Food category not found."));

        if (foodItemRepository.existsByCategoryAndNameIgnoreCaseAndIsDeletedFalse(
                category,
                normalizedName
        )) {

            throw new ResourseAlreadyExistsException(
                    "Food item already exists in this category."
            );
        }

        FoodItemEntity entity = foodItemTransformer.toEntity(
                requestDTO,
                category,
                normalizedName
        );

        FoodItemEntity savedEntity = foodItemRepository.save(entity);

        return foodItemTransformer.toDto(savedEntity);
    }

    @Override
    @Transactional
    public List<FoodItemResponseDTO> getAllFoodItems() {

        List<FoodItemEntity> foodItems =
                foodItemRepository.findAllByIsDeletedFalseOrderByCategoryNameAscNameAsc();

        return foodItems.stream()
                .map(foodItemTransformer::toDto)
                .toList();
    }
    @Override
    @Transactional
    public FoodItemResponseDTO getFoodItemById(Long itemId) {

        FoodItemEntity foodItem = foodItemRepository
                .findByItemIdAndIsDeletedFalse(itemId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Food item not found."));

        return foodItemTransformer.toDto(foodItem);
    }
    @Override
    @Transactional
    public List<FoodItemResponseDTO> getFoodItemsByCategory(
            Long categoryId
    ) {

        FoodCategoryEntity category = foodCategoryRepository
                .findByCategoryIdAndIsDeletedFalse(categoryId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Food category not found."));

        List<FoodItemEntity> foodItems =
                foodItemRepository.findAllByCategoryAndIsDeletedFalseOrderByNameAsc(category);

        return foodItems.stream()
                .map(foodItemTransformer::toDto)
                .toList();
    }

    @Override
    @Transactional
    public FoodItemResponseDTO updateFoodItem(
            Long itemId,
            FoodItemUpdateRequestDTO requestDTO
    ) {

        FoodItemEntity foodItem = foodItemRepository
                .findByItemIdAndIsDeletedFalse(itemId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Food item not found."));

        if (requestDTO.getName() != null &&
                !requestDTO.getName().isBlank()) {

            String normalizedName = requestDTO.getName()
                    .trim()
                    .replaceAll("\\s+", " ");

            boolean alreadyExists =
                    foodItemRepository.existsByCategoryAndNameIgnoreCaseAndIsDeletedFalse(
                            foodItem.getCategory(),
                            normalizedName
                    );

            if (alreadyExists &&
                    !foodItem.getName().equalsIgnoreCase(normalizedName)) {

                throw new ResourseAlreadyExistsException(
                        "Food item already exists in this category."
                );
            }

            foodItem.setName(normalizedName);
        }

        if (requestDTO.getFoodType() != null) {
            foodItem.setFoodType(requestDTO.getFoodType());
        }

        if (requestDTO.getDefaultImage() != null &&
                !requestDTO.getDefaultImage().isBlank()) {

            foodItem.setDefaultImage(requestDTO.getDefaultImage().trim());
        }

        FoodItemEntity updated =
                foodItemRepository.save(foodItem);

        return foodItemTransformer.toDto(updated);
    }

    @Override
    @Transactional
    public void deleteFoodItem(Long itemId) {

        FoodItemEntity foodItem = foodItemRepository
                .findByItemIdAndIsDeletedFalse(itemId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Food item not found."));

        foodItem.setIsDeleted(true);
        foodItemRepository.save(foodItem);
    }

    @Override
    @Transactional
    public List<ItemSearchResponseDTO> searchItems(
            String keyword
    ) {

        String normalizedKeyword =
                keyword == null ? "" : keyword.trim();

        if (normalizedKeyword.isBlank()) {
            return Collections.emptyList();
        }

        List<FoodItemEntity> items =
                foodItemRepository
                        .findTop6ByNameStartingWithIgnoreCaseAndIsDeletedFalse(
                                normalizedKeyword
                        );

        return items.stream()
                .map(foodItemTransformer::toSearchDto)
                .toList();
    }
}

