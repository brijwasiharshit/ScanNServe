package com.app.ScanNServe.service;

import com.app.ScanNServe.dto.request.FoodItemRequestDTO;
import com.app.ScanNServe.dto.request.FoodItemUpdateRequestDTO;
import com.app.ScanNServe.dto.response.FoodItemResponseDTO;
import com.app.ScanNServe.dto.response.ItemSearchResponseDTO;

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

