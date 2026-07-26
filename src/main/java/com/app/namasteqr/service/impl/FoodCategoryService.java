package com.app.namasteqr.service.impl;

import com.app.namasteqr.domain.entity.FoodCategoryEntity;
import com.app.namasteqr.domain.repository.IFoodCategoryRepository;
import com.app.namasteqr.dto.request.FoodCategoryRequestDTO;
import com.app.namasteqr.dto.response.FoodCategoryResponseDTO;
import com.app.namasteqr.exception.ResourceNotFoundException;
import com.app.namasteqr.exception.ResourseAlreadyExistsException;
import com.app.namasteqr.service.IFoodCategoryService;
import com.app.namasteqr.transformer.FoodCategoryTransformer;
import com.app.namasteqr.utils.validations.ValidateFoodCategory;
import jakarta.transaction.Transactional;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Data
@Transactional
public class FoodCategoryService implements IFoodCategoryService {

    private final IFoodCategoryRepository foodCategoryRepository;
    private final FoodCategoryTransformer foodCategoryTransformer;

    @Override
    public FoodCategoryResponseDTO createCategory(FoodCategoryRequestDTO foodCategoryRequestDTO) {

        String normalizedName = foodCategoryRequestDTO.getName().trim();
        if (foodCategoryRepository.existsByNameIgnoreCase(normalizedName)) {
            throw new IllegalArgumentException("Food category with this name already exists");
        }

        foodCategoryRequestDTO.setName(normalizedName);
        FoodCategoryEntity entity = foodCategoryTransformer.toEntity(foodCategoryRequestDTO);
        FoodCategoryEntity saved = foodCategoryRepository.save(entity);

        return foodCategoryTransformer.toDto(saved);
    }

    @Override
    @Transactional
    public List<FoodCategoryResponseDTO> getAllCategories() {

        return foodCategoryTransformer.toDtoList(
                foodCategoryRepository.findAllByIsDeletedFalseOrderByNameAsc()
        );
    }
    @Override
    @Transactional
    public FoodCategoryResponseDTO getCategoryById(Long categoryId) {

        FoodCategoryEntity entity = foodCategoryRepository
                .findByCategoryIdAndIsDeletedFalse(categoryId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Food category not found."));

        return foodCategoryTransformer.toDto(entity);
    }
    @Override
    @Transactional
    public FoodCategoryResponseDTO updateCategory(
            Long categoryId,
            FoodCategoryRequestDTO requestDTO
    ) {

        ValidateFoodCategory.validateCategoryName(requestDTO.getName());

        String normalizedName = requestDTO.getName()
                .trim()
                .replaceAll("\\s+", " ");

        FoodCategoryEntity category = foodCategoryRepository
                .findByCategoryIdAndIsDeletedFalse(categoryId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Food category not found."));

        if (!category.getName().equalsIgnoreCase(normalizedName)
                && foodCategoryRepository.existsByNameIgnoreCase(normalizedName)) {

            throw new ResourseAlreadyExistsException(
                    "Food category with this name already exists.");
        }

        foodCategoryTransformer.updateEntity(category, normalizedName);

        FoodCategoryEntity updatedCategory =
                foodCategoryRepository.save(category);

        return foodCategoryTransformer.toDto(updatedCategory);
    }

    @Override
    @Transactional
    public void deleteCategory(Long categoryId) {

        FoodCategoryEntity category = foodCategoryRepository
                .findByCategoryIdAndIsDeletedFalse(categoryId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Food category not found."));

        category.setIsDeleted(true);

        foodCategoryRepository.save(category);
    }
}
