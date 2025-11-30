package com.app.ScanNServe;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name="table_1")
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

}
