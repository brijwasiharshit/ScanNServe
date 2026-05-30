package com.app.ScanNServe.service;

import com.app.ScanNServe.dto.request.PropertyRequestDTO;
import com.app.ScanNServe.dto.response.PropertyResponseDTO;

public interface IPropertyService {

    PropertyResponseDTO createProperty(PropertyRequestDTO propertyRequestDTO);
}

