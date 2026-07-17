package com.app.ScanNServe.dto.response;

import lombok.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Component
public class CustomerMenuResponseDTO {

    private RestaurantDTO restaurant;

    private TableDTO table;

    private List<CustomerMenuItemDTO> menu;
}