package com.app.ScanNServe.domain.entity;

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
@Table(name = "wifi_details")
public class WifiEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "wifi_seq")
    @SequenceGenerator(
            name = "wifi_seq",
            sequenceName = "wifi_seq",
            allocationSize = 10
    )
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "property_id", nullable = false)
    private PropertyEntity property;

    private String ssid;

    private String hashedPassword;

}
