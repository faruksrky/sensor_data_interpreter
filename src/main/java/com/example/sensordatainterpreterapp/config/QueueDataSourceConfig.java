package com.example.sensordatainterpreterapp.config;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "queueEntityManagerFactory",
        transactionManagerRef = "queueTransactionManager",
        basePackages = { "com.example.sensordatainterpreterapp.queue.repository" }

        /** basePackages =  Spring'in Queue yöntemlerini çalıştırırken queue veri kaynağını kullanmasını sağlayacaktır.*/
)
public class QueueDataSourceConfig {

    /** --> @ConfigurationProperties(prefix = "spring.queuedb.datasource"). Bu, Spring'in veri kaynağını oluşturmak
                ve QueueData.java yöntemlerini çalıştırırken onu kullanmak için spring.queue.datasource ile başlayan özellikleri seçmesini sağlayacaktır.
     * --> @Primary, aynı dönüş türü için birden fazla Bean olduğundan, Spring'e bu Bean birincil Bean olarak kullanmasını söyler.
     *          Aynı dönüş tipindeki diğer Bean leri kullanmak için @Qualifier ek açıklamasını kullanmamız gerekir.*/

    @Bean(name="queueDataSource")
    @Primary
    @ConfigurationProperties(prefix="spring.queuedb.datasource")
    public DataSource queueDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Primary
    @Bean(name = "queueEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean queueEntityManagerFactory(EntityManagerFactoryBuilder builder,
                                                                              @Qualifier("queueDataSource") DataSource queueDataSource) {
        return builder
                .dataSource(queueDataSource)
                .packages("com.example.sensordatainterpreterapp.queue.entity")
                .build();
    }
    @Bean(name = "queueTransactionManager")
    public PlatformTransactionManager queueTransactionManager(
            @Qualifier("queueEntityManagerFactory") EntityManagerFactory queueEntityManagerFactory) {
        return new JpaTransactionManager(queueEntityManagerFactory);
    }
}
