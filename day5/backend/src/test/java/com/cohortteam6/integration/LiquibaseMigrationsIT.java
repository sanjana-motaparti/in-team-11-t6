package com.cohortteam6.integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.jdbc.core.JdbcTemplate;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
public class LiquibaseMigrationsIT {

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15-alpine");

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    void testLiquibaseMigrations() {
        Integer changelogCount = jdbcTemplate.queryForObject("SELECT count(*) FROM databasechangelog", Integer.class);
        assertThat(changelogCount).isGreaterThanOrEqualTo(13);

        Integer tradeCount = jdbcTemplate.queryForObject("SELECT count(*) FROM trades WHERE deleted_at IS NULL", Integer.class);
        assertThat(tradeCount).isGreaterThanOrEqualTo(10);
    }
}
