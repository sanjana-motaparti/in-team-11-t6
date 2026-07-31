package com.cohortteam6.service;

import com.cohortteam6.dto.TradeRequest;
import com.cohortteam6.exception.DuplicateTradeRefException;
import com.cohortteam6.exception.TradeNotFoundException;
import com.cohortteam6.kafka.TradeEventProducer;
import com.cohortteam6.observability.TradeMetrics;
import com.cohortteam6.repository.CounterpartyRepository;
import com.cohortteam6.repository.InstrumentRepository;
import com.cohortteam6.repository.TradeRepository;
import com.cohortteam6.repository.entity.Trade;
import com.cohortteam6.dto.TradeEvent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

import static com.cohortteam6.repository.TradeSpecifications.*;

@Service
@Transactional
public class TradeService {

    private final TradeRepository tradeRepo;
    private final CounterpartyRepository cpRepo;
    private final InstrumentRepository instRepo;
    private final TradeEventProducer events;
    private final TradeMetrics metrics;

    public TradeService(TradeRepository tradeRepo,
                        CounterpartyRepository cpRepo,
                        InstrumentRepository instRepo,
                        TradeEventProducer events,
                        TradeMetrics metrics) {
        this.tradeRepo = tradeRepo;
        this.cpRepo = cpRepo;
        this.instRepo = instRepo;
        this.events = events;
        this.metrics = metrics;
    }

    public Trade create(TradeRequest req, String actor) {
        if (tradeRepo.findByTradeRef(req.tradeRef()).isPresent()) {
            throw new DuplicateTradeRefException(req.tradeRef());
        }
        var instrument = instRepo.findById(req.instrumentId())
                .orElseThrow(() -> new TradeNotFoundException("Instrument not found"));
        var counterparty = cpRepo.findById(req.counterpartyId())
                .orElseThrow(() -> new TradeNotFoundException("Counterparty not found"));

        Trade t = new Trade();
        t.setTradeRef(req.tradeRef());
        t.setInstrument(instrument);
        t.setCounterparty(counterparty);
        t.setQuantity(req.quantity());
        t.setPrice(req.price());
        t.setTradeDate(req.tradeDate());
        t.setStatus("PENDING");

        Trade saved = tradeRepo.save(t);

        metrics.incrementTradeCreated();
        metrics.recordTradeValue(req.quantity().multiply(req.price()).doubleValue());

        events.publish(new TradeEvent(UUID.randomUUID(), saved.getTradeRef(), TradeEvent.EventType.TRADE_CREATED, Instant.now(), actor, null, "PENDING"));

        return saved;
    }

    public Trade update(Long id, TradeRequest req, String actor) {
        Trade t = tradeRepo.findById(id)
                .orElseThrow(() -> new TradeNotFoundException(id.toString()));
        String oldStatus = t.getStatus();

        t.setQuantity(req.quantity());
        t.setPrice(req.price());
        t.setTradeDate(req.tradeDate());

        Trade saved = tradeRepo.save(t);
        events.publish(new TradeEvent(UUID.randomUUID(), saved.getTradeRef(), TradeEvent.EventType.TRADE_UPDATED, Instant.now(), actor, oldStatus, saved.getStatus()));
        return saved;
    }

    public Trade updateStatus(Long id, String status, String actor) {
        Trade t = tradeRepo.findById(id)
                .orElseThrow(() -> new TradeNotFoundException(id.toString()));
        String oldStatus = t.getStatus();
        t.setStatus(status);
        Trade saved = tradeRepo.save(t);
        events.publish(new TradeEvent(UUID.randomUUID(), saved.getTradeRef(), TradeEvent.EventType.TRADE_UPDATED, Instant.now(), actor, oldStatus, saved.getStatus()));
        return saved;
    }

    public void softDelete(Long id, String actor) {
        Trade t = tradeRepo.findById(id)
                .orElseThrow(() -> new TradeNotFoundException(id.toString()));
        t.softDelete();
        tradeRepo.save(t);
        events.publish(new TradeEvent(UUID.randomUUID(), t.getTradeRef(), TradeEvent.EventType.TRADE_CANCELLED, Instant.now(), actor, t.getStatus(), "CANCELLED"));
    }

    @Transactional(readOnly = true)
    public Page<Trade> list(LocalDate from, LocalDate to, String status, Long counterpartyId, Pageable pageable) {
        Specification<Trade> spec = Specification.where(null);
        if (status != null) spec = spec.and(hasStatus(status));
        if (from != null && to != null) spec = spec.and(tradeDateBetween(from, to));
        if (counterpartyId != null) spec = spec.and(hasCounterparty(counterpartyId));
        return tradeRepo.findAll(spec, pageable);
    }
}
