package com.app.ScanNServe.controller.impl;

import com.app.ScanNServe.controller.IRestaurantAdminController;
import com.app.ScanNServe.dto.request.RestaurantMenuItemRequestDTO;
import com.app.ScanNServe.dto.request.RestaurantTableRequestDTO;
import com.app.ScanNServe.dto.request.UpdateRestaurantMenuItemRequestDTO;
import com.app.ScanNServe.dto.response.ItemSearchResponseDTO;
import com.app.ScanNServe.dto.response.RestaurantMenuItemResponseDTO;
import com.app.ScanNServe.dto.response.RestaurantResponseDTO;
import com.app.ScanNServe.dto.response.RestaurantTableResponseDTO;
import com.app.ScanNServe.service.IFoodItemService;
import com.app.ScanNServe.service.IRestaurantMenuService;
import com.app.ScanNServe.service.IRestaurantService;
import com.app.ScanNServe.service.IRestaurantTableService;
import com.app.ScanNServe.service.impl.FoodItemService;
import com.app.ScanNServe.utils.api.StandardResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin")
@AllArgsConstructor
public class RestaurantAdminController implements IRestaurantAdminController {
    private final IRestaurantService restaurantService;
    private final IRestaurantMenuService restaurantMenuService;
    private final IFoodItemService foodItemService;
    private final IRestaurantTableService restaurantTableService;
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/restaurant")
    @Override
    public ResponseEntity<StandardResponse<RestaurantResponseDTO>> getRestaurant() {

        RestaurantResponseDTO responseDTO =
                restaurantService.getRestaurant();

        StandardResponse<RestaurantResponseDTO> response =
                StandardResponse.<RestaurantResponseDTO>builder()
                        .success(true)
                        .message("Restaurant fetched successfully")
                        .data(responseDTO)
                        .errors(null)
                        .httpStatus(HttpStatus.OK)
                        .build();

        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/items/search")
    @Override
    public ResponseEntity<StandardResponse<List<ItemSearchResponseDTO>>> searchItems(
            @RequestParam String keyword
    ) {

        List<ItemSearchResponseDTO> responseDTO =
                foodItemService.searchItems(keyword);

        StandardResponse<List<ItemSearchResponseDTO>> response =
                StandardResponse.<List<ItemSearchResponseDTO>>builder()
                        .success(true)
                        .message("Items fetched successfully.")
                        .data(responseDTO)
                        .errors(null)
                        .httpStatus(HttpStatus.OK)
                        .build();

        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("menu/subscribe/items")
    @Override
    public ResponseEntity<StandardResponse<RestaurantMenuItemResponseDTO>> subscribeItem(
            @Valid @RequestBody RestaurantMenuItemRequestDTO requestDTO
    ) {

        RestaurantMenuItemResponseDTO responseDTO =
                restaurantMenuService.subscribeItem(requestDTO);

        StandardResponse<RestaurantMenuItemResponseDTO> response =
                StandardResponse.<RestaurantMenuItemResponseDTO>builder()
                        .success(true)
                        .message("Item subscribed successfully.")
                        .data(responseDTO)
                        .errors(null)
                        .httpStatus(HttpStatus.CREATED)
                        .build();

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/menu/subscribe/items")
    @Override
    public ResponseEntity<StandardResponse<List<RestaurantMenuItemResponseDTO>>> getMenu() {

        List<RestaurantMenuItemResponseDTO> responseDTO =
                restaurantMenuService.getMenu();

        StandardResponse<List<RestaurantMenuItemResponseDTO>> response =
                StandardResponse.<List<RestaurantMenuItemResponseDTO>>builder()
                        .success(true)
                        .message("Restaurant menu fetched successfully.")
                        .data(responseDTO)
                        .errors(null)
                        .httpStatus(HttpStatus.OK)
                        .build();

        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("menu/subscribe/items/{itemId}")
    @Override
    public ResponseEntity<StandardResponse<RestaurantMenuItemResponseDTO>> getMenuItem(
            @PathVariable Long itemId
    ) {

        RestaurantMenuItemResponseDTO responseDTO =
                restaurantMenuService.getMenuItem(itemId);

        StandardResponse<RestaurantMenuItemResponseDTO> response =
                StandardResponse.<RestaurantMenuItemResponseDTO>builder()
                        .success(true)
                        .message("Menu item fetched successfully.")
                        .data(responseDTO)
                        .errors(null)
                        .httpStatus(HttpStatus.OK)
                        .build();

        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("menu/subscribe/items/{itemId}")
    @Override
    public ResponseEntity<StandardResponse<RestaurantMenuItemResponseDTO>> updateMenuItem(
            @PathVariable Long itemId,
            @RequestBody UpdateRestaurantMenuItemRequestDTO requestDTO
    ) {
        RestaurantMenuItemResponseDTO responseDTO =
                restaurantMenuService.updateMenuItem(
                        itemId,
                        requestDTO
                );

        StandardResponse<RestaurantMenuItemResponseDTO> response =
                StandardResponse.<RestaurantMenuItemResponseDTO>builder()
                        .success(true)
                        .message("Menu item updated successfully.")
                        .data(responseDTO)
                        .errors(null)
                        .httpStatus(HttpStatus.OK)
                        .build();

        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("menu/subscribe/items/{itemId}")
    @Override
    public ResponseEntity<StandardResponse<Void>> removeMenuItem(
            @PathVariable Long itemId
    ) {

        restaurantMenuService.removeMenuItem(itemId);

        StandardResponse<Void> response =
                StandardResponse.<Void>builder()
                        .success(true)
                        .message("Menu item removed successfully.")
                        .data(null)
                        .errors(null)
                        .httpStatus(HttpStatus.NO_CONTENT)
                        .build();

        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .body(response);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("tables")
    @Override
    public ResponseEntity<StandardResponse<RestaurantTableResponseDTO>> createTable(
            @Valid @RequestBody RestaurantTableRequestDTO requestDTO
    ) {

        RestaurantTableResponseDTO responseDTO =
                restaurantTableService.createTable(requestDTO);

        StandardResponse<RestaurantTableResponseDTO> response =
                StandardResponse.<RestaurantTableResponseDTO>builder()
                        .success(true)
                        .message("Table created successfully.")
                        .data(responseDTO)
                        .errors(null)
                        .httpStatus(HttpStatus.CREATED)
                        .build();

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(response);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/tables")
    @Override
    public ResponseEntity<StandardResponse<List<RestaurantTableResponseDTO>>> getTables() {

        List<RestaurantTableResponseDTO> responseDTO =
                restaurantTableService.getTables();

        StandardResponse<List<RestaurantTableResponseDTO>> response =
                StandardResponse.<List<RestaurantTableResponseDTO>>builder()
                        .success(true)
                        .message("Restaurant tables fetched successfully.")
                        .data(responseDTO)
                        .errors(null)
                        .httpStatus(HttpStatus.OK)
                        .build();

        return ResponseEntity.ok(response);
    }

}

