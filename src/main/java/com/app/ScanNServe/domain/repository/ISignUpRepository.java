package com.app.ScanNServe.domain.repository;


import com.app.ScanNServe.domain.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ISignUpRepository extends JpaRepository<UserEntity, UUID> {
}
