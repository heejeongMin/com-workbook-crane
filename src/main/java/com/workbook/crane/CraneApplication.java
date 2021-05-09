package com.workbook.crane;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(
		exclude = {SecurityAutoConfiguration.class },
		scanBasePackages = "com.workbook.crane")
public class CraneApplication {

	public static void main(String[] args) {
		SpringApplication.run(CraneApplication.class, args);
	}
}
