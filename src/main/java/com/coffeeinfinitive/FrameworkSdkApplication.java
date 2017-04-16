package com.coffeeinfinitive;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class FrameworkSdkApplication{

	public static void main(String[] args) {
		SpringApplication.run(FrameworkSdkApplication.class, args);
	}
}
