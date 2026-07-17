package com.app.ScanNServe.dto.response;

import lombok.*;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Component
public class RestaurantTableResponseDTO {

    private Long tableId;

    private String tableNumber;

    private String tableToken;

    private LocalDateTime createdAt;
}