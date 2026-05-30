package com.app.ScanNServe.service.impl;

import com.app.ScanNServe.domain.entity.PropertyEntity;
import com.app.ScanNServe.domain.entity.WifiEntity;
import com.app.ScanNServe.domain.repository.IPropertyRepository;
import com.app.ScanNServe.domain.repository.IWifiRepository;
import com.app.ScanNServe.dto.request.WifiRequestDTO;
import com.app.ScanNServe.dto.response.WifiResponseDTO;
import com.app.ScanNServe.service.IWifiService;
import com.app.ScanNServe.transformer.WifiTransformer;
import com.app.ScanNServe.utils.validations.ValidateWifi;
import lombok.Data;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Data
public class WifiService implements IWifiService {

    private final IWifiRepository wifiRepository;
    private final IPropertyRepository propertyRepository;
    private final PasswordEncoder passwordEncoder;
    private final WifiTransformer wifiTransformer;

    @Override
    public WifiResponseDTO createWifi(WifiRequestDTO wifiRequestDTO) {

        ValidateWifi.validateSsid(wifiRequestDTO.getSSID());

        String password = wifiRequestDTO.getPassword();
        String confirmPassword = wifiRequestDTO.getConfPassword();

        ValidateWifi.validatePassword(password, confirmPassword);

        Long propertyId = Long.parseLong(wifiRequestDTO.getPropertyIdFk());

        PropertyEntity property = propertyRepository.findById(propertyId)
                .orElseThrow(() -> new IllegalArgumentException("Property not found with id: " + propertyId));

        String hashedPassword = passwordEncoder.encode(password);
        WifiEntity wifiEntity = wifiTransformer.toEntity(wifiRequestDTO, property, hashedPassword);

        WifiEntity saved = wifiRepository.save(wifiEntity);

        return wifiTransformer.toDto(saved);
    }

    @Override
    public WifiResponseDTO getWifiDetails(WifiRequestDTO wifiRequestDTO) {

        Long propertyId = Long.parseLong(wifiRequestDTO.getPropertyIdFk());
        String coverageArea = wifiRequestDTO.getCoverageArea();

        PropertyEntity property = propertyRepository.findById(propertyId)
                .orElseThrow(() -> new IllegalArgumentException("Property not found with id: " + propertyId));

        WifiEntity wifiEntity = wifiRepository.findByPropertyAndCoverage(property, coverageArea)
                .orElseThrow(() -> new IllegalArgumentException("Wifi details not found for coverage: " + coverageArea));

        return wifiTransformer.toDto(wifiEntity);
    }

    @Override
    public List<WifiResponseDTO> getWifiByProperty(WifiRequestDTO wifiRequestDTO) {

        Long propertyId = Long.parseLong(wifiRequestDTO.getPropertyIdFk());

        PropertyEntity property = propertyRepository.findById(propertyId)
                .orElseThrow(() -> new IllegalArgumentException("Property not found with id: " + propertyId));

        List<WifiEntity> wifiEntities = wifiRepository.findByProperty(property);

        return wifiEntities.stream()
                .map(wifiTransformer::toDto)
                .collect(Collectors.toList());
    }
}

