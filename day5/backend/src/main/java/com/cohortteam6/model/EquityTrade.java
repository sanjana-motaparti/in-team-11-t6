package com.cohortteam6.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Currency;
import java.util.Objects;

public final class EquityTrade implements TradeType {

    private final TradeRef tradeRef;
    private final String instrumentSymbol;
    private final BigDecimal quantity;
    private final BigDecimal price;
    private final Currency currency;
    private final Side side;
    private final LocalDate tradeDate;
    private final long counterpartyId;

    private EquityTrade(Builder b) {
        this.tradeRef         = b.tradeRef;
        this.instrumentSymbol = b.instrumentSymbol;
        this.quantity         = b.quantity;
        this.price            = b.price;
        this.currency         = b.currency;
        this.side             = b.side;
        this.tradeDate        = b.tradeDate;
        this.counterpartyId   = b.counterpartyId;
    }

    public static Builder builder() { return new Builder(); }

    @Override public TradeRef tradeRef()    { return tradeRef; }
    @Override public LocalDate tradeDate()  { return tradeDate; }
    @Override public AssetClass assetClass(){ return AssetClass.EQUITY; }

    @Override public Money notional() {
        throw new UnsupportedOperationException("Not implemented");
    }

    public String instrumentSymbol() { return instrumentSymbol; }
    public BigDecimal quantity()     { return quantity; }
    public BigDecimal price()        { return price; }
    public Currency currency()       { return currency; }
    public Side side()               { return side; }
    public long counterpartyId()     { return counterpartyId; }

    @Override
    public boolean equals(Object o) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override public int hashCode() {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public String toString() {

        throw new UnsupportedOperationException("Not implemented");
    }

    public static final class Builder {
        private TradeRef tradeRef;
        private String instrumentSymbol;
        private BigDecimal quantity;
        private BigDecimal price;
        private Currency currency;
        private Side side;
        private LocalDate tradeDate;
        private long counterpartyId;

        public Builder tradeRef(TradeRef v)           { this.tradeRef = v;        return this; }
        public Builder instrumentSymbol(String v)     { this.instrumentSymbol = v; return this; }
        public Builder quantity(BigDecimal v)         { this.quantity = v;        return this; }
        public Builder price(BigDecimal v)            { this.price = v;           return this; }
        public Builder currency(Currency v)           { this.currency = v;        return this; }
        public Builder currency(String code)          { return currency(Currency.getInstance(code)); }
        public Builder side(Side v)                   { this.side = v;            return this; }
        public Builder tradeDate(LocalDate v)         { this.tradeDate = v;       return this; }
        public Builder counterpartyId(long v)         { this.counterpartyId = v;  return this; }

        public EquityTrade build() {

            throw new UnsupportedOperationException("Not implemented");
        }
    }
}
