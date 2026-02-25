package com.app.ScanNServe.transformer;

import com.app.ScanNServe.domain.entity.PropertyEntity;
import com.app.ScanNServe.domain.entity.WifiEntity;
import com.app.ScanNServe.dto.request.WifiRequestDTO;
import com.app.ScanNServe.dto.response.WifiResponseDTO;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class WifiTransformer {

    public WifiEntity toEntity(WifiRequestDTO d, PropertyEntity property, String hashedPassword) {
        WifiEntity wifi = new WifiEntity();
        wifi.setProperty(property);
        wifi.setSsid(d.getSSID());
        wifi.setCoverage(d.getCoverageArea());
        wifi.setHashedPassword(hashedPassword);
        return wifi;
    }

    public WifiResponseDTO toDto(WifiEntity e) {
        WifiResponseDTO d = new WifiResponseDTO();
        d.setSSID(e.getSsid());
        d.setCoverageArea(e.getCoverage());
        return d;
    }
}

