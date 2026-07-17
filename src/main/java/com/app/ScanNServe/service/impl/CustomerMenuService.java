package com.app.ScanNServe.service.impl;

import com.app.ScanNServe.domain.entity.RestaurantEntity;
import com.app.ScanNServe.domain.entity.RestaurantMenuItemEntity;
import com.app.ScanNServe.domain.entity.RestaurantTableEntity;
import com.app.ScanNServe.domain.repository.IRestaurantMenuRepository;
import com.app.ScanNServe.domain.repository.IRestaurantTableRepository;
import com.app.ScanNServe.dto.response.CustomerMenuResponseDTO;
import com.app.ScanNServe.exception.ResourceNotFoundException;
import com.app.ScanNServe.service.ICustomerMenuService;
import com.app.ScanNServe.transformer.CustomerMenuTransfomer;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@AllArgsConstructor
public class CustomerMenuService implements ICustomerMenuService {

    private final CustomerMenuTransfomer customerMenuTransformer;
    private final IRestaurantTableRepository restaurantTableRepository;
    private final IRestaurantMenuRepository restaurantMenuRepository;
    @Override
    @Transactional
    public CustomerMenuResponseDTO getMenu(
            String tableToken
    ) {

        RestaurantTableEntity table =
                restaurantTableRepository
                        .findByTableTokenAndIsDeletedFalse(
                                tableToken
                        )
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Invalid QR Code."
                                ));

        RestaurantEntity restaurant =
                table.getRestaurant();

        if (!Boolean.TRUE.equals(restaurant.getIsActive())) {

            throw new ResourceNotFoundException(
                    "Restaurant is currently unavailable."
            );
        }
        System.out.println(restaurant.getName());

        List<RestaurantMenuItemEntity> menuItems =
                restaurantMenuRepository
                        .findAllByRestaurantAndAvailableTrueAndIsDeletedFalseOrderByCreatedAtAsc(
                                restaurant
                        );
        System.out.println(menuItems.size());
        return customerMenuTransformer.toDto(
                restaurant,
                table,
                menuItems
        );
    }
}
