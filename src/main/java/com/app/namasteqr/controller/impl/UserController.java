package com.app.namasteqr.controller.impl;

import com.app.namasteqr.controller.IUserController;
import com.app.namasteqr.dto.response.CustomerMenuResponseDTO;
import com.app.namasteqr.dto.response.FoodItemResponseDTO;
import com.app.namasteqr.dto.response.WifiResponseDTO;
import com.app.namasteqr.service.ICustomerMenuService;
import com.app.namasteqr.service.IOrderService;
import com.app.namasteqr.dto.request.OrderItemRequestDTO;
import com.app.namasteqr.dto.response.OrderResponseDTO;
import com.app.namasteqr.utils.api.StandardResponse;
import jakarta.validation.Valid;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
@AllArgsConstructor
public class UserController implements IUserController {

    private final ICustomerMenuService customerMenuService;
    private final IOrderService orderService;

    @GetMapping("/menu/{tableToken}")
    @Override
    public ResponseEntity<StandardResponse<CustomerMenuResponseDTO>> getMenu(
            @PathVariable String tableToken
    ) {


        CustomerMenuResponseDTO responseDTO =
                customerMenuService.getMenu(tableToken);

        StandardResponse<CustomerMenuResponseDTO> response =
                StandardResponse.<CustomerMenuResponseDTO>builder()
                        .success(true)
                        .message("Menu fetched successfully.")
                        .data(responseDTO)
                        .errors(null)
                        .httpStatus(HttpStatus.OK)
                        .build();

        return ResponseEntity.ok(response);
    }

    @Override
    @PostMapping("/order/{tableToken}")
    public ResponseEntity<StandardResponse<OrderResponseDTO>> placeOrder(
            @PathVariable String tableToken,
            @Valid @RequestBody List<OrderItemRequestDTO> items
    ) {
        OrderResponseDTO responseDTO = orderService.placeOrder(tableToken, items);

        StandardResponse<OrderResponseDTO> response =
                StandardResponse.<OrderResponseDTO>builder()
                        .success(true)
                        .message("Order placed successfully.")
                        .data(responseDTO)
                        .errors(null)
                        .httpStatus(HttpStatus.CREATED)
                        .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
