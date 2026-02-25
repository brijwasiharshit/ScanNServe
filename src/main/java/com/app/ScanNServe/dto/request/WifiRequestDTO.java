package com.app.ScanNServe.dto.request;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class WifiRequestDTO {
    private String SSID;
    private String password;
    private String confPassword;
    private String coverageArea;
    private String propertyIdFk;
}
