package com.app.ScanNServe.domain.entity;

import com.app.ScanNServe.utils.enums.Category;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

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


    @Column(name = "img_link", length = 2048)
    private String imgLink;

    @Column(name = "is_veg", nullable = false)
    private Boolean isVeg;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private Category category;
}
