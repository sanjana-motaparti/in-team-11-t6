package com.cohortteam6.repository;

import com.cohortteam6.repository.entity.Trade;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public final class TradeSpecifications {

    private TradeSpecifications() {}

    public static Specification<Trade> hasStatus(String status) {
        throw new UnsupportedOperationException("Not implemented");
    }

    public static Specification<Trade> tradeDateBetween(LocalDate from, LocalDate to) {
        throw new UnsupportedOperationException("Not implemented");
    }

    public static Specification<Trade> hasCounterparty(Long counterpartyId) {
        throw new UnsupportedOperationException("Not implemented");
    }
}
