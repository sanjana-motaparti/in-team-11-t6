package com.cohortteam6.model;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Objects;

public record Money(BigDecimal amount, Currency currency) {

    public Money {
        Objects.requireNonNull(amount, "amount cannot be null");
        Objects.requireNonNull(currency, "currency cannot be null");
        if (amount.signum() < 0) {
            throw new IllegalArgumentException("Money amount cannot be negative: " + amount);
        }
    }

    public static Money of(String amountVal, String currencyCode) {
        return new Money(new BigDecimal(amountVal), Currency.getInstance(currencyCode));
    }

    public static Money of(BigDecimal amountVal, String currencyCode) {
        return new Money(amountVal, Currency.getInstance(currencyCode));
    }

    public Money plus(Money otherMoney) {
        if (!this.currency.equals(otherMoney.currency)) {
            throw new IllegalArgumentException(
                    "Cannot add %s to %s — currency mismatch".formatted(otherMoney.currency, this.currency));
        }
        return new Money(this.amount.add(otherMoney.amount), this.currency);
    }

    public Money times(BigDecimal multiplier) {
        return new Money(this.amount.multiply(multiplier), this.currency);
    }
}