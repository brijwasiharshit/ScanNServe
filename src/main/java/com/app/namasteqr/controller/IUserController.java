package com.app.namasteqr.controller;


import com.app.namasteqr.dto.request.OrderItemRequestDTO;
import com.app.namasteqr.dto.response.CustomerMenuResponseDTO;
import com.app.namasteqr.dto.response.FoodItemResponseDTO;
import com.app.namasteqr.dto.response.OrderResponseDTO;
import com.app.namasteqr.dto.response.WifiResponseDTO;
import com.app.namasteqr.utils.api.StandardResponse;
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
