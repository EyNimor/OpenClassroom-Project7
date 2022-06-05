package com.nnk.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.nnk.springboot.annotation.ExcludeFromJacocoGeneratedReport;

@SpringBootApplication @ExcludeFromJacocoGeneratedReport
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}