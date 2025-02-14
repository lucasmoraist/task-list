package com.lucasmoraist.task_list.infra.documentation;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI documentation(){
        return new OpenAPI()
                .info(new Info()
                        .title("Task List API")
                        .description("API to manage tasks.")
                        .version("0.1.0")
                        .contact(contact())
                        .license(license())
                )
                .components(components())
                .addSecurityItem(new SecurityRequirement().addList("bearer"));
    }

    private Contact contact(){
        return new Contact()
                .name("Lucas de Morais Nascimento Taguchi")
                .email("luksmnt1101@gmail.com")
                .url("https://lucasmoraist.github.io/Portfolio/")
                ;
    }

    private License license(){
        return new License()
                .name("MIT License")
                .identifier("MIT")
                .url("https://github.com/lucasmoraist/task-list/blob/main/LICENSE")
                ;
    }

    private Components components(){
        return new Components()
                .addSecuritySchemes("bearer",
                        new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")
                );
    }
}
