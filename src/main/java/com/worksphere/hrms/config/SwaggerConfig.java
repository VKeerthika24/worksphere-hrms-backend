package com.worksphere.hrms.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI workSphereOpenAPI() {

        return new OpenAPI()

                .info(new Info()

                        .title("WorkSphere HRMS API")

                        .description(
                                "Enterprise Human Resource Management System REST APIs")

                        .version("1.0.0")

                        .contact(new Contact()

                                .name("Keerthika V")

                                .email("keerthika@example.com"))

                        .license(new License()

                                .name("Apache 2.0")))

                .externalDocs(new ExternalDocumentation()

                        .description("WorkSphere Documentation"));
    }
}