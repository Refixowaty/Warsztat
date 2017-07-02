package com.refix.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
public class BazaDanychApplication {

	public static void main(String[] args) {
		SpringApplication.run(BazaDanychApplication.class, args);
	}
}
