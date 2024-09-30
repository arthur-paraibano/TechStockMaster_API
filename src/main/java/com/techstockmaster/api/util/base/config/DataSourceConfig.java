package com.techstockmaster.api.util.base.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {

    // Primary (local) datasource
    @Value("${spring.datasource.local.url}")
    private String localUrl;

    @Value("${spring.datasource.local.username}")
    private String localUsername;

    @Value("${spring.datasource.local.password}")
    private String localPassword;

    // Secondary (Lykos) datasource
    @Value("${spring.datasource.lykos.url}")
    private String lykosUrl;

    @Value("${spring.datasource.lykos.username}")
    private String lykosUsername;

    @Value("${spring.datasource.lykos.password}")
    private String lykosPassword;

    @Bean(name = "dataSourceLocal")
    @Primary
    public DataSource dataSourceLocal() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl(localUrl);
        dataSource.setUsername(localUsername);
        dataSource.setPassword(localPassword);
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

    // Bean for JdbcTemplate for local database
    @Bean(name = "jdbcTemplateLocal")
    public JdbcTemplate jdbcTemplateLocal(@Qualifier("dataSourceLocal") DataSource dataSourceLocal) {
        return new JdbcTemplate(dataSourceLocal);
    }

    // Bean for JdbcTemplate for Lykos database
    @Bean(name = "jdbcTemplateLykos")
    public JdbcTemplate jdbcTemplateLykos(@Qualifier("dataSourceLykos") DataSource dataSourceLykos) {
        return new JdbcTemplate(dataSourceLykos);
    }
}
