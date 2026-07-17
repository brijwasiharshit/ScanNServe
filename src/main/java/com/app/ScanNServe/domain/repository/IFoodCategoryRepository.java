package com.app.ScanNServe.domain.repository;

import com.app.ScanNServe.domain.entity.FoodCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IFoodCategoryRepository extends JpaRepository<FoodCategoryEntity, Long> {

    boolean existsByNameIgnoreCase(String name);
    List<FoodCategoryEntity> findAllByIsDeletedFalseOrderByNameAsc();
    Optional<FoodCategoryEntity> findByCategoryIdAndIsDeletedFalse(Long categoryId);

}

