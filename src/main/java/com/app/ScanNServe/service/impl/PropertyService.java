package com.app.ScanNServe.service.impl;

import com.app.ScanNServe.domain.entity.PropertyEntity;
import com.app.ScanNServe.domain.repository.IPropertyRepository;
import com.app.ScanNServe.dto.request.PropertyRequestDTO;
import com.app.ScanNServe.dto.response.PropertyResponseDTO;
import com.app.ScanNServe.service.IPropertyService;
import com.app.ScanNServe.transformer.PropertyTransformer;
import com.app.ScanNServe.utils.validations.ValidateProperty;
import lombok.Data;
import org.springframework.stereotype.Service;

@Service
@Data
public class PropertyService implements IPropertyService {

    private final IPropertyRepository propertyRepository;
    private final PropertyTransformer propertyTransformer;

    @Override
    public PropertyResponseDTO createProperty(PropertyRequestDTO propertyRequestDTO) {
        String normalizedName = normalizeName(propertyRequestDTO.getName());

        if (propertyExists(normalizedName)) {
            throw new IllegalArgumentException("Property with the given name already exists");
        }

        propertyRequestDTO.setName(normalizedName);

        ValidateProperty.validateProperty(
                normalizedName,
                propertyRequestDTO.getDescription(),
                propertyRequestDTO.getAddress(),
                propertyRequestDTO.getLogoLink()
        );

        PropertyEntity propertyEntity = propertyTransformer.toEntity(propertyRequestDTO);

        PropertyEntity saved = propertyRepository.save(propertyEntity);

        return propertyTransformer.toDto(saved);
    }

    private boolean propertyExists(String name) {
        return propertyRepository.existsByName(name);
    }

    private String normalizeName(String name) {
        return name == null ? null : name.trim();
    }

}

