package com.app.ScanNServe.controller.impl;

import com.app.ScanNServe.controller.IFoodItemController;
import com.app.ScanNServe.dto.request.FoodCategoryRequestDTO;
import com.app.ScanNServe.dto.request.FoodItemRequestDTO;
import com.app.ScanNServe.dto.response.FoodCategoryResponseDTO;
import com.app.ScanNServe.dto.response.FoodItemResponseDTO;
import com.app.ScanNServe.service.IFoodCategoryService;
import com.app.ScanNServe.service.IFoodItemService;
import com.app.ScanNServe.utils.api.StandardResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/food")
@AllArgsConstructor
public class FoodItemController implements IFoodItemController {

    private final IFoodItemService foodItemService;
    private final IFoodCategoryService foodCategoryService;

    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @PostMapping("/create")
    @Override
    public ResponseEntity<StandardResponse<FoodItemResponseDTO>> addFoodItemByCategory(
            @RequestBody FoodItemRequestDTO foodItemRequestDTO
    ) {
        System.out.println("Inside add food Item!");
        FoodItemResponseDTO responseDTO = foodItemService.addFoodItemByCategory(foodItemRequestDTO);

        StandardResponse<FoodItemResponseDTO> response =
                StandardResponse.<FoodItemResponseDTO>builder()
                        .data(responseDTO)
                        .success(true)
                        .message("Food item created successfully")
                        .errors(null)
                        .httpStatus(HttpStatus.CREATED)
                        .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @PostMapping("/category/create")
    @Override
    public ResponseEntity<StandardResponse<FoodCategoryResponseDTO>> createCategory(
            @RequestBody FoodCategoryRequestDTO foodCategoryRequestDTO
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
}

