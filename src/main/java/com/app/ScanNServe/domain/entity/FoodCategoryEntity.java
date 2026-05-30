package com.app.ScanNServe.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "food_category")
public class FoodCategoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "food_category_seq")
    @SequenceGenerator(
            name = "food_category_seq",
            sequenceName = "food_category_seq",
            allocationSize = 50
    )
    private Long id;

    @Column(name = "name", length = 100, nullable = false, unique = true)
    private String name;
}

