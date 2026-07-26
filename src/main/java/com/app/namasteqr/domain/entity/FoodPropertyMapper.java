package com.app.namasteqr.domain.entity;

import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;

public class FoodPropertyMapper {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "food_property_seq")
    @SequenceGenerator(
            name = "food_property_seq",
            sequenceName = "food_property_seq",
            allocationSize = 50
    )

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "food_id", nullable = false)
    private Long foodIdFk;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "property_id", nullable = false)
    private Long propertyIdFk;

    private Integer priceForQuarter;
    private Integer priceForHalf;
    private Integer priceForFull;
    private Boolean isAvl;
}
