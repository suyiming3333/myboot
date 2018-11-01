package com.sym.myboot.config.DynamicDataSourceConfig;

import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DynamicDataSourceConfig {

    @Bean(name = "writeDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.master")
    public DataSource writeDataSource(){
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "readDataSource1")
    @ConfigurationProperties(prefix = "spring.datasource.slave")
    public DataSource readDataSource1(){
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "readDataSource2")
    @ConfigurationProperties(prefix = "spring.datasource.slave1")
    public DataSource readDataSource2(){
        return DataSourceBuilder.create().build();
    }
}
