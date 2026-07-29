package com.app.namasteqr.controller.impl;

import com.app.namasteqr.controller.IFoodItemController;
import com.app.namasteqr.dto.request.FoodCategoryRequestDTO;
import com.app.namasteqr.dto.request.FoodItemRequestDTO;
import com.app.namasteqr.dto.request.FoodItemUpdateRequestDTO;
import com.app.namasteqr.dto.response.FoodCategoryResponseDTO;
import com.app.namasteqr.dto.response.FoodItemResponseDTO;
import com.app.namasteqr.service.IFoodCategoryService;
import com.app.namasteqr.service.IFoodItemService;
import com.app.namasteqr.utils.api.StandardResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
public class FoodItemController implements IFoodItemController {

    private final IFoodItemService foodItemService;
    private final IFoodCategoryService foodCategoryService;



    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @PostMapping("/categories")
    @Override
    public ResponseEntity<StandardResponse<FoodCategoryResponseDTO>> createCategory(
            @Valid @RequestBody FoodCategoryRequestDTO foodCategoryRequestDTO
    ) {
        FoodCategoryResponseDTO responseDTO = foodCategoryService.createCategory(foodCategoryRequestDTO);

        StandardResponse<FoodCategoryResponseDTO> response =
                StandardResponse.<FoodCategoryResponseDTO>builder()
                        .data(responseDTO)
                        .success(true)
                        .message("Food category created successfully")
                        .errors(null)
                        .httpStatus(HttpStatus.CREATED)
                        .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @GetMapping("/categories")
    public ResponseEntity<StandardResponse<List<FoodCategoryResponseDTO>>> getAllCategories() {
        System.out.println("Inside get all!");
        List<FoodCategoryResponseDTO> responseDTO =
                foodCategoryService.getAllCategories();

        StandardResponse<List<FoodCategoryResponseDTO>> response =
                StandardResponse.<List<FoodCategoryResponseDTO>>builder()
                        .success(true)
                        .message("Food categories fetched successfully")
                        .data(responseDTO)
                        .errors(null)
                        .httpStatus(HttpStatus.OK)
                        .build();

        return ResponseEntity.ok(response);
    }


    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @GetMapping("/categories/{categoryId}")
    @Override
    public ResponseEntity<StandardResponse<FoodCategoryResponseDTO>> getCategoryById(
            @PathVariable Long categoryId
    ) {

        FoodCategoryResponseDTO responseDTO =
                foodCategoryService.getCategoryById(categoryId);

        StandardResponse<FoodCategoryResponseDTO> response =
                StandardResponse.<FoodCategoryResponseDTO>builder()
                        .success(true)
                        .message("Food category fetched successfully")
                        .data(responseDTO)
                        .errors(null)
                        .httpStatus(HttpStatus.OK)
                        .build();

        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @PutMapping("/categories/{categoryId}")
    @Override
    public ResponseEntity<StandardResponse<FoodCategoryResponseDTO>> updateCategory(
            @PathVariable Long categoryId,
            @Valid @RequestBody FoodCategoryRequestDTO requestDTO
    ) {
        FoodCategoryResponseDTO responseDTO =
                foodCategoryService.updateCategory(categoryId, requestDTO);

        StandardResponse<FoodCategoryResponseDTO> response =
                StandardResponse.<FoodCategoryResponseDTO>builder()
                        .success(true)
                        .message("Food category updated successfully")
                        .data(responseDTO)
                        .errors(null)
                        .httpStatus(HttpStatus.OK)
                        .build();

        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @DeleteMapping("/categories/{categoryId}")
    @Override
    public ResponseEntity<Void> deleteCategory(
            @PathVariable Long categoryId
    ) {

        foodCategoryService.deleteCategory(categoryId);

        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @GetMapping("/categories/{categoryId}/items")
    @Override
    public ResponseEntity<StandardResponse<List<FoodItemResponseDTO>>> getFoodItemsByCategory(
            @PathVariable Long categoryId
    ) {

        List<FoodItemResponseDTO> responseDTO =
                foodItemService.getFoodItemsByCategory(categoryId);

        StandardResponse<List<FoodItemResponseDTO>> response =
                StandardResponse.<List<FoodItemResponseDTO>>builder()
                        .success(true)
                        .message("Food items fetched successfully")
                        .data(responseDTO)
                        .errors(null)
                        .httpStatus(HttpStatus.OK)
                        .build();

        return ResponseEntity.ok(response);
    }
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @PostMapping("/items")
    @Override
    public ResponseEntity<StandardResponse<FoodItemResponseDTO>> createFoodItem(
            @Valid @RequestBody FoodItemRequestDTO requestDTO
    ) {

        FoodItemResponseDTO responseDTO =
                foodItemService.createFoodItem(requestDTO);

        StandardResponse<FoodItemResponseDTO> response =
                StandardResponse.<FoodItemResponseDTO>builder()
                        .success(true)
                        .message("Food item created successfully")
                        .data(responseDTO)
                        .errors(null)
                        .httpStatus(HttpStatus.CREATED)
                        .build();

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @GetMapping("/items")
    @Override
    public ResponseEntity<StandardResponse<List<FoodItemResponseDTO>>> getAllFoodItems() {

        List<FoodItemResponseDTO> responseDTO =
                foodItemService.getAllFoodItems();

        StandardResponse<List<FoodItemResponseDTO>> response =
                StandardResponse.<List<FoodItemResponseDTO>>builder()
                        .success(true)
                        .message("Food items fetched successfully")
                        .data(responseDTO)
                        .errors(null)
                        .httpStatus(HttpStatus.OK)
                        .build();

        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @GetMapping("/items/{itemId}")
    @Override
    public ResponseEntity<StandardResponse<FoodItemResponseDTO>> getFoodItemById(
            @PathVariable Long itemId
    ) {

        FoodItemResponseDTO responseDTO =
                foodItemService.getFoodItemById(itemId);

        StandardResponse<FoodItemResponseDTO> response =
                StandardResponse.<FoodItemResponseDTO>builder()
                        .success(true)
                        .message("Food item fetched successfully")
                        .data(responseDTO)
                        .errors(null)
                        .httpStatus(HttpStatus.OK)
                        .build();

        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @PatchMapping("/items/{itemId}")
    @Override
    public ResponseEntity<StandardResponse<FoodItemResponseDTO>> updateFoodItem(
            @PathVariable Long itemId,
            @RequestBody FoodItemUpdateRequestDTO requestDTO
    ) {

        FoodItemResponseDTO responseDTO =
                foodItemService.updateFoodItem(itemId, requestDTO);

        StandardResponse<FoodItemResponseDTO> response =
                StandardResponse.<FoodItemResponseDTO>builder()
                        .success(true)
                        .message("Food item updated successfully")
                        .data(responseDTO)
                        .errors(null)
                        .httpStatus(HttpStatus.OK)
                        .build();

        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @DeleteMapping("/items/{itemId}")
    @Override
    public ResponseEntity<StandardResponse<Void>> deleteFoodItem(
            @PathVariable Long itemId
    ) {

        foodItemService.deleteFoodItem(itemId);

        StandardResponse<Void> response =
                StandardResponse.<Void>builder()
                        .success(true)
                        .message("Food item deleted successfully")
                        .data(null)
                        .errors(null)
                        .httpStatus(HttpStatus.OK)
                        .build();

        return ResponseEntity.ok(response);
    }



    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @PostMapping("/items/bulk")
    @Override
    public ResponseEntity<StandardResponse<List<FoodItemResponseDTO>>> uploadBulkFoodItems(
            @RequestParam("file") org.springframework.web.multipart.MultipartFile file
    ) {
        List<FoodItemResponseDTO> responseDTOs = foodItemService.uploadBulkFoodItems(file);

        StandardResponse<List<FoodItemResponseDTO>> response =
                StandardResponse.<List<FoodItemResponseDTO>>builder()
                        .success(true)
                        .message("Bulk food items uploaded successfully")
                        .data(responseDTOs)
                        .errors(null)
                        .httpStatus(HttpStatus.CREATED)
                        .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
