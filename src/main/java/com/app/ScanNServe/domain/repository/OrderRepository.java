package com.app.ScanNServe.domain.repository;

import com.app.ScanNServe.domain.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {

    @Query("SELECT SUM(o.totalAmount) FROM OrderEntity o WHERE o.restaurant.restaurantId = :restaurantId AND o.isDeleted = false")
    BigDecimal getTotalSalesAllTime(@Param("restaurantId") Long restaurantId);

    @Query("SELECT COUNT(o) FROM OrderEntity o WHERE o.restaurant.restaurantId = :restaurantId AND o.createdAt >= :startDate AND o.createdAt <= :endDate AND o.isDeleted = false")
    Long countOrdersBetween(@Param("restaurantId") Long restaurantId, @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    @Query("SELECT SUM(o.totalAmount) FROM OrderEntity o WHERE o.restaurant.restaurantId = :restaurantId AND o.createdAt >= :startDate AND o.createdAt <= :endDate AND o.isDeleted = false")
    BigDecimal sumSalesBetween(@Param("restaurantId") Long restaurantId, @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    @Query("SELECT o FROM OrderEntity o WHERE o.restaurant.restaurantId = :restaurantId AND o.createdAt >= :startDate AND o.createdAt <= :endDate AND o.isDeleted = false")
    List<OrderEntity> findOrdersBetween(@Param("restaurantId") Long restaurantId, @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
}
