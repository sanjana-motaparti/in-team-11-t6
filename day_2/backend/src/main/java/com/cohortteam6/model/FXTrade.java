package com.cohortteam6.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Currency;
import java.util.Objects;

public final class FXTrade implements TradeType {

    private final TradeRef tradeRef;
    private final Currency ccy1;
    private final Currency ccy2;
    private final BigDecimal notionalCcy1;
    private final BigDecimal fxRate;
    private final Side side;
    private final LocalDate tradeDate;
    private final long counterpartyId;

    private FXTrade(Builder bld) {
        this.tradeRef       = bld.tradeRef;
        this.ccy1           = bld.ccy1;
        this.ccy2           = bld.ccy2;
        this.notionalCcy1   = bld.notionalCcy1;
        this.fxRate         = bld.fxRate;
        this.side           = bld.side;
        this.tradeDate      = bld.tradeDate;
        this.counterpartyId = bld.counterpartyId;
    }

    public static Builder builder() { return new Builder(); }

    @Override public TradeRef tradeRef()     { return tradeRef; }
    @Override public LocalDate tradeDate()   { return tradeDate; }
    @Override public AssetClass assetClass() { return AssetClass.FX; }
    @Override public Money notional()        { return new Money(notionalCcy1.multiply(fxRate), ccy2); }

    public Currency ccy1()           { return ccy1; }
    public Currency ccy2()           { return ccy2; }
    public BigDecimal notionalCcy1() { return notionalCcy1; }
    public BigDecimal fxRate()       { return fxRate; }
    public Side side()               { return side; }
    public long counterpartyId()     { return counterpartyId; }

    @Override 
    public boolean equals(Object obj) {
        return (obj instanceof FXTrade that) && this.tradeRef.equals(that.tradeRef);
    }
    
    @Override 
    public int hashCode() { 
        return tradeRef.hashCode(); 
    }

    @Override 
    public String toString() {
        return "FXTrade[ref=%s, %s/%s, notional=%s %s, rate=%s, side=%s]"
                .formatted(tradeRef, ccy1.getCurrencyCode(), ccy2.getCurrencyCode(),
                           notionalCcy1.toPlainString(), ccy1.getCurrencyCode(), fxRate.toPlainString(), side);
        // NOTE: counterpartyId deliberately excluded to avoid PII logs.
    }

    public static final class Builder {
        private TradeRef tradeRef;
        private Currency ccy1, ccy2;
        private BigDecimal notionalCcy1, fxRate;
        private Side side;
        private LocalDate tradeDate;
        private long counterpartyId;

        public Builder tradeRef(TradeRef ref)          { this.tradeRef = ref; return this; }
        public Builder ccy1(String code)               { this.ccy1 = Currency.getInstance(code); return this; }
        public Builder ccy2(String code)               { this.ccy2 = Currency.getInstance(code); return this; }
        public Builder notionalCcy1(BigDecimal val)    { this.notionalCcy1 = val; return this; }
        public Builder fxRate(BigDecimal rate)         { this.fxRate = rate; return this; }
        public Builder side(Side s)                    { this.side = s; return this; }
        public Builder tradeDate(LocalDate date)       { this.tradeDate = date; return this; }
        public Builder counterpartyId(long id)         { this.counterpartyId = id; return this; }

        public FXTrade build() {
            Objects.requireNonNull(tradeRef,     "tradeRef is missing");
            Objects.requireNonNull(ccy1,         "ccy1 is missing");
            Objects.requireNonNull(ccy2,         "ccy2 is missing");
            Objects.requireNonNull(notionalCcy1, "notionalCcy1 is missing");
            Objects.requireNonNull(fxRate,       "fxRate is missing");
            Objects.requireNonNull(side,         "side is missing");
            Objects.requireNonNull(tradeDate,    "tradeDate is missing");
            
            if (ccy1.equals(ccy2)) throw new IllegalStateException("ccy1 and ccy2 must be different");
            if (fxRate.signum() <= 0) throw new IllegalStateException("fxRate must be > 0");
            
            return new FXTrade(this);
        }
    }
}