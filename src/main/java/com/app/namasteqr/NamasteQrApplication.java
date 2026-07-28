package com.app.namasteqr;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.context.annotation.Bean;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootApplication
@EnableJpaAuditing
public class NamasteQrApplication {

	public static void main(String[] args) {
		SpringApplication.run(NamasteQrApplication.class, args);
	}

	@Bean
	public CommandLineRunner dropConstraint(JdbcTemplate jdbcTemplate) {
		return args -> {
			try {
				jdbcTemplate.execute("ALTER TABLE user_table DROP CONSTRAINT IF EXISTS uken3wad7p8qfu8pcmh62gvef6v");
				System.out.println("✅ Successfully dropped the stale username constraint from the database!");
			} catch (Exception e) {
				System.out.println("⚠️ Could not drop constraint (it might already be removed).");
			}
		};
	}

}
