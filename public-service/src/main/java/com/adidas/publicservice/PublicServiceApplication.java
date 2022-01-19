package com.adidas.publicservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class PublicServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(PublicServiceApplication.class, args);
	}

}
