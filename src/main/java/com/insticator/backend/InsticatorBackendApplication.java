package com.insticator.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@ComponentScan
@SpringBootApplication
@EnableJpaAuditing
public class InsticatorBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(InsticatorBackendApplication.class, args);
	}

}
