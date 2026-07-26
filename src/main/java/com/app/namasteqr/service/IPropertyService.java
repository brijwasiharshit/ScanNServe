package com.app.namasteqr.service;

import com.app.namasteqr.dto.request.PropertyRequestDTO;
import com.app.namasteqr.dto.response.PropertyResponseDTO;

public interface IPropertyService {

    PropertyResponseDTO createProperty(PropertyRequestDTO propertyRequestDTO);
}

