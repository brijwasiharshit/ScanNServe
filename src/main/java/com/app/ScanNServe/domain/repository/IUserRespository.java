package com.app.ScanNServe.domain.repository;

import com.app.ScanNServe.domain.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUserRespository extends JpaRepository<UserEntity,Long> {
    Optional<UserEntity> findByName(String name);
    UserEntity findByRole(String role);
}
