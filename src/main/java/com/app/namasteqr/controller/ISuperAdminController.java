package com.app.namasteqr.controller;

import com.app.namasteqr.dto.request.AdminRequestDTO;
import com.app.namasteqr.dto.request.PropertyRequestDTO;
import com.app.namasteqr.dto.request.RestaurantRequestDTO;
import com.app.namasteqr.dto.request.UserRequestDTO;
import com.app.namasteqr.dto.response.AdminResponseDTO;
import com.app.namasteqr.dto.response.PropertyResponseDTO;
import com.app.namasteqr.dto.response.RestaurantResponseDTO;
import com.app.namasteqr.dto.response.UserResponseDTO;
import com.app.namasteqr.utils.api.StandardResponse;
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


    ResponseEntity<StandardResponse<List<RestaurantResponseDTO>>> getRestaurants();

    ResponseEntity<StandardResponse<List<AdminResponseDTO>>> getAdmins();
}
