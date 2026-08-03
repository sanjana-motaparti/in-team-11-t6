package com.cohortteam6.model;

import java.util.Objects;
import java.util.regex.Pattern;

public record TradeRef(String value) {

    private static final Pattern REF_PATTERN = Pattern.compile("^[A-Z]{3}-\\d{8}-\\d{4}$");

    public TradeRef {
        Objects.requireNonNull(value, "tradeRef value cannot be null");
        if (!REF_PATTERN.matcher(value).matches()) {
            throw new IllegalArgumentException(
                    "Invalid tradeRef format '%s' — must match AAA-YYYYMMDD-NNNN".formatted(value));
        }
    }

    public static TradeRef of(String referenceString) {
        return new TradeRef(referenceString);
    }

    @Override
    public String toString() {
        return value;
    }
}