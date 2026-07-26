package com.app.namasteqr.service;

import com.app.namasteqr.dto.request.RestaurantMenuItemRequestDTO;
import com.app.namasteqr.dto.request.UpdateRestaurantMenuItemRequestDTO;
import com.app.namasteqr.dto.response.RestaurantMenuItemResponseDTO;

import java.util.List;

public interface IRestaurantMenuService {
    RestaurantMenuItemResponseDTO subscribeItem(
            RestaurantMenuItemRequestDTO requestDTO
    );
    List<RestaurantMenuItemResponseDTO> getMenu();

    RestaurantMenuItemResponseDTO getMenuItem(Long itemId);

    RestaurantMenuItemResponseDTO updateMenuItem(
            Long itemId,
            UpdateRestaurantMenuItemRequestDTO requestDTO
    );
    void removeMenuItem(Long itemId);
}
