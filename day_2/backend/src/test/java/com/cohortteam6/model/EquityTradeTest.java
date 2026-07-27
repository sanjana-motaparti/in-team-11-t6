package com.cohortteam6.model;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class EquityTradeTest {

    @Test
    void builder_buildsWhenAllRequiredPresent() {
        EquityTrade testTrade = createSampleEquity("EQU-20260603-0001");
        
        assertThat(testTrade.tradeRef().value()).isEqualTo("EQU-20260603-0001");
        assertThat(testTrade.notional().amount()).isEqualByComparingTo(new BigDecimal("10000"));
        assertThat(testTrade.notional().currency().getCurrencyCode()).isEqualTo("EUR");
        assertThat(testTrade.assetClass()).isEqualTo(TradeType.AssetClass.EQUITY);
    }

    @Test
    void builder_missingPrice_throws() {
        assertThatThrownBy(() -> EquityTrade.builder()
                .tradeRef(TradeRef.of("EQU-20260603-0002"))
                .instrumentSymbol("SAP.DE")
                .quantity(new BigDecimal("100"))
                .currency("EUR")
                .side(Side.BUY)
                .tradeDate(LocalDate.of(2026, 6, 3))
                .counterpartyId(1L)
                .build())
                .isInstanceOf(NullPointerException.class)
                .hasMessageContaining("price");
    }

    @Test
    void equality_byTradeRef() {
        EquityTrade tradeA = createSampleEquity("EQU-20260603-0003");
        EquityTrade tradeB = createSampleEquity("EQU-20260603-0003");
        EquityTrade tradeC = createSampleEquity("EQU-20260603-0004");
        
        assertThat(tradeA).isEqualTo(tradeB).hasSameHashCodeAs(tradeB);
        assertThat(tradeA).isNotEqualTo(tradeC);
    }

    private EquityTrade createSampleEquity(String refId) {
        return EquityTrade.builder()
                .tradeRef(TradeRef.of(refId))
                .instrumentSymbol("SAP.DE")
                .quantity(new BigDecimal("100"))
                .price(new BigDecimal("100"))
                .currency("EUR")
                .side(Side.BUY)
                .tradeDate(LocalDate.of(2026, 6, 3))
                .counterpartyId(1L)
                .build();
    }
}