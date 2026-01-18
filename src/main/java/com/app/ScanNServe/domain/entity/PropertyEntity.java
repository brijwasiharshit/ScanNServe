package com.app.ScanNServe.domain.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "property_details")
public class PropertyEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "property_seq")
    @SequenceGenerator(
            name = "property_seq",
            sequenceName = "property_seq",
            allocationSize = 50
    )
    private Long id;

    @Column(length = 256, nullable = false)
    private String name;

    @Column(name = "description", length = 1024)
    private String desc;

    @Column(length = 2048)
    private String address;

    @Column(name = "logo_link", length = 2048)
    private String logoLink;

    @OneToMany(
            mappedBy = "property",
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<WifiEntity> wifis;
}
