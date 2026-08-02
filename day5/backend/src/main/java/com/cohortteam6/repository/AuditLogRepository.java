package com.cohortteam6.repository;

import com.cohortteam6.repository.entity.AuditLogEntry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AuditLogRepository extends JpaRepository<AuditLogEntry, Long> {
    List<AuditLogEntry> findByTradeRefOrderByEventTimestampAsc(String tradeRef);
}
