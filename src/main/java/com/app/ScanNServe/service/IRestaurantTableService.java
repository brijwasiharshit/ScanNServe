package com.app.ScanNServe.service;

import com.app.ScanNServe.dto.request.RestaurantTableRequestDTO;
import com.app.ScanNServe.dto.response.RestaurantTableResponseDTO;

import java.util.List;

public interface IRestaurantTableService {
    RestaurantTableResponseDTO createTable(
            RestaurantTableRequestDTO requestDTO
    );
    List<RestaurantTableResponseDTO> getTables();
}
