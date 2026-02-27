package com.payment.api.integration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class PaymentApiIntegrationApplication {

	public static void main(String[] args) {
		SpringApplication.run(PaymentApiIntegrationApplication.class, args);
		System.out.println("Payment Integration project started");
	}

}
