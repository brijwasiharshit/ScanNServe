package com.app.namasteqr.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.annotation.LastModifiedDate;
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
        name = "restaurant_table",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_restaurant_table_number",
                        columnNames = {
                                "restaurant_id",
                                "table_number"
                        }
                )
        }
)
public class RestaurantTableEntity {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "restaurant_table_seq"
    )
    @SequenceGenerator(
            name = "restaurant_table_seq",
            sequenceName = "restaurant_table_seq",
            allocationSize = 50
    )
    @Column(name = "table_id")
    private Long tableId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "restaurant_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_restaurant_table_restaurant")
    )
    private RestaurantEntity restaurant;

    @Column(name = "table_number", nullable = false, length = 30)
    private String tableNumber;

    @Column(name = "table_token", nullable = false, unique = true, length = 100)
    private String tableToken;

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
