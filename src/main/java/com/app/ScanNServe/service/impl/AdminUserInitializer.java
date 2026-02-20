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

        String defaultAdminEmail = "admin@scannserve.com";

        UserEntity adminUser = new UserEntity();
        adminUser.setName("admin");
        adminUser.setEmailAddress(defaultAdminEmail);
        adminUser.setHashedPassword(passwordEncoder.encode("Admin@123"));
        adminUser.setRole(Role.ADMIN);
        adminUser.setPropertyIdFk(0L);
        adminUser.setContactNumber("9999999999");
        adminUser.setAddress("System Generated");

        userRepository.save(adminUser);

        System.out.println("✅ Default admin user created");
    }
}
