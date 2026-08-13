package com.tuempresa.miproyecto.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * CORS = permiso para que el navegador deje que Vue (otro origen)
 * llame a tus APIs.
 *
 * Vue corre en http://localhost:5173
 * API corre en http://localhost:8080
 * Sin esto, el browser bloquea el fetch.
 */
@Configuration
public class CorsConfig {

	@Bean
	WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/api/**")
						.allowedOrigins("http://localhost:5173")
						.allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
						.allowedHeaders("*")
						// Necesario para que el browser envíe/reciba cookies en requests cross-origin
						.allowCredentials(true);
			}
		};
	}
}
