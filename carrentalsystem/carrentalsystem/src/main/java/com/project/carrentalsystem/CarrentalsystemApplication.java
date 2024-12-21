package com.project.carrentalsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages= {"com.*"})
public class CarrentalsystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(CarrentalsystemApplication.class, args);
	}

}
