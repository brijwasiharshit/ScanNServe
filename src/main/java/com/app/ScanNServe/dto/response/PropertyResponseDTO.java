package com.app.ScanNServe.dto.response;

import lombok.Data;

@Data
public class PropertyResponseDTO {
    private Long id;
    private String name;
    private String description;
    private String address;
    private String logoLink;
}

