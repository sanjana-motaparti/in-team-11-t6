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

    private DerivativeTrade(Builder b) {
        this.tradeRef       = b.tradeRef;
        this.underlying     = b.underlying;
        this.strike         = b.strike;
        this.quantity       = b.quantity;
        this.expiry         = b.expiry;
        this.optionType     = b.optionType;
        this.currency       = b.currency;
        this.side           = b.side;
        this.tradeDate      = b.tradeDate;
        this.counterpartyId = b.counterpartyId;
    }

    public static Builder builder() { return new Builder(); }

    @Override public TradeRef tradeRef()     { return tradeRef; }
    @Override public LocalDate tradeDate()   { return tradeDate; }
    @Override public AssetClass assetClass() { return AssetClass.DERIVATIVE; }

    @Override public Money notional() {
        throw new UnsupportedOperationException("Not implemented");
    }

    public String underlying()       { return underlying; }
    public BigDecimal strike()       { return strike; }
    public BigDecimal quantity()     { return quantity; }
    public LocalDate expiry()        { return expiry; }
    public OptionType optionType()   { return optionType; }
    public Currency currency()       { return currency; }
    public Side side()               { return side; }
    public long counterpartyId()     { return counterpartyId; }

    @Override public boolean equals(Object o) {
        throw new UnsupportedOperationException("Not implemented");
    }
    @Override public int hashCode() {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override public String toString() {
        throw new UnsupportedOperationException("Not implemented");
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

        public Builder tradeRef(TradeRef v)        { this.tradeRef = v; return this; }
        public Builder underlying(String v)        { this.underlying = v; return this; }
        public Builder strike(BigDecimal v)        { this.strike = v; return this; }
        public Builder quantity(BigDecimal v)      { this.quantity = v; return this; }
        public Builder expiry(LocalDate v)         { this.expiry = v; return this; }
        public Builder optionType(OptionType v)    { this.optionType = v; return this; }
        public Builder currency(String code)       { this.currency = Currency.getInstance(code); return this; }
        public Builder side(Side v)                { this.side = v; return this; }
        public Builder tradeDate(LocalDate v)      { this.tradeDate = v; return this; }
        public Builder counterpartyId(long v)      { this.counterpartyId = v; return this; }

        public DerivativeTrade build() {

            throw new UnsupportedOperationException("Not implemented");
        }
    }
}
