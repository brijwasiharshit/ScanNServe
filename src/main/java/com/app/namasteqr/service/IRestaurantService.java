package com.app.namasteqr.service;

import com.app.namasteqr.dto.request.RestaurantRequestDTO;
import com.app.namasteqr.dto.response.RestaurantResponseDTO;

public interface IRestaurantService {
    public RestaurantResponseDTO createRestaurant(
            RestaurantRequestDTO requestDTO
    );
    RestaurantResponseDTO getRestaurant();
    java.util.List<RestaurantResponseDTO> getAllRestaurants();
}
