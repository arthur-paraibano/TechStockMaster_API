package com.techstockmaster.api;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@OpenAPIDefinition(servers = {@Server(url = "/", description = "Default Server URL")})
@SpringBootApplication(scanBasePackages = "com.techstockmaster.api")
public class ApiApplication {

    /**
     * @param args
     */
    public static void main(String[] args) {
//        SpringApplication.run(ApiApplication.class, args);
        SpringApplication app = new SpringApplication(ApiApplication.class);
        app.setAdditionalProfiles(System.getenv("SPRING_PROFILES_ACTIVE"));
        app.run(args);
    }
}