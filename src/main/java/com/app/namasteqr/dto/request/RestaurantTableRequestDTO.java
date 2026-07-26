package com.app.namasteqr.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Component
public class RestaurantTableRequestDTO {

    @NotBlank(message = "Table number is required.")
    private String tableNumber;
}
