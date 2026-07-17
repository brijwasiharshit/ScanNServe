package com.app.ScanNServe.controller;

import com.app.ScanNServe.dto.request.AdminRequestDTO;
import com.app.ScanNServe.dto.request.PropertyRequestDTO;
import com.app.ScanNServe.dto.request.RestaurantRequestDTO;
import com.app.ScanNServe.dto.request.UserRequestDTO;
import com.app.ScanNServe.dto.response.AdminResponseDTO;
import com.app.ScanNServe.dto.response.PropertyResponseDTO;
import com.app.ScanNServe.dto.response.RestaurantResponseDTO;
import com.app.ScanNServe.dto.response.UserResponseDTO;
import com.app.ScanNServe.utils.api.StandardResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


public interface ISuperAdminController {


    @PostMapping("/restaurants")
    ResponseEntity<StandardResponse<RestaurantResponseDTO>> createRestaurant(
            @Valid @RequestBody RestaurantRequestDTO requestDTO
    );

    ResponseEntity<StandardResponse<AdminResponseDTO>> createAdmin(
            @Valid @RequestBody AdminRequestDTO requestDTO
    );



}
