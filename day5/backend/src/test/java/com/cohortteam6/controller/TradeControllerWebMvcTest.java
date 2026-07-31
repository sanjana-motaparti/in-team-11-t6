package com.cohortteam6.controller;

import com.cohortteam6.dto.TradeRequest;
import com.cohortteam6.repository.entity.Trade;
import com.cohortteam6.security.JwtAuthenticationFilter;
import com.cohortteam6.security.JwtTokenProvider;
import com.cohortteam6.service.TradeService;
import com.cohortteam6.dto.TradeMapper;
import com.cohortteam6.dto.TradeResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.springframework.context.annotation.Import;
import com.cohortteam6.security.SecurityConfig;

@WebMvcTest(
    controllers = TradeController.class,
    includeFilters = @ComponentScan.Filter(
        type = FilterType.ASSIGNABLE_TYPE,
        classes = {JwtAuthenticationFilter.class, JwtTokenProvider.class}
    )
)
@Import(SecurityConfig.class)
public class TradeControllerWebMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TradeService tradeService;

    @MockBean
    private TradeMapper tradeMapper;

    @MockBean
    private JwtTokenProvider jwtTokenProvider;

    @MockBean
    private org.springframework.data.jpa.mapping.JpaMetamodelMappingContext jpaMappingContext;

    @Test
    @WithMockUser(roles = "TRADER")
    void testCreateTrade_authenticated_returns201() throws Exception {
        TradeRequest req = new TradeRequest("AAA-20260101-1111", 1L, 2L, "EQUITY", "BUY", BigDecimal.TEN, BigDecimal.ONE, LocalDate.now());
        Trade mockTrade = new Trade();
        org.springframework.test.util.ReflectionTestUtils.setField(mockTrade, "id", 99L);

        TradeResponse mockResponse = new TradeResponse(99L, "AAA-20260101-1111", 1L, "AAPL", 2L, "GS", "EQUITY", "BUY", BigDecimal.TEN, BigDecimal.ONE, LocalDate.now(), "PENDING", Instant.now(), Instant.now());

        Mockito.when(tradeService.create(any(), any())).thenReturn(mockTrade);
        Mockito.when(tradeMapper.toResponse(any())).thenReturn(mockResponse);

        mockMvc.perform(post("/v1/trades")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/api/v1/trades/99"))
                .andExpect(jsonPath("$.id").value(99));
    }

    @Test
    void testCreateTrade_unauthenticated_returns401() throws Exception {
        TradeRequest req = new TradeRequest("AAA-20260101-1111", 1L, 2L, "EQUITY", "BUY", BigDecimal.TEN, BigDecimal.ONE, LocalDate.now());

        mockMvc.perform(post("/v1/trades")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "VIEWER")
    void testCreateTrade_viewerRole_returns403() throws Exception {
        TradeRequest req = new TradeRequest("AAA-20260101-1111", 1L, 2L, "EQUITY", "BUY", BigDecimal.TEN, BigDecimal.ONE, LocalDate.now());

        mockMvc.perform(post("/v1/trades")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isForbidden());
    }
}
