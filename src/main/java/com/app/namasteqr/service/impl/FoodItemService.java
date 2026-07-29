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
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import org.springframework.web.multipart.MultipartFile;
import com.app.namasteqr.utils.enums.FoodType;

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

        FoodCategoryEntity category;
        if (requestDTO.getCategoryId() != null) {
            category = foodCategoryRepository
                    .findByCategoryIdAndIsDeletedFalse(requestDTO.getCategoryId())
                    .orElseThrow(() ->
                            new ResourceNotFoundException("Food category not found with ID: " + requestDTO.getCategoryId()));
        } else if (requestDTO.getCategoryName() != null && !requestDTO.getCategoryName().trim().isEmpty()) {
            category = foodCategoryRepository
                    .findByNameIgnoreCaseAndIsDeletedFalse(requestDTO.getCategoryName().trim())
                    .orElseThrow(() ->
                            new ResourceNotFoundException("Food category not found with name: " + requestDTO.getCategoryName()));
        } else {
            throw new IllegalArgumentException("Either categoryId or categoryName must be provided.");
        }

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
    @Transactional(rollbackOn = Exception.class)
    public List<FoodItemResponseDTO> uploadBulkFoodItems(MultipartFile file) {
        List<FoodItemResponseDTO> responseDTOs = new ArrayList<>();
        
        try (BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8))) {
            String line;
            boolean firstLine = true;
            while ((line = br.readLine()) != null) {
                if (firstLine) {
                    firstLine = false;
                    if (line.toLowerCase().contains("name") || line.toLowerCase().contains("category")) continue;
                }
                
                String[] values = line.split(",", -1);
                if (values.length < 3) {
                    throw new RuntimeException("Invalid CSV format. Expected at least: Name, CategoryName, FoodType");
                }
                
                String name = values[0].trim();
                String categoryName = values[1].trim();
                String foodTypeStr = values[2].trim();
                String defaultImage = values.length >= 4 ? values[3].trim() : "";
                
                if (name.isEmpty() || categoryName.isEmpty() || foodTypeStr.isEmpty()) {
                    throw new RuntimeException("Name, CategoryName, and FoodType are required fields.");
                }
                
                FoodItemRequestDTO requestDTO = new FoodItemRequestDTO();
                requestDTO.setName(name);
                requestDTO.setCategoryName(categoryName);
                
                try {
                    requestDTO.setFoodType(FoodType.valueOf(foodTypeStr.toUpperCase()));
                } catch (IllegalArgumentException ex) {
                    throw new RuntimeException("Invalid FoodType: " + foodTypeStr + " for item: " + name);
                }
                
                requestDTO.setDefaultImage(defaultImage);
                
                responseDTOs.add(createFoodItem(requestDTO));
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to process bulk upload: " + e.getMessage(), e);
        }
        
        return responseDTOs;
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

