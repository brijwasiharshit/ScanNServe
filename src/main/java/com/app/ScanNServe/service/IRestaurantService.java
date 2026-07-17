package com.app.ScanNServe.service;

import com.app.ScanNServe.dto.request.RestaurantRequestDTO;
import com.app.ScanNServe.dto.response.RestaurantResponseDTO;

public interface IRestaurantService {
    public RestaurantResponseDTO createRestaurant(
            RestaurantRequestDTO requestDTO
    );
    RestaurantResponseDTO getRestaurant();
}
