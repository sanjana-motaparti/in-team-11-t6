package com.cohortteam6.service;

import com.cohortteam6.exception.InvalidTradeException;
import com.cohortteam6.repository.InstrumentRepository;
import com.cohortteam6.repository.entity.Instrument;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class InstrumentService {

    private final InstrumentRepository repo;

    public InstrumentService(InstrumentRepository repo) { this.repo = repo; }

    @Cacheable("instruments")
    public Instrument findBySymbol(String symbol) {

        throw new UnsupportedOperationException("Not implemented");
    }
}
