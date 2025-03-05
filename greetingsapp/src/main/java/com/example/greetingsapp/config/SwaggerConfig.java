package com.example.greetingsapp.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("GreetingsApp API")
                        .description("This is a sample GreetingsApp API using SpringDoc OpenAPI 3")
                        .version("1.0")
                        .contact(new Contact()
                                .name("Support Team")
                                .email("support@greetingsapp.com")
                                .url("https://greetingsapp.com"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://springdoc.org")));
    }
}
