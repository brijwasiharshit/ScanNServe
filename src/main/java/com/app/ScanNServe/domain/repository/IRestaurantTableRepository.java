package com.app.ScanNServe.domain.repository;

import com.app.ScanNServe.domain.entity.RestaurantEntity;
import com.app.ScanNServe.domain.entity.RestaurantTableEntity;
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
