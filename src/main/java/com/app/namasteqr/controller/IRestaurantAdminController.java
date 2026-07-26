package com.app.namasteqr.controller;

import com.app.namasteqr.dto.request.RestaurantMenuItemRequestDTO;
import com.app.namasteqr.dto.request.RestaurantTableRequestDTO;
import com.app.namasteqr.dto.request.UpdateRestaurantMenuItemRequestDTO;
import com.app.namasteqr.dto.response.ItemSearchResponseDTO;
import com.app.namasteqr.dto.response.RestaurantMenuItemResponseDTO;
import com.app.namasteqr.dto.response.RestaurantResponseDTO;
import com.app.namasteqr.dto.response.RestaurantTableResponseDTO;
import com.app.namasteqr.utils.api.StandardResponse;
import jakarta.validation.Valid;
import com.app.namasteqr.dto.response.SalesReportResponseDTO;
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

    ResponseEntity<StandardResponse<SalesReportResponseDTO>> getSalesReport();
}
