package com.app.namasteqr.domain.repository;

import com.app.namasteqr.domain.entity.FoodCategoryEntity;
import com.app.namasteqr.domain.entity.FoodItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IFoodItemRepository
        extends JpaRepository<FoodItemEntity, Long> {

    boolean existsByCategoryAndNameIgnoreCaseAndIsDeletedFalse(
            FoodCategoryEntity category,
            String name
    );
    List<FoodItemEntity> findAllByIsDeletedFalseOrderByCategoryNameAscNameAsc();
    Optional<FoodItemEntity> findByItemIdAndIsDeletedFalse(Long itemId);
    List<FoodItemEntity> findAllByCategoryAndIsDeletedFalseOrderByNameAsc(FoodCategoryEntity category);
    List<FoodItemEntity> findTop6ByNameStartingWithIgnoreCaseAndIsDeletedFalse(String name);
}

