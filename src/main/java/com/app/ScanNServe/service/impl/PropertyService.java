package com.app.ScanNServe.service.impl;

import com.app.ScanNServe.domain.entity.PropertyEntity;
import com.app.ScanNServe.domain.repository.IPropertyRepository;
import com.app.ScanNServe.dto.request.PropertyRequestDTO;
import com.app.ScanNServe.dto.response.PropertyResponseDTO;
import com.app.ScanNServe.service.IPropertyService;
import com.app.ScanNServe.transformer.PropertyTransformer;
import com.app.ScanNServe.utils.jwt.UserPrincipal;
import com.app.ScanNServe.utils.security.SecurityContextUtil;
import com.app.ScanNServe.utils.validations.ValidateProperty;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Data
public class PropertyService implements IPropertyService {

    private final IPropertyRepository propertyRepository;
    private final PropertyTransformer propertyTransformer;
    private final SecurityContextUtil securityContextUtil;

    @Override
    public PropertyResponseDTO createProperty(PropertyRequestDTO propertyRequestDTO) {
        String normalizedName = normalizeName(propertyRequestDTO.getName());
        System.out.println(normalizedName);

        String actualName = propertyRequestDTO.getName();
        System.out.println(actualName);
        if (propertyExists(normalizedName,actualName)) {
            throw new IllegalArgumentException("Property with the given name already exists");
        }

        ValidateProperty.validateProperty(
                normalizedName,
                propertyRequestDTO.getDescription(),
                propertyRequestDTO.getAddress(),
                propertyRequestDTO.getLogoLink()
        );
        UserPrincipal user = securityContextUtil.fetchActiveUserDetails();
        PropertyEntity propertyEntity = propertyTransformer.toEntity(propertyRequestDTO,user.getId());

        PropertyEntity saved = propertyRepository.save(propertyEntity);

        return propertyTransformer.toDto(saved);
    }

    private boolean propertyExists(String normalizedName, String actualName) {
        return propertyRepository.findByName(actualName)
                .map(property -> normalizedName.equals(
                        property.getName().trim().toLowerCase()
                ))
                .orElse(false);
    }

    private String normalizeName(String name) {
        return name == null ? null : name.trim().toLowerCase();
    }

}

