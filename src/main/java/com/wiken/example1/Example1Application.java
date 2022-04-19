package com.wiken.example1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class Example1Application {

	public static void main(String[] args) {
		SpringApplication.run(Example1Application.class, args);
	}

}
