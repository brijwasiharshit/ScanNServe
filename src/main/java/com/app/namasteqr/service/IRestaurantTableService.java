package com.app.namasteqr.service;

import com.app.namasteqr.dto.request.RestaurantTableRequestDTO;
import com.app.namasteqr.dto.response.RestaurantTableResponseDTO;

import java.util.List;

public interface IRestaurantTableService {
    RestaurantTableResponseDTO createTable(
            RestaurantTableRequestDTO requestDTO
    );
    List<RestaurantTableResponseDTO> getTables();
}
