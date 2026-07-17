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

        createSuperAdminIfNotExists();

    }

    private void createSuperAdminIfNotExists() {

        String email = "superadmin@scannserve.com";

        if (userRepository.findByEmailAddress(email).isEmpty()) {

            UserEntity user = new UserEntity();

            user.setUsername("Super Admin");
            user.setEmailAddress(email);
            user.setHashedPassword(passwordEncoder.encode("Password@123"));
            user.setRole(Role.SUPER_ADMIN);
            user.setContactNumber("9999999999");
            user.setAddress("System");
            user.setRestaurant(null);

            userRepository.save(user);

            System.out.println("✅ Super Admin created successfully.");
        }
    }
}