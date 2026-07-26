package com.app.namasteqr.transformer;

import com.app.namasteqr.domain.entity.OrderEntity;
import com.app.namasteqr.domain.entity.OrderItemEntity;
import com.app.namasteqr.dto.response.OrderResponseDTO;

import java.util.List;
import java.util.stream.Collectors;

public class OrderTransformer {

    public static OrderResponseDTO toOrderResponseDTO(OrderEntity order) {
        if (order == null) {
            return null;
        }

        List<OrderResponseDTO.OrderItemDTO> itemDTOs = order.getOrderItems().stream()
                .map(OrderTransformer::toOrderItemDTO)
                .collect(Collectors.toList());

        return OrderResponseDTO.builder()
                .orderId(order.getOrderId())
                .restaurantId(order.getRestaurant().getRestaurantId())
                .tableNumber(order.getTable().getTableNumber())
                .totalAmount(order.getTotalAmount())
                .createdAt(order.getCreatedAt())
                .items(itemDTOs)
                .build();
    }

    private static OrderResponseDTO.OrderItemDTO toOrderItemDTO(OrderItemEntity orderItem) {
        if (orderItem == null) {
            return null;
        }

        return OrderResponseDTO.OrderItemDTO.builder()
                .orderItemId(orderItem.getOrderItemId())
                .restaurantMenuItemId(orderItem.getMenuItem().getRestaurantMenuItemId())
                .itemName(orderItem.getMenuItem().getFoodItem().getName())
                .quantity(orderItem.getQuantity())
                .price(orderItem.getPrice())
                .build();
    }
}
