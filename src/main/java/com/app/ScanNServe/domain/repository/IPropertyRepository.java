package com.app.ScanNServe.domain.repository;

import com.app.ScanNServe.domain.entity.PropertyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IPropertyRepository extends JpaRepository<PropertyEntity, Long> {
    boolean existsByName(String name);
    Optional<PropertyEntity> findByName(String name);
}

