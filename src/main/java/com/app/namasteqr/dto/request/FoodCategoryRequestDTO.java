package com.app.namasteqr.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data

public class FoodCategoryRequestDTO {
    @NotBlank
    @Size(max = 100)
    private String name;
}
