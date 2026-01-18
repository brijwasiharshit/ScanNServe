package com.app.ScanNServe.domain.entity;

import com.app.ScanNServe.utils.enums.Role;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.util.UUID;

@Entity
@Data
@Table(name="user_table")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "user_seq_gen")
    @SequenceGenerator(
            name = "user_seq_gen",
            sequenceName = "user_seq_gen",
            allocationSize = 50
    )
    private Long id;

    @Column(nullable = false, length = 256)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Role role;

    @Column(name = "property_id_fk", nullable = false)
    private Long propertyIdFk;

    @Column(name = "address", length = 2048)
    private String address;

    @Column(name = "email_address", nullable = false, length = 256)
    private String emailAddress;

    @Column(name = "contact_number", length = 15)
    private String contactNumber;
}
