package com.app.namasteqr.domain.repository;

import com.app.namasteqr.domain.entity.FoodItemEntity;
import com.app.namasteqr.domain.entity.RestaurantEntity;
import com.app.namasteqr.domain.entity.RestaurantMenuItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IRestaurantMenuRepository extends JpaRepository<RestaurantMenuItemEntity, Long> {
    boolean existsByRestaurantAndFoodItemAndIsDeletedFalse(
            RestaurantEntity restaurant,
            FoodItemEntity foodItem
    );

    Optional<RestaurantMenuItemEntity> findByRestaurantAndFoodItem(
            RestaurantEntity restaurant,
            FoodItemEntity foodItem
    );
    List<RestaurantMenuItemEntity> findAllByRestaurantAndIsDeletedFalseOrderByCreatedAtAsc(
            RestaurantEntity restaurant
    );

    Optional<RestaurantMenuItemEntity>
    findByRestaurantRestaurantIdAndFoodItemItemIdAndIsDeletedFalse(
            Long restaurantId,
            Long itemId
    );
    List<RestaurantMenuItemEntity>
    findAllByRestaurantAndAvailableTrueAndIsDeletedFalseOrderByCreatedAtAsc(
            RestaurantEntity restaurant
    );
}
