package com.epam.esm.zotov.mjcschool.dataaccess;

import java.io.IOException;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

import io.zonky.test.db.postgres.embedded.EmbeddedPostgres;

public class DataAccessTestConfig {
    @Profile("test")
    @Bean
    DataSource testDataSource() throws IOException {
        EmbeddedPostgres embeddedPg = EmbeddedPostgres.start();
        return embeddedPg.getPostgresDatabase();
    }
}