package com.app.ScanNServe.service;

import com.app.ScanNServe.dto.request.RestaurantMenuItemRequestDTO;
import com.app.ScanNServe.dto.request.UpdateRestaurantMenuItemRequestDTO;
import com.app.ScanNServe.dto.response.RestaurantMenuItemResponseDTO;

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
