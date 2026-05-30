package com.app.ScanNServe.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "food_details")
public class FoodItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "food_item_seq")
    @SequenceGenerator(
            name = "food_item_seq",
            sequenceName = "food_item_seq",
            allocationSize = 50
    )
    private Long id;
    @Column(name = "name", length = 100, nullable = false)
    private String name;

    @Column(name = "img_link", length = 2048)
    private String imgLink;

    @Column(name = "is_veg", nullable = false)
    private Boolean isVeg;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "food_category_id", nullable = false)
    private FoodCategoryEntity foodCategory;


}
