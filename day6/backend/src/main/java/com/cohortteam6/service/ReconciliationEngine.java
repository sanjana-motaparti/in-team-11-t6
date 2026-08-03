package com.cohortteam6.service;

import com.cohortteam6.dto.ReconResult;
import com.cohortteam6.model.ReconciliationRule;
import com.cohortteam6.model.TradeType;
import io.micrometer.core.annotation.Timed;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class ReconciliationEngine {

    @Timed(value = "reconciliation.duration", description = "Wall time of reconcile()",
           percentiles = {0.5, 0.95, 0.99}, histogram = true)
    public List<ReconResult> reconcile(List<TradeType> internal,
                                       List<TradeType> external,
                                       ReconciliationRule rule) {

        throw new UnsupportedOperationException("Not implemented");
    }

    public CompletableFuture<List<ReconResult>> reconcileByCounterparty(
            Map<Long, List<TradeType>> internalByCp,
            Map<Long, List<TradeType>> externalByCp,
            ReconciliationRule rule) {

        throw new UnsupportedOperationException("Not implemented");
    }

    private ReconResult matchOne(TradeType internal, TradeType external, ReconciliationRule rule) {

        throw new UnsupportedOperationException("Not implemented");
    }

    private BigDecimal[] priceQty(TradeType t) {

        throw new UnsupportedOperationException("Not implemented");
    }
}
