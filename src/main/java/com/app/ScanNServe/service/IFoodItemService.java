package com.app.ScanNServe.service;

import com.app.ScanNServe.dto.request.FoodItemRequestDTO;
import com.app.ScanNServe.dto.response.FoodItemResponseDTO;

public interface IFoodItemService {

    FoodItemResponseDTO addFoodItemByCategory(FoodItemRequestDTO foodItemRequestDTO);
}

