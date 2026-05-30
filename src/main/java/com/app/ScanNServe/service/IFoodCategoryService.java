package com.app.ScanNServe.service;

import com.app.ScanNServe.dto.request.FoodCategoryRequestDTO;
import com.app.ScanNServe.dto.response.FoodCategoryResponseDTO;

public interface IFoodCategoryService {

    FoodCategoryResponseDTO createCategory(FoodCategoryRequestDTO foodCategoryRequestDTO);
}
