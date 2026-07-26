package com.app.namasteqr.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class DatabaseMigrationConfig {

    @Bean
    public CommandLineRunner migrateDatabase(JdbcTemplate jdbcTemplate) {
        return args -> {
            try {
                jdbcTemplate.execute("ALTER TABLE restaurant_menu_item ALTER COLUMN custom_image TYPE TEXT");
                System.out.println("=== Migrated custom_image to TEXT successfully ===");
            } catch (Exception e) {
                System.out.println("=== Migration skipped or failed (might already be TEXT) ===");
            }
        };
    }
}
