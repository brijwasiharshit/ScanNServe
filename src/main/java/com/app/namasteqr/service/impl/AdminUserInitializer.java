package com.app.namasteqr.service.impl;

import com.app.namasteqr.domain.entity.UserEntity;
import com.app.namasteqr.domain.repository.IUserRespository;
import com.app.namasteqr.utils.enums.Role;
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
