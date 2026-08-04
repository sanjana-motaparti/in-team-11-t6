package com.dbtraining.reconx.kafka;

import com.dbtraining.reconx.dto.TradeEvent;
import com.dbtraining.reconx.repository.AuditLogRepository;
import com.dbtraining.reconx.repository.entity.AuditLogEntry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class AuditEventConsumer {

  private static final Logger log = LoggerFactory.getLogger(AuditEventConsumer.class);
  private final AuditLogRepository repo;

  public AuditEventConsumer(AuditLogRepository repo) {
    this.repo = repo;
  }

  @KafkaListener(topics = "trade-events", groupId = "audit-service", containerFactory = "tradeEventListenerContainerFactory")
  @Transactional
  public void onTradeEvent(TradeEvent e) {
    AuditLogEntry entry = AuditLogEntry.builder()
        .eventId(e.eventId())
        .tradeRef(e.tradeRef())
        .operation(e.eventType().name())
        .beforeData(e.before())
        .afterData(e.after())
        .occurredAt(e.timestamp())
        .build();

    repo.save(entry);
    log.debug("Audit row persisted for eventId={}", e.eventId());
  }
}