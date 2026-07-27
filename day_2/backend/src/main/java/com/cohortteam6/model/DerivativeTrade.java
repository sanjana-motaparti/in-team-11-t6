package com.cohortteam6.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Currency;
import java.util.Objects;

public final class DerivativeTrade implements TradeType {

    public enum OptionType { CALL, PUT }

    private final TradeRef tradeRef;
    private final String underlying;
    private final BigDecimal strike;
    private final BigDecimal quantity;
    private final LocalDate expiry;
    private final OptionType optionType;
    private final Currency currency;
    private final Side side;
    private final LocalDate tradeDate;
    private final long counterpartyId;

    private DerivativeTrade(Builder bld) {
        this.tradeRef       = bld.tradeRef;
        this.underlying     = bld.underlying;
        this.strike         = bld.strike;
        this.quantity       = bld.quantity;
        this.expiry         = bld.expiry;
        this.optionType     = bld.optionType;
        this.currency       = bld.currency;
        this.side           = bld.side;
        this.tradeDate      = bld.tradeDate;
        this.counterpartyId = bld.counterpartyId;
    }

    public static Builder builder() { return new Builder(); }

    @Override public TradeRef tradeRef()     { return tradeRef; }
    @Override public LocalDate tradeDate()   { return tradeDate; }
    @Override public AssetClass assetClass() { return AssetClass.DERIVATIVE; }
    @Override public Money notional()        { return new Money(strike.multiply(quantity), currency); }

    public String underlying()       { return underlying; }
    public BigDecimal strike()       { return strike; }
    public BigDecimal quantity()     { return quantity; }
    public LocalDate expiry()        { return expiry; }
    public OptionType optionType()   { return optionType; }
    public Currency currency()       { return currency; }
    public Side side()               { return side; }
    public long counterpartyId()     { return counterpartyId; }

    @Override 
    public boolean equals(Object obj) {
        return (obj instanceof DerivativeTrade that) && this.tradeRef.equals(that.tradeRef);
    }
    
    @Override 
    public int hashCode() { 
        return tradeRef.hashCode(); 
    }

    @Override 
    public String toString() {
        return "DerivativeTrade[ref=%s, %s %s on %s, strike=%s %s, qty=%s, expiry=%s, side=%s]"
                .formatted(tradeRef, optionType, underlying, tradeDate, strike.toPlainString(),
                           currency.getCurrencyCode(), quantity.toPlainString(), expiry, side);
        // NOTE: counterpartyId deliberately excluded to avoid PII logs.
    }

    public static final class Builder {
        private TradeRef tradeRef;
        private String underlying;
        private BigDecimal strike, quantity;
        private LocalDate expiry, tradeDate;
        private OptionType optionType;
        private Currency currency;
        private Side side;
        private long counterpartyId;

        public Builder tradeRef(TradeRef ref)          { this.tradeRef = ref; return this; }
        public Builder underlying(String val)          { this.underlying = val; return this; }
        public Builder strike(BigDecimal val)          { this.strike = val; return this; }
        public Builder quantity(BigDecimal val)        { this.quantity = val; return this; }
        public Builder expiry(LocalDate date)          { this.expiry = date; return this; }
        public Builder optionType(OptionType type)     { this.optionType = type; return this; }
        public Builder currency(String code)           { this.currency = Currency.getInstance(code); return this; }
        public Builder side(Side s)                    { this.side = s; return this; }
        public Builder tradeDate(LocalDate date)       { this.tradeDate = date; return this; }
        public Builder counterpartyId(long id)         { this.counterpartyId = id; return this; }

        public DerivativeTrade build() {
            Objects.requireNonNull(tradeRef,   "tradeRef is missing");
            Objects.requireNonNull(underlying, "underlying is missing");
            Objects.requireNonNull(strike,     "strike is missing");
            Objects.requireNonNull(quantity,   "quantity is missing");
            Objects.requireNonNull(expiry,     "expiry is missing");
            Objects.requireNonNull(optionType, "optionType is missing");
            Objects.requireNonNull(currency,   "currency is missing");
            Objects.requireNonNull(side,       "side is missing");
            Objects.requireNonNull(tradeDate,  "tradeDate is missing");
            
            if (strike.signum() <= 0)   throw new IllegalStateException("strike must be > 0");
            if (quantity.signum() <= 0) throw new IllegalStateException("quantity must be > 0");
            if (expiry.isBefore(tradeDate)) {
                throw new IllegalStateException("expiry cannot be before tradeDate");
            }
            return new DerivativeTrade(this);
        }
    }
}