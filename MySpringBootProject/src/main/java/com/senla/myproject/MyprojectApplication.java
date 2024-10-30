package com.senla.myproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication // @Configuration+@ComponentScan+@EnableAutoConfiguration
public class MyprojectApplication  { //extends SpringBootServletInitializer

	public static void main(String[] args) {
		SpringApplication.run(MyprojectApplication.class, args);
	}

	/*@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(MyprojectApplication.class);
	}*/
}
