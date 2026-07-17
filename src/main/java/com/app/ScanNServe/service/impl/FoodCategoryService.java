package com.app.ScanNServe.service.impl;

import com.app.ScanNServe.domain.entity.FoodCategoryEntity;
import com.app.ScanNServe.domain.repository.IFoodCategoryRepository;
import com.app.ScanNServe.dto.request.FoodCategoryRequestDTO;
import com.app.ScanNServe.dto.response.FoodCategoryResponseDTO;
import com.app.ScanNServe.exception.ResourceNotFoundException;
import com.app.ScanNServe.exception.ResourseAlreadyExistsException;
import com.app.ScanNServe.service.IFoodCategoryService;
import com.app.ScanNServe.transformer.FoodCategoryTransformer;
import com.app.ScanNServe.utils.validations.ValidateFoodCategory;
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
