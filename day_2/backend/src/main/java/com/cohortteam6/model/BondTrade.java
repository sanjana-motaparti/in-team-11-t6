package com.cohortteam6.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Currency;
import java.util.Objects;

public final class BondTrade implements TradeType {

    private final TradeRef tradeRef;
    private final String isin;
    private final BigDecimal faceValue;
    private final BigDecimal couponRate;
    private final LocalDate maturityDate;
    private final Currency currency;
    private final Side side;
    private final LocalDate tradeDate;
    private final long counterpartyId;

    private BondTrade(Builder bld) {
        this.tradeRef       = bld.tradeRef;
        this.isin           = bld.isin;
        this.faceValue      = bld.faceValue;
        this.couponRate     = bld.couponRate;
        this.maturityDate   = bld.maturityDate;
        this.currency       = bld.currency;
        this.side           = bld.side;
        this.tradeDate      = bld.tradeDate;
        this.counterpartyId = bld.counterpartyId;
    }

    public static Builder builder() { return new Builder(); }

    @Override public TradeRef tradeRef()     { return tradeRef; }
    @Override public LocalDate tradeDate()   { return tradeDate; }
    @Override public AssetClass assetClass() { return AssetClass.BOND; }
    @Override public Money notional()        { return new Money(faceValue, currency); }

    public String isin()              { return isin; }
    public BigDecimal faceValue()     { return faceValue; }
    public BigDecimal couponRate()    { return couponRate; }
    public LocalDate maturityDate()   { return maturityDate; }
    public Currency currency()        { return currency; }
    public Side side()                { return side; }
    public long counterpartyId()      { return counterpartyId; }

    @Override 
    public boolean equals(Object obj) {
        return (obj instanceof BondTrade that) && this.tradeRef.equals(that.tradeRef);
    }
    
    @Override 
    public int hashCode() { 
        return tradeRef.hashCode(); 
    }

    @Override 
    public String toString() {
        return "BondTrade[ref=%s, isin=%s, face=%s %s, coupon=%s, maturity=%s, side=%s]"
                .formatted(tradeRef, isin, faceValue.toPlainString(), currency.getCurrencyCode(),
                           couponRate.toPlainString(), maturityDate, side);
        // NOTE: counterpartyId deliberately excluded to avoid PII logs.
    }

    public static final class Builder {
        private TradeRef tradeRef;
        private String isin;
        private BigDecimal faceValue, couponRate;
        private LocalDate maturityDate, tradeDate;
        private Currency currency;
        private Side side;
        private long counterpartyId;

        public Builder tradeRef(TradeRef ref)          { this.tradeRef = ref; return this; }
        public Builder isin(String code)               { this.isin = code; return this; }
        public Builder faceValue(BigDecimal val)       { this.faceValue = val; return this; }
        public Builder couponRate(BigDecimal rate)     { this.couponRate = rate; return this; }
        public Builder maturityDate(LocalDate date)    { this.maturityDate = date; return this; }
        public Builder currency(String code)           { this.currency = Currency.getInstance(code); return this; }
        public Builder side(Side s)                    { this.side = s; return this; }
        public Builder tradeDate(LocalDate date)       { this.tradeDate = date; return this; }
        public Builder counterpartyId(long id)         { this.counterpartyId = id; return this; }

        public BondTrade build() {
            Objects.requireNonNull(tradeRef,     "tradeRef is missing");
            Objects.requireNonNull(isin,         "isin is missing");
            Objects.requireNonNull(faceValue,    "faceValue is missing");
            Objects.requireNonNull(couponRate,   "couponRate is missing");
            Objects.requireNonNull(maturityDate, "maturityDate is missing");
            Objects.requireNonNull(currency,     "currency is missing");
            Objects.requireNonNull(side,         "side is missing");
            Objects.requireNonNull(tradeDate,    "tradeDate is missing");
            
            if (maturityDate.isBefore(tradeDate)) {
                throw new IllegalStateException("maturityDate cannot be before tradeDate");
            }
            return new BondTrade(this);
        }
    }
}