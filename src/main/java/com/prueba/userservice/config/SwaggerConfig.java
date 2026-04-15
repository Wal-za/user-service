package com.prueba.userservice.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI usersMicroserviceOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("User Service API")
                        .description("API de registro, autenticación y recuperación de usuarios con JWT")
                        .version("1.0")
                        .contact(new Contact()
                                .name("Backend Team")
                                .email("backend@prueba.com")));
    }
}