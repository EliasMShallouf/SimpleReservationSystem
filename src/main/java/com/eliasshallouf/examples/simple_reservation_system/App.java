package com.eliasshallouf.examples.simple_reservation_system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories("com.eliasshallouf.examples.simple_reservation_system.domain.model.repository")
@EntityScan("com.eliasshallouf.examples.simple_reservation_system.domain.model")
@SpringBootApplication
public class App {
	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}
}
