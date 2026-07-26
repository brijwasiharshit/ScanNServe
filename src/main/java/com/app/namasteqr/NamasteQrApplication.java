package com.app.namasteqr;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class NamasteQrApplication {

	public static void main(String[] args) {
		SpringApplication.run(NamasteQrApplication.class, args);
	}

}
