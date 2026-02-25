package com.app.ScanNServe.dto.request;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Data
public class PropertyRequestDTO {
    private String name;
    private String description;
    private String address;
//    private List<String> gallery;
    private String wifiIdFk;
}
