package com.epam.esm.zotov.mjcschool.api;

import java.util.Locale;

import org.springframework.context.annotation.Bean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.context.support.ResourceBundleMessageSource;

@SpringBootApplication(scanBasePackages = "com.epam.esm.zotov.mjcschool")
@PropertySource("classpath:api.properties")
public class Api {
    @Value("${msg.baseName}")
    private String baseName;
    @Value("${msg.defaultLocale}")
    private String defaultLocale;
    @Value("${msg.defaultEncoding}")
    private String defaultEncoding;

    public static void main(String[] args) {
        SpringApplication.run(Api.class, args);
    }

    @Bean("messageSource")
    public MessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename(baseName);
        messageSource.setDefaultLocale(Locale.forLanguageTag(defaultLocale));
        messageSource.setUseCodeAsDefaultMessage(true);
        messageSource.setDefaultEncoding(defaultEncoding);
        return messageSource;
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer placeholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }
}