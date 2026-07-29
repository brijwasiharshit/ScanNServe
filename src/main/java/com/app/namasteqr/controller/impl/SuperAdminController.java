package com.app.namasteqr.controller.impl;

import com.app.namasteqr.controller.ISuperAdminController;
import com.app.namasteqr.dto.request.AdminRequestDTO;
import com.app.namasteqr.dto.request.PropertyRequestDTO;
import com.app.namasteqr.dto.request.RestaurantRequestDTO;
import com.app.namasteqr.dto.request.UserRequestDTO;
import com.app.namasteqr.dto.response.AdminResponseDTO;
import com.app.namasteqr.dto.response.PropertyResponseDTO;
import com.app.namasteqr.dto.response.RestaurantResponseDTO;
import com.app.namasteqr.dto.response.UserResponseDTO;
import com.app.namasteqr.service.IManageAdminService;
import com.app.namasteqr.service.IPropertyService;
import com.app.namasteqr.service.IRestaurantService;
import com.app.namasteqr.service.IUserService;
import com.app.namasteqr.utils.api.StandardResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/super")
@AllArgsConstructor
public class SuperAdminController implements ISuperAdminController {

    private final IRestaurantService restaurantService;
    private final IUserService userService;
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @PostMapping("/restaurants")
    @Override
    public ResponseEntity<StandardResponse<RestaurantResponseDTO>> createRestaurant(
            @Valid @RequestBody RestaurantRequestDTO requestDTO
    ) {

        RestaurantResponseDTO responseDTO =
                restaurantService.createRestaurant(requestDTO);

        StandardResponse<RestaurantResponseDTO> response =
                StandardResponse.<RestaurantResponseDTO>builder()
                        .success(true)
                        .message("Restaurant created successfully")
                        .data(responseDTO)
                        .errors(null)
                        .httpStatus(HttpStatus.CREATED)
                        .build();

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }


    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @PostMapping("/admins")
    @Override
    public ResponseEntity<StandardResponse<AdminResponseDTO>> createAdmin(
            @Valid @RequestBody AdminRequestDTO requestDTO
    ) {

        AdminResponseDTO responseDTO =
                userService.createAdmin(requestDTO);

        StandardResponse<AdminResponseDTO> response =
                StandardResponse.<AdminResponseDTO>builder()
                        .success(true)
                        .message("Admin created successfully")
                        .data(responseDTO)
                        .errors(null)
                        .httpStatus(HttpStatus.CREATED)
                        .build();

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @GetMapping("/restaurants")
    @Override
    public ResponseEntity<StandardResponse<List<RestaurantResponseDTO>>> getRestaurants() {
        List<RestaurantResponseDTO> data = restaurantService.getAllRestaurants();
        StandardResponse<List<RestaurantResponseDTO>> response = StandardResponse.<List<RestaurantResponseDTO>>builder()
                .success(true)
                .message("Restaurants fetched successfully")
                .data(data)
                .httpStatus(HttpStatus.OK)
                .build();
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @GetMapping("/admins")
    @Override
    public ResponseEntity<StandardResponse<List<AdminResponseDTO>>> getAdmins() {
        List<AdminResponseDTO> data = userService.getAllAdmins();
        StandardResponse<List<AdminResponseDTO>> response = StandardResponse.<List<AdminResponseDTO>>builder()
                .success(true)
                .message("Admins fetched successfully")
                .data(data)
                .httpStatus(HttpStatus.OK)
                .build();
        return ResponseEntity.ok(response);
    }
}
