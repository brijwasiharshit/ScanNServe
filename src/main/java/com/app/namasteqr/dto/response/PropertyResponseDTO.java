package com.app.namasteqr.dto.response;

import lombok.Data;

@Data
public class PropertyResponseDTO {
    private Long id;
    private String name;
    private String description;
    private String address;
    private String logoLink;
}

