package com.cohortteam6.service;

import com.cohortteam6.model.EquityTrade;
import com.cohortteam6.model.TradeType;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TradeAnalyticsService {

    public Map<Long, NotionalSummary> notionalByCounterparty(List<? extends TradeType> trades) {

        throw new UnsupportedOperationException("Not implemented");
    }

    public Map<String, BigDecimal> vwapByInstrument(List<EquityTrade> equityTrades) {

        throw new UnsupportedOperationException("Not implemented");
    }

    public Map<String, BigDecimal> pnlByInstrument(List<EquityTrade> equityTrades) {

        throw new UnsupportedOperationException("Not implemented");
    }

    private BigDecimal pnl(EquityTrade t) {
        throw new UnsupportedOperationException("Not implemented");
    }

    private long counterpartyIdOf(TradeType t) {

        throw new UnsupportedOperationException("Not implemented");
    }

    public record NotionalSummary(long count, BigDecimal total) {}
}
