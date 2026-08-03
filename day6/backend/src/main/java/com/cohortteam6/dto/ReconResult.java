package com.cohortteam6.dto;

public record ReconResult(
        String tradeRef,
        Status status,
        String discrepancyType,
        String details
) {
    public enum Status { MATCHED, BREAK }

    public static ReconResult matched(String tradeRef) {
        return new ReconResult(tradeRef, Status.MATCHED, null, null);
    }

    public static ReconResult breakResult(String tradeRef, String discrepancyType, String details) {
        return new ReconResult(tradeRef, Status.BREAK, discrepancyType, details);
    }
}
