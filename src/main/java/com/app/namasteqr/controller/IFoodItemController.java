package com.app.namasteqr.controller;

import com.app.namasteqr.dto.request.FoodCategoryRequestDTO;
import com.app.namasteqr.dto.request.FoodItemRequestDTO;
import com.app.namasteqr.dto.request.FoodItemUpdateRequestDTO;
import com.app.namasteqr.dto.request.RestaurantMenuItemRequestDTO;
import com.app.namasteqr.dto.response.FoodCategoryResponseDTO;
import com.app.namasteqr.dto.response.FoodItemResponseDTO;
import com.app.namasteqr.dto.response.RestaurantMenuItemResponseDTO;
import com.app.namasteqr.utils.api.StandardResponse;
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

