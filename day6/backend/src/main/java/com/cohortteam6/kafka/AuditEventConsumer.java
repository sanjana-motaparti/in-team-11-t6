package com.cohortteam6.kafka;

import com.cohortteam6.dto.TradeEvent;
import com.cohortteam6.repository.AuditLogRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class AuditEventConsumer {

    private static final Logger log = LoggerFactory.getLogger(AuditEventConsumer.class);
    private final AuditLogRepository repo;

    public AuditEventConsumer(AuditLogRepository repo) { this.repo = repo; }

    public void onTradeEvent(TradeEvent e) {
        throw new UnsupportedOperationException("Not implemented");
    }
}
