package com.app.ScanNServe.controller;

import com.app.ScanNServe.dto.request.FoodCategoryRequestDTO;
import com.app.ScanNServe.dto.request.FoodItemRequestDTO;
import com.app.ScanNServe.dto.response.FoodCategoryResponseDTO;
import com.app.ScanNServe.dto.response.FoodItemResponseDTO;
import com.app.ScanNServe.utils.api.StandardResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

public interface IFoodItemController {

    ResponseEntity<StandardResponse<FoodItemResponseDTO>> addFoodItemByCategory(
            @RequestBody FoodItemRequestDTO foodItemRequestDTO
    );

    ResponseEntity<StandardResponse<FoodCategoryResponseDTO>> createCategory(
            @RequestBody FoodCategoryRequestDTO foodCategoryRequestDTO
    );
}

