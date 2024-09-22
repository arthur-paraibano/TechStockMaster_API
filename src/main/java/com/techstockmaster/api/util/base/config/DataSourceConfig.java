package com.techstockmaster.api.util.base.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {

    // Primary (local) datasource
    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    // Secondary (Lykos) datasource
    @Value("${spring.datasource.url_Lykos}")
    private String lykosUrl;

    @Value("${spring.datasource.username_Lykos}")
    private String lykosUsername;

    @Value("${spring.datasource.password_Lykos}")
    private String lykosPassword;

    @Bean(name = "dataSourceLocal")
    @Primary
    public DataSource dataSourceLocal() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        return dataSource;
    }

    @Bean(name = "dataSourceLykos")
    public DataSource dataSourceLykos() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl(lykosUrl);
        dataSource.setUsername(lykosUsername);
        dataSource.setPassword(lykosPassword);
        return dataSource;
    }
}
