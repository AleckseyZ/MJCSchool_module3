package com.epam.esm.zotov.mjcschool.dataaccess;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@ComponentScan("com.epam.esm.zotov.module2.dataacces")
@PropertySource("classpath:db.properties")
@PropertySource("classpath:hibernate.properties")
@EnableTransactionManagement
public class DataAccessConfig {
    @Value("${driver}")
    private String driver;
    @Value("${url}")
    private String url;
    @Value("${user}")
    private String user;
    @Value("${password}")
    private String password;
    @Value("${test.url}")
    private String testUrl;
    @Value("${hibernate.scanPackage}")
    private String scanPackage;
    @Value("${hibernate.dialectPropertyName}")
    private String hibernateDialectPropertyName;
    @Value("${hibernate.dialect}")
    private String hibernateDialect;

    @Profile("prod")
    @Bean
    public DataSource dataSource() {
        return makeDataSource(driver, url, user, password);
    }

    @Profile("dev")
    @Bean
    public DataSource devDataSource() {
        return makeDataSource(driver, testUrl, user, password);
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) {
        LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactory.setDataSource(dataSource);
        entityManagerFactory.setPackagesToScan(scanPackage);
        entityManagerFactory.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        entityManagerFactory.setJpaProperties(makeHibernateProperties());

        return entityManagerFactory;
    }

    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory);

        return transactionManager;
    }

    private Properties makeHibernateProperties() {
        Properties hibernateProperties = new Properties();
        hibernateProperties.setProperty(hibernateDialectPropertyName, hibernateDialect);
        return hibernateProperties;
    }

    private DataSource makeDataSource(String driver, String url, String user, String password) {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(driver);
        dataSource.setUrl(url);
        dataSource.setUsername(user);
        dataSource.setPassword(password);
        return dataSource;
    }
}