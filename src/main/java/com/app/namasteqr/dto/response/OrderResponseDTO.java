package com.app.namasteqr.dto.response;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class OrderResponseDTO {
    private Long orderId;
    private Long restaurantId;
    private String tableNumber;
    private BigDecimal totalAmount;
    private LocalDateTime createdAt;
    private List<OrderItemDTO> items;

    @Data
    @Builder
    public static class OrderItemDTO {
        private Long orderItemId;
        private Long restaurantMenuItemId;
        private String itemName;
        private Integer quantity;
        private BigDecimal price;
    }
}
