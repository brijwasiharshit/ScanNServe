package com.app.ScanNServe.service.impl;

import com.app.ScanNServe.domain.entity.UserEntity;
import com.app.ScanNServe.domain.repository.IUserRespository;
import com.app.ScanNServe.utils.enums.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class AdminUserInitializer implements CommandLineRunner {

    private final IUserRespository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {

        createUserIfNotExists(
                "superadmin@scannserve.com",
                "Super Admin",
                Role.SUPER_ADMIN
        );

        createUserIfNotExists(
                "admin@scannserve.com",
                "Admin User",
                Role.ADMIN
        );

        createUserIfNotExists(
                "user@scannserve.com",
                "Normal User",
                Role.USER
        );
    }

    private void createUserIfNotExists(String email, String username, Role role) {

        if (userRepository.findByUsernameAndEmailAddress(username, email).isEmpty()) {

            UserEntity user = new UserEntity();
            user.setUsername(username);
            user.setEmailAddress(email);
            user.setHashedPassword(passwordEncoder.encode("Password@123"));
            user.setRole(role);
            user.setPropertyIdFk(0L);
            user.setContactNumber("9999999999");
            user.setAddress("Default Address");

            userRepository.save(user);

            System.out.println("✅ Created default user: " + role.name());
        }
    }
}