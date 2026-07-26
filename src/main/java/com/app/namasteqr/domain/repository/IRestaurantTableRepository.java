package com.app.namasteqr.domain.repository;

import com.app.namasteqr.domain.entity.RestaurantEntity;
import com.app.namasteqr.domain.entity.RestaurantTableEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IRestaurantTableRepository extends JpaRepository<RestaurantTableEntity, Long> {
    boolean existsByRestaurantAndTableNumberIgnoreCaseAndIsDeletedFalse(
            RestaurantEntity restaurant,
            String tableNumber
    );

    List<RestaurantTableEntity>
    findAllByRestaurantAndIsDeletedFalseOrderByTableNumberAsc(
            RestaurantEntity restaurant
    );
    Optional<RestaurantTableEntity> findByTableTokenAndIsDeletedFalse(
            String tableToken
    );
}
