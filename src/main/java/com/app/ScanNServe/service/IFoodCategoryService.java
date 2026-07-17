package com.app.ScanNServe.service;

import com.app.ScanNServe.dto.request.FoodCategoryRequestDTO;
import com.app.ScanNServe.dto.response.FoodCategoryResponseDTO;

import java.util.List;

public interface IFoodCategoryService {

    FoodCategoryResponseDTO createCategory(FoodCategoryRequestDTO foodCategoryRequestDTO);

    List<FoodCategoryResponseDTO> getAllCategories();
    FoodCategoryResponseDTO getCategoryById(Long categoryId);
    FoodCategoryResponseDTO updateCategory(Long categoryId, FoodCategoryRequestDTO requestDTO);
    void deleteCategory(Long categoryId);
}
