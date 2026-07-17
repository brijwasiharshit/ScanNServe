package com.app.ScanNServe.controller.impl;

import com.app.ScanNServe.controller.IUserController;
import com.app.ScanNServe.dto.response.CustomerMenuResponseDTO;
import com.app.ScanNServe.dto.response.FoodItemResponseDTO;
import com.app.ScanNServe.dto.response.WifiResponseDTO;
import com.app.ScanNServe.service.ICustomerMenuService;
import com.app.ScanNServe.utils.api.StandardResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
@AllArgsConstructor
public class UserController implements IUserController {

    private final ICustomerMenuService customerMenuService;

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
}
