    package com.app.namasteqr.domain.entity;

    import jakarta.persistence.*;
    import lombok.*;
    import org.hibernate.annotations.DynamicUpdate;
    import org.springframework.data.annotation.CreatedDate;
    import org.springframework.data.annotation.LastModifiedDate;
    import org.springframework.data.jpa.domain.support.AuditingEntityListener;

    import java.math.BigDecimal;
    import java.time.LocalDateTime;

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Entity
    @DynamicUpdate
    @EntityListeners(AuditingEntityListener.class)
    @Table(
            name = "restaurant_menu_item",
            uniqueConstraints = {
                    @UniqueConstraint(
                            name = "uk_restaurant_menu_item",
                            columnNames = {"restaurant_id", "item_id"}
                    )
            }
    )
    public class RestaurantMenuItemEntity {

        @Id
        @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "restaurant_menu_item_seq")
        @SequenceGenerator(
                name = "restaurant_menu_item_seq",
                sequenceName = "restaurant_menu_item_seq",
                allocationSize = 50
        )
        @Column(name = "restaurant_menu_item_id")
        private Long restaurantMenuItemId;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(
                name = "restaurant_id",
                nullable = false,
                foreignKey = @ForeignKey(name = "fk_menu_item_restaurant")
        )
        private RestaurantEntity restaurant;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(
                name = "item_id",
                nullable = false,
                foreignKey = @ForeignKey(name = "fk_menu_item_food_item")
        )
        private FoodItemEntity foodItem;

        @Column(name = "price", nullable = false, precision = 10, scale = 2)
        private BigDecimal price;

        @Column(name = "custom_image", columnDefinition = "TEXT")
        private String customImage;

        @Builder.Default
        @Column(name = "available", nullable = false)
        private Boolean available = true;

        @CreatedDate
        @Column(name = "created_at", nullable = false, updatable = false)
        private LocalDateTime createdAt;

        @LastModifiedDate
        @Column(name = "updated_at")
        private LocalDateTime updatedAt;

        @Builder.Default
        @Column(name = "is_deleted", nullable = false)
        private Boolean isDeleted = false;
    }
