package com.cohortteam6.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

public final class TradeFactory {

    private TradeFactory() { }

    public static TradeType create(String assetClass, Map<String, Object> p) {
        throw new UnsupportedOperationException("Not implemented");
    }

    private static EquityTrade equity(Map<String, Object> p) {
        throw new UnsupportedOperationException("Not implemented");
    }

    private static FXTrade fx(Map<String, Object> p) {
        throw new UnsupportedOperationException("Not implemented");
    }

    private static BondTrade bond(Map<String, Object> p) {
        throw new UnsupportedOperationException("Not implemented");
    }

    private static DerivativeTrade derivative(Map<String, Object> p) {
        throw new UnsupportedOperationException("Not implemented");
    }
}
