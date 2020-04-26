package com.danilo.diplomski;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@EnableDiscoveryClient
public class NotebookMicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(NotebookMicroserviceApplication.class, args);
	}
	
	@Bean
	public WebMvcConfigurer corsConfigurer() {

	   return new WebMvcConfigurer() {
		   @Override
		   public void addCorsMappings(CorsRegistry registry) {
			   registry.addMapping("/**").allowedOrigins("*").allowedMethods("HEAD", "GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS");;
		   }
		   
	   };}

}
