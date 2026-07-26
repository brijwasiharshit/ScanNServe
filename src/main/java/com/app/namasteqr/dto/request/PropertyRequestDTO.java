package com.app.namasteqr.dto.request;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Data
public class PropertyRequestDTO {
    private String name;
    private String description;
    private String address;
    private String logoLink;
//    private List<String> gallery;
}
