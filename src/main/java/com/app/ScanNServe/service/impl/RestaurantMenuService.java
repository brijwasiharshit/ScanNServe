package com.app.ScanNServe.service.impl;

import com.app.ScanNServe.domain.entity.FoodItemEntity;
import com.app.ScanNServe.domain.entity.RestaurantEntity;
import com.app.ScanNServe.domain.entity.RestaurantMenuItemEntity;
import com.app.ScanNServe.domain.repository.IFoodItemRepository;
import com.app.ScanNServe.domain.repository.IRestaurantMenuRepository;
import com.app.ScanNServe.domain.repository.IRestaurantRepository;
import com.app.ScanNServe.dto.request.RestaurantMenuItemRequestDTO;
import com.app.ScanNServe.dto.request.UpdateRestaurantMenuItemRequestDTO;
import com.app.ScanNServe.dto.response.RestaurantMenuItemResponseDTO;
import com.app.ScanNServe.exception.ResourceNotFoundException;
import com.app.ScanNServe.exception.ResourseAlreadyExistsException;
import com.app.ScanNServe.service.IRestaurantMenuService;
import com.app.ScanNServe.transformer.RestaurantMenuTransformer;
import com.app.ScanNServe.utils.jwt.UserPrincipal;
import com.app.ScanNServe.utils.security.SecurityContextUtil;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class RestaurantMenuService implements IRestaurantMenuService {

    private final SecurityContextUtil securityContextUtil;
    private final IRestaurantRepository restaurantRepository;
    private final IFoodItemRepository foodItemRepository;
    private final IRestaurantMenuRepository restaurantMenuRepository;
    private final RestaurantMenuTransformer restaurantMenuTransformer;
    @Override
    @Transactional
    public RestaurantMenuItemResponseDTO subscribeItem(
            RestaurantMenuItemRequestDTO requestDTO
    ) {

        UserPrincipal activeUser =
                securityContextUtil.fetchActiveUserDetails();
        System.out.println("Active User");
        System.out.println(activeUser.getId());
        System.out.println(activeUser.getRestaurantId());
        RestaurantEntity restaurant =
                restaurantRepository
                        .findByRestaurantIdAndIsDeletedFalse(
                                activeUser.getRestaurantId()
                        )
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Restaurant not found."
                                ));

        FoodItemEntity foodItem =
                foodItemRepository
                        .findByItemIdAndIsDeletedFalse(
                                requestDTO.getItemId()
                        )
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Food item not found."
                                ));

        if (restaurantMenuRepository
                .existsByRestaurantAndFoodItemAndIsDeletedFalse(
                        restaurant,
                        foodItem
                )) {

            throw new ResourseAlreadyExistsException(
                    "Item already exists in restaurant menu."
            );
        }

        RestaurantMenuItemEntity entity =
                restaurantMenuTransformer.toEntity(
                        requestDTO,
                        restaurant,
                        foodItem
                );

        RestaurantMenuItemEntity saved =
                restaurantMenuRepository.save(entity);

        return restaurantMenuTransformer.toDto(saved);
    }

    @Override
    @Transactional
    public List<RestaurantMenuItemResponseDTO> getMenu() {

        UserPrincipal activeUser =
                securityContextUtil.fetchActiveUserDetails();

        RestaurantEntity restaurant =
                restaurantRepository
                        .findByRestaurantIdAndIsDeletedFalse(
                                activeUser.getRestaurantId()
                        )
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Restaurant not found."
                                ));

        List<RestaurantMenuItemEntity> menuItems =
                restaurantMenuRepository
                        .findAllByRestaurantAndIsDeletedFalseOrderByCreatedAtAsc(
                                restaurant
                        );

        return menuItems.stream()
                .map(restaurantMenuTransformer::toDto)
                .toList();
    }

    @Override
    @Transactional
    public RestaurantMenuItemResponseDTO getMenuItem(
            Long itemId
    ) {

        UserPrincipal activeUser =
                securityContextUtil.fetchActiveUserDetails();

        RestaurantMenuItemEntity menuItem =
                restaurantMenuRepository
                        .findByRestaurantRestaurantIdAndFoodItemItemIdAndIsDeletedFalse(
                                activeUser.getRestaurantId(),
                                itemId
                        )
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Menu item not found."
                                ));

        return restaurantMenuTransformer.toDto(menuItem);
    }

    @Override
    @Transactional
    public RestaurantMenuItemResponseDTO updateMenuItem(
            Long itemId,
            UpdateRestaurantMenuItemRequestDTO requestDTO
    ) {

        UserPrincipal activeUser =
                securityContextUtil.fetchActiveUserDetails();

        RestaurantMenuItemEntity menuItem =
                restaurantMenuRepository
                        .findByRestaurantRestaurantIdAndFoodItemItemIdAndIsDeletedFalse(
                                activeUser.getRestaurantId(),
                                itemId
                        )
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Subscribed menu item not found."
                                ));

        restaurantMenuTransformer.updateEntity(
                menuItem,
                requestDTO
        );

        RestaurantMenuItemEntity saved =
                restaurantMenuRepository.save(menuItem);

        return restaurantMenuTransformer.toDto(saved);
    }

    @Override
    @Transactional
    public void removeMenuItem(
            Long itemId
    ) {

        UserPrincipal activeUser =
                securityContextUtil.fetchActiveUserDetails();

        RestaurantMenuItemEntity menuItem =
                restaurantMenuRepository
                        .findByRestaurantRestaurantIdAndFoodItemItemIdAndIsDeletedFalse(
                                activeUser.getRestaurantId(),
                                itemId
                        )
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Menu item not found."
                                ));

        restaurantMenuTransformer.softDelete(menuItem);

        restaurantMenuRepository.save(menuItem);
    }

}
