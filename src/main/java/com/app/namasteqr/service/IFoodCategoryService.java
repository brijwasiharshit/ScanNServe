package com.app.namasteqr.service;

import com.app.namasteqr.dto.request.FoodCategoryRequestDTO;
import com.app.namasteqr.dto.response.FoodCategoryResponseDTO;

import java.util.List;

public interface IFoodCategoryService {

    FoodCategoryResponseDTO createCategory(FoodCategoryRequestDTO foodCategoryRequestDTO);

    List<FoodCategoryResponseDTO> getAllCategories();
    FoodCategoryResponseDTO getCategoryById(Long categoryId);
    FoodCategoryResponseDTO updateCategory(Long categoryId, FoodCategoryRequestDTO requestDTO);
    void deleteCategory(Long categoryId);
}
