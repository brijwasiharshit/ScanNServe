package com.app.ScanNServe;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableCaching
public class ScanNServeApplication {

	public static void main(String[] args) {
		SpringApplication.run(ScanNServeApplication.class, args);
	}

}
