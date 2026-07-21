package com.app.ScanNServe.controller;


import com.app.ScanNServe.dto.request.OrderItemRequestDTO;
import com.app.ScanNServe.dto.response.CustomerMenuResponseDTO;
import com.app.ScanNServe.dto.response.FoodItemResponseDTO;
import com.app.ScanNServe.dto.response.OrderResponseDTO;
import com.app.ScanNServe.dto.response.WifiResponseDTO;
import com.app.ScanNServe.utils.api.StandardResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface IUserController {

    ResponseEntity<StandardResponse<CustomerMenuResponseDTO>> getMenu(
            @PathVariable String tableToken
    );

    ResponseEntity<StandardResponse<OrderResponseDTO>> placeOrder(
            @PathVariable String tableToken,
            @Valid @RequestBody List<OrderItemRequestDTO> items
    );
}
