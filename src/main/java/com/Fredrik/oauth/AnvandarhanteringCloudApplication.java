package com.Fredrik.oauth;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
@EnableWebSecurity
public class AnvandarhanteringCloudApplication {
	public static void main(String[] args) {
		Dotenv dotenv = Dotenv.load();

		System.setProperty("GITHUB_CLIENT_ID", dotenv.get("GITHUB_CLIENT_ID"));
		System.setProperty("GITHUB_CLIENT_SECRET", dotenv.get("GITHUB_CLIENT_SECRET"));
		System.setProperty("GOOGLE_CLIENT_ID", dotenv.get("GOOGLE_CLIENT_ID"));
		System.setProperty("GOOGLE_CLIENT_SECRET", dotenv.get("GOOGLE_CLIENT_SECRET"));

		SpringApplication.run(AnvandarhanteringCloudApplication.class, args);
	}
}
