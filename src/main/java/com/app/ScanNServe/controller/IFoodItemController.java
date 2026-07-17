package com.app.ScanNServe.controller;

import com.app.ScanNServe.dto.request.FoodCategoryRequestDTO;
import com.app.ScanNServe.dto.request.FoodItemRequestDTO;
import com.app.ScanNServe.dto.request.FoodItemUpdateRequestDTO;
import com.app.ScanNServe.dto.request.RestaurantMenuItemRequestDTO;
import com.app.ScanNServe.dto.response.FoodCategoryResponseDTO;
import com.app.ScanNServe.dto.response.FoodItemResponseDTO;
import com.app.ScanNServe.dto.response.RestaurantMenuItemResponseDTO;
import com.app.ScanNServe.utils.api.StandardResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface IFoodItemController {

    ResponseEntity<StandardResponse<FoodCategoryResponseDTO>> createCategory(
            @RequestBody FoodCategoryRequestDTO foodCategoryRequestDTO
    );
    ResponseEntity<StandardResponse<List<FoodCategoryResponseDTO>>> getAllCategories();

    public ResponseEntity<StandardResponse<FoodCategoryResponseDTO>> getCategoryById(
            @PathVariable Long categoryId
    );
    public ResponseEntity<StandardResponse<FoodCategoryResponseDTO>> updateCategory(
            @PathVariable Long categoryId,
            @Valid @RequestBody FoodCategoryRequestDTO requestDTO
    );
    public ResponseEntity<Void> deleteCategory(
            @PathVariable Long categoryId
    );

    ResponseEntity<StandardResponse<List<FoodItemResponseDTO>>> getFoodItemsByCategory(
            @PathVariable Long categoryId
    );

    public ResponseEntity<StandardResponse<FoodItemResponseDTO>> createFoodItem(
            @Valid @RequestBody FoodItemRequestDTO requestDTO
    );

    public ResponseEntity<StandardResponse<List<FoodItemResponseDTO>>> getAllFoodItems();

    public ResponseEntity<StandardResponse<FoodItemResponseDTO>> getFoodItemById(
            @PathVariable Long itemId
    );


    ResponseEntity<StandardResponse<FoodItemResponseDTO>> updateFoodItem(
            @PathVariable Long itemId,
            @RequestBody FoodItemUpdateRequestDTO requestDTO
    );

    ResponseEntity<StandardResponse<Void>> deleteFoodItem(
            @PathVariable Long itemId
    );


}

