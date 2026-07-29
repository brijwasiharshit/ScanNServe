package com.app.namasteqr.domain.repository;

import com.app.namasteqr.domain.entity.RestaurantEntity;
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
    java.util.List<RestaurantEntity> findAllByIsDeletedFalseOrderByNameAsc();

}
