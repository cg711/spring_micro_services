package com.example.batches;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableBatchProcessing
@EnableEurekaClient
public class BatchesApplication {

	public static void main(String[] args) {
		SpringApplication.run(BatchesApplication.class, args);
	}

}
