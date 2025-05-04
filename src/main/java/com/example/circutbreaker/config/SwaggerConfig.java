package com.example.circutbreaker.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/*
This is optional
 */
@Configuration
public class SwaggerConfig {

  @Bean
  public OpenAPI apiInfo() {
    return new OpenAPI()
        .info(new Info()
            .title("My API")
            .description("API documentation with Springdoc")
            .version("1.0.0"));
  }
}

