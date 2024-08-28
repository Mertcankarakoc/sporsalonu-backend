package com.FitnessPro.sporsalonu_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
public class SporsalonuBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(SporsalonuBackendApplication.class, args);
	}

}
