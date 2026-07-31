package com.cohortteam6.integration;

import com.cohortteam6.dto.LoginRequest;
import com.cohortteam6.dto.ReconRunRequest;
import com.cohortteam6.dto.StatusUpdate;
import com.cohortteam6.dto.TradeRequest;
import com.cohortteam6.dto.ResolutionRequest;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.http.*;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TradeLifecycleIT {

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15-alpine");

    @Autowired
    private TestRestTemplate restTemplate;

    private static String jwtToken;
    private static Long tradeId;
    private static String jobId;

    @Test
    @Order(1)
    void loginAsAdmin() {
        LoginRequest req = new LoginRequest("admin@db.com", "admin123");
        ResponseEntity<Map> res = restTemplate.postForEntity("/auth/login", req, Map.class);
        assertThat(res.getStatusCode()).isEqualTo(HttpStatus.OK);
        jwtToken = (String) res.getBody().get("token");
        assertThat(jwtToken).isNotNull();
    }

    @Test
    @Order(2)
    void createTrade() {
        TradeRequest req = new TradeRequest("AAA-20260101-9999", 1L, 1L, "EQUITY", "BUY", BigDecimal.TEN, BigDecimal.ONE, LocalDate.now());
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(jwtToken);
        HttpEntity<TradeRequest> entity = new HttpEntity<>(req, headers);

        ResponseEntity<Map> res = restTemplate.postForEntity("/v1/trades", entity, Map.class);
        assertThat(res.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        tradeId = ((Number) res.getBody().get("id")).longValue();
    }

    @Test
    @Order(3)
    void listTrades() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(jwtToken);
        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<Map> res = restTemplate.exchange("/v1/trades", HttpMethod.GET, entity, Map.class);
        assertThat(res.getStatusCode()).isEqualTo(HttpStatus.OK);
        Number total = (Number) res.getBody().get("totalElements");
        assertThat(total.intValue()).isGreaterThanOrEqualTo(1);
    }

    @Test
    @Order(4)
    void patchStatus() {
        StatusUpdate req = new StatusUpdate("MATCHED");
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(jwtToken);
        HttpEntity<StatusUpdate> entity = new HttpEntity<>(req, headers);

        ResponseEntity<Map> res = restTemplate.exchange("/v1/trades/" + tradeId + "/status", HttpMethod.PATCH, entity, Map.class);
        assertThat(res.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(res.getBody().get("status")).isEqualTo("MATCHED");
    }

    @Test
    @Order(5)
    void triggerRecon() {
        ReconRunRequest req = new ReconRunRequest(LocalDate.now().minusDays(1), LocalDate.now(), null);
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(jwtToken);
        HttpEntity<ReconRunRequest> entity = new HttpEntity<>(req, headers);

        ResponseEntity<Map> res = restTemplate.postForEntity("/v1/recon/run", entity, Map.class);
        assertThat(res.getStatusCode()).isEqualTo(HttpStatus.ACCEPTED);
        jobId = (String) res.getBody().get("jobId");
        assertThat(jobId).isNotNull();
    }

}
