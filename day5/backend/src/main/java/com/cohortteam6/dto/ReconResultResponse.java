package com.cohortteam6.dto;

import java.time.Instant;

public record ReconResultResponse(
        Long id,
        Long tradeId,
        String discrepancyType,
        String status,
        Instant detectedAt,
        Instant resolvedAt,
        String resolutionNote
) {}
