package com.promedicus.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.promedicus"})
public class ProMedicusBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProMedicusBackendApplication.class, args);
	}
}
