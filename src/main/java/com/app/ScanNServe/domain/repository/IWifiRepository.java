package com.app.ScanNServe.domain.repository;

import com.app.ScanNServe.domain.entity.PropertyEntity;
import com.app.ScanNServe.domain.entity.WifiEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IWifiRepository extends JpaRepository<WifiEntity, Long> {

    Optional<WifiEntity> findByPropertyAndCoverage(PropertyEntity property, String coverage);

    List<WifiEntity> findByProperty(PropertyEntity property);
}
