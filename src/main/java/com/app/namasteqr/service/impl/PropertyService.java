package com.app.namasteqr.service.impl;

import com.app.namasteqr.dto.request.PropertyRequestDTO;
import com.app.namasteqr.dto.response.PropertyResponseDTO;
import com.app.namasteqr.service.IPropertyService;
//import com.app.namasteqr.transformer.PropertyTransformer;
import com.app.namasteqr.utils.security.SecurityContextUtil;
import lombok.Data;
import org.springframework.stereotype.Service;

@Service
@Data
public class PropertyService implements IPropertyService {

//    private final PropertyTransformer propertyTransformer;
    private final SecurityContextUtil securityContextUtil;

    @Override
    public PropertyResponseDTO createProperty(PropertyRequestDTO propertyRequestDTO) {
        return null;
//        String normalizedName = normalizeName(propertyRequestDTO.getName());
//        System.out.println(normalizedName);
//
//        String actualName = propertyRequestDTO.getName();
//        System.out.println(actualName);
//        if (propertyExists(normalizedName,actualName)) {
//            throw new IllegalArgumentException("Property with the given name already exists");
//        }
//
//        ValidateProperty.validateProperty(
//                normalizedName,
//                propertyRequestDTO.getDescription(),
//                propertyRequestDTO.getAddress(),
//                propertyRequestDTO.getLogoLink()
//        );
//        UserPrincipal user = securityContextUtil.fetchActiveUserDetails();
//        PropertyEntity propertyEntity = propertyTransformer.toEntity(propertyRequestDTO,user.getId());
//
//        PropertyEntity saved = propertyRepository.save(propertyEntity);
//
//        return propertyTransformer.toDto(saved);
    }



    private String normalizeName(String name) {
        return name == null ? null : name.trim().toLowerCase();
    }

}

