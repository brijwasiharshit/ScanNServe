package com.app.ScanNServe.domain.repository;

import com.app.ScanNServe.domain.entity.PropertyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPropertyRepository extends JpaRepository<PropertyEntity, Long> {
}

