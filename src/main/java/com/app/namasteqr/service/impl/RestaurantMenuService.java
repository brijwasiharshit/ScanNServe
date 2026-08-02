package com.app.namasteqr.service.impl;

import com.app.namasteqr.domain.entity.FoodItemEntity;
import com.app.namasteqr.domain.entity.RestaurantEntity;
import com.app.namasteqr.domain.entity.RestaurantMenuItemEntity;
import com.app.namasteqr.domain.repository.IFoodItemRepository;
import com.app.namasteqr.domain.repository.IRestaurantMenuRepository;
import com.app.namasteqr.domain.repository.IRestaurantRepository;
import com.app.namasteqr.dto.request.RestaurantMenuItemRequestDTO;
import com.app.namasteqr.dto.request.UpdateRestaurantMenuItemRequestDTO;
import com.app.namasteqr.dto.response.RestaurantMenuItemResponseDTO;
import com.app.namasteqr.exception.ResourceNotFoundException;
import com.app.namasteqr.exception.ResourseAlreadyExistsException;
import com.app.namasteqr.service.IRestaurantMenuService;
import com.app.namasteqr.transformer.RestaurantMenuTransformer;
import com.app.namasteqr.utils.jwt.UserPrincipal;
import com.app.namasteqr.utils.security.SecurityContextUtil;
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

        java.util.Optional<RestaurantMenuItemEntity> existingOpt =
                restaurantMenuRepository.findByRestaurantAndFoodItem(restaurant, foodItem);

        if (existingOpt.isPresent()) {
            RestaurantMenuItemEntity existing = existingOpt.get();
            if (!existing.getIsDeleted()) {
                throw new ResourseAlreadyExistsException(
                        "Item already exists in restaurant menu."
                );
            } else {
                // Restore soft-deleted item and update its fields
                existing.setIsDeleted(false);
                existing.setPrice(requestDTO.getPrice());
                
                if (requestDTO.getCustomImage() != null) {
                    existing.setCustomImage(requestDTO.getCustomImage().trim());
                }
                
                if (requestDTO.getAvailable() != null) {
                    existing.setAvailable(requestDTO.getAvailable());
                }
                
                existing.setTag(requestDTO.getTag());
                
                RestaurantMenuItemEntity saved = restaurantMenuRepository.save(existing);
                return restaurantMenuTransformer.toDto(saved);
            }
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
                        .findAllByRestaurantAndIsDeletedFalseOrderByCreatedAtAscRestaurantMenuItemIdAsc(
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
