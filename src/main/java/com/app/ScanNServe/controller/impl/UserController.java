package com.app.ScanNServe.controller.impl;

import com.app.ScanNServe.dto.response.FoodItemResponseDTO;
import com.app.ScanNServe.dto.response.WifiResponseDTO;
import com.app.ScanNServe.utils.api.StandardResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    @GetMapping("/{propertyId}/items")
    ResponseEntity<StandardResponse<WifiResponseDTO>> getFoodItem(
            @PathVariable Long propertyId,
            @RequestBody FoodItemResponseDTO foodItemResponseDTO
    ){
    return null;
    }
}
