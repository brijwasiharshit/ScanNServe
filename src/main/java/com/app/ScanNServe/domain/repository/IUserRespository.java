package com.app.ScanNServe.domain.repository;

import com.app.ScanNServe.domain.entity.UserEntity;
import com.app.ScanNServe.domain.entity.UserEntityId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUserRespository extends JpaRepository<UserEntity, UserEntityId> {

    Optional<UserEntity> findByUsernameAndEmailAddress(String username, String emailAddress);

    Optional<UserEntity> findByEmailAddress(String emailAddress);

    Optional<UserEntity> findFirstByUsername(String username);

    Optional<UserEntity> findFirstByRole(com.app.ScanNServe.utils.enums.Role role);
}
