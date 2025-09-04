package com.nisum.usermanagement.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI userManagementOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("User Management API")
                .version("v1")
                .description("API para gestión de usuarios y teléfonos"));
    }
}
