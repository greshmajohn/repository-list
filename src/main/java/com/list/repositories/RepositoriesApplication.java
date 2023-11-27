package com.list.repositories;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class RepositoriesApplication {

	public static void main(String[] args) {
		SpringApplication.run(RepositoriesApplication.class, args);
	}
	
	@Bean
	RestTemplate restTemplate() {
	    return new RestTemplate();
	}

}
