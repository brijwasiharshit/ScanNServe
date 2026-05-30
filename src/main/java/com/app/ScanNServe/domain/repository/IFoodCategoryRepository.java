package com.app.ScanNServe.domain.repository;

import com.app.ScanNServe.domain.entity.FoodCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IFoodCategoryRepository extends JpaRepository<FoodCategoryEntity, Long> {

    boolean existsByName(String name);
}

