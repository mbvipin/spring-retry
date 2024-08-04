package com.springboot.retrydemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.EnableRetry;

@SpringBootApplication
@EnableRetry
public class RetrydemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(RetrydemoApplication.class, args);
	}

}
