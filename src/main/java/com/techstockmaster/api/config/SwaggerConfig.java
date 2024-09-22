package com.techstockmaster.api.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Tech Stock Master API")
                        .version("1.0")
                        .description("Documentação da API do Tech Stock Master"));
    }

    @Bean
    public GroupedOpenApi apiGroup() {
        return GroupedOpenApi.builder()
                .group("controllers")
                .packagesToScan("com.techstockmaster.api.controllers")
                .build();
    }
}