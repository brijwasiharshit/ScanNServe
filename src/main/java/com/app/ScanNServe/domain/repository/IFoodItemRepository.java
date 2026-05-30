package com.app.ScanNServe.domain.repository;

import com.app.ScanNServe.domain.entity.FoodItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IFoodItemRepository extends JpaRepository<FoodItemEntity, Long> {
}

