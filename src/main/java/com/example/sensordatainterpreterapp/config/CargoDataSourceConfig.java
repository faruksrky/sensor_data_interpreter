package com.example.sensordatainterpreterapp.config;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "cargoEntityManagerFactory",
        transactionManagerRef = "cargoTransactionManager",
        basePackages = { "com.example.sensordatainterpreterapp.cargo.repository" }
)
public class CargoDataSourceConfig {

    @Bean(name="cargoDataSource")
    @ConfigurationProperties(prefix="spring.cargodb.datasource")
    public DataSource cargoDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "cargoEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean cargoEntityManagerFactory(EntityManagerFactoryBuilder builder,
                                                                           @Qualifier("cargoDataSource") DataSource cargoDataSource) {
        return builder
                .dataSource(cargoDataSource)
                .packages("com.example.sensordatainterpreterapp.cargo.entity")
                .build();
    }

    @Bean(name = "cargoTransactionManager")
    public PlatformTransactionManager cargoTransactionManager(
            @Qualifier("cargoEntityManagerFactory") EntityManagerFactory cargoEntityManagerFactory) {
        return new JpaTransactionManager(cargoEntityManagerFactory);
    }


}
