package com.sw.hearhere;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@OpenAPIDefinition(servers = {@Server(url = "http://localhost:8080",description = "local url"),
		@Server(url = "hear-here.shop",description = "Server url")})
@EnableJpaAuditing
@SpringBootApplication
public class HearhereApplication {

	public static void main(String[] args) {
		SpringApplication.run(HearhereApplication.class, args);
	}

}
