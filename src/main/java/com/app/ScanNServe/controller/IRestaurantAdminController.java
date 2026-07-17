package com.app.ScanNServe.controller;

import com.app.ScanNServe.dto.request.RestaurantMenuItemRequestDTO;
import com.app.ScanNServe.dto.request.RestaurantTableRequestDTO;
import com.app.ScanNServe.dto.request.UpdateRestaurantMenuItemRequestDTO;
import com.app.ScanNServe.dto.response.ItemSearchResponseDTO;
import com.app.ScanNServe.dto.response.RestaurantMenuItemResponseDTO;
import com.app.ScanNServe.dto.response.RestaurantResponseDTO;
import com.app.ScanNServe.dto.response.RestaurantTableResponseDTO;
import com.app.ScanNServe.utils.api.StandardResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface IRestaurantAdminController {
    ResponseEntity<StandardResponse<RestaurantResponseDTO>> getRestaurant();
    ResponseEntity<StandardResponse<List<ItemSearchResponseDTO>>> searchItems(@RequestParam String keyword
    );
    ResponseEntity<StandardResponse<RestaurantMenuItemResponseDTO>> subscribeItem(
            @Valid @RequestBody RestaurantMenuItemRequestDTO requestDTO
    );
    ResponseEntity<StandardResponse<RestaurantMenuItemResponseDTO>> getMenuItem(
            @PathVariable Long itemId
    );
    ResponseEntity<StandardResponse<List<RestaurantMenuItemResponseDTO>>> getMenu();
    ResponseEntity<StandardResponse<RestaurantMenuItemResponseDTO>> updateMenuItem(@PathVariable Long itemId,
    @RequestBody UpdateRestaurantMenuItemRequestDTO requestDTO
    );

    ResponseEntity<StandardResponse<Void>> removeMenuItem(
            @PathVariable Long itemId
    );
    ResponseEntity<StandardResponse<RestaurantTableResponseDTO>> createTable(
            @Valid @RequestBody RestaurantTableRequestDTO requestDTO
    );

    ResponseEntity<StandardResponse<List<RestaurantTableResponseDTO>>> getTables();
}

