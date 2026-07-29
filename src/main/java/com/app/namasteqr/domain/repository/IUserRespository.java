package com.app.namasteqr.domain.repository;

import com.app.namasteqr.domain.entity.UserEntity;
import com.app.namasteqr.domain.entity.UserEntityId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUserRespository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByUsernameAndEmailAddress(String username, String emailAddress);

    Optional<UserEntity> findByEmailAddress(String emailAddress);

    Optional<UserEntity> findFirstByUsername(String username);

    Optional<UserEntity> findFirstByRole(com.app.namasteqr.utils.enums.Role role);
    
    java.util.List<UserEntity> findAllByRole(com.app.namasteqr.utils.enums.Role role);
}
