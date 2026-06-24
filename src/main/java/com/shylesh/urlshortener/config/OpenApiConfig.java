package com.shylesh.urlshortener.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI urlShortenerApi() {

        return new OpenAPI()
                .info(new Info()
                        .title("Scalable URL Shortener API")
                        .version("1.0.0")
                        .description("""
                                URL shortening service built using Spring Boot,
                                MySQL and Redis.
                                """));
    }
}