package com.app.ScanNServe.domain.repository;

import com.app.ScanNServe.domain.entity.RestaurantEntity;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface IRestaurantRepository
        extends JpaRepository<RestaurantEntity, Long> {

    boolean existsByNameIgnoreCaseAndIsDeletedFalse(
            String name
    );
    Optional<RestaurantEntity> findByRestaurantIdAndIsDeletedFalse(
            Long restaurantId
    );

}