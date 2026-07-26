package com.app.namasteqr.service;

import com.app.namasteqr.dto.request.FoodItemRequestDTO;
import com.app.namasteqr.dto.request.FoodItemUpdateRequestDTO;
import com.app.namasteqr.dto.response.FoodItemResponseDTO;
import com.app.namasteqr.dto.response.ItemSearchResponseDTO;

import java.util.List;

public interface IFoodItemService {

    FoodItemResponseDTO createFoodItem(FoodItemRequestDTO requestDTO);
    List<FoodItemResponseDTO> getAllFoodItems();
    FoodItemResponseDTO getFoodItemById(Long itemId);
    List<FoodItemResponseDTO> getFoodItemsByCategory(Long categoryId);
    FoodItemResponseDTO updateFoodItem(Long itemId, FoodItemUpdateRequestDTO requestDTO);
    void deleteFoodItem(Long itemId);
    List<ItemSearchResponseDTO> searchItems(String keyword);
}

