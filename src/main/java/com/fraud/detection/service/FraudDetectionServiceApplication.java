package com.fraud.detection.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.validation.annotation.Validated;

@SpringBootApplication
@EnableFeignClients
@Validated
@EnableDiscoveryClient
public class FraudDetectionServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(FraudDetectionServiceApplication.class, args);
	}

}
