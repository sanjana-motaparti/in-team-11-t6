package com.dbtraining.reconx.service;

import com.dbtraining.reconx.domain.Trade;
import com.dbtraining.reconx.domain.TradeStatus;
import com.dbtraining.reconx.dto.TradeResponse;
import com.dbtraining.reconx.dto.TradeMapper;
import com.dbtraining.reconx.repository.TradeRepository;
import com.dbtraining.reconx.repository.TradeSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TradeQueryService {

  private final TradeRepository repository;
  private final TradeMapper mapper;

  public Page<Trade> search(LocalDate from, LocalDate to, TradeStatus status, Long counterpartyId, Pageable pageable) {
    Specification<Trade> spec = Specification.where(TradeSpecification.tradeDateBetween(from, to))
        .and(TradeSpecification.hasStatus(status))
        .and(TradeSpecification.forCounterparty(counterpartyId));
    return repository.findAll(spec, pageable);
  }

  public TradeResponse toResponse(Trade trade) {
    return mapper.toResponse(trade);
  }
}
