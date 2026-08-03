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

    private EquityTrade(Builder bld) {
        this.tradeRef         = bld.tradeRef;
        this.instrumentSymbol = bld.instrumentSymbol;
        this.quantity         = bld.quantity;
        this.price            = bld.price;
        this.currency         = bld.currency;
        this.side             = bld.side;
        this.tradeDate        = bld.tradeDate;
        this.counterpartyId   = bld.counterpartyId;
    }

    public static Builder builder() { return new Builder(); }

    @Override public TradeRef tradeRef()    { return tradeRef; }
    @Override public LocalDate tradeDate()  { return tradeDate; }
    @Override public AssetClass assetClass(){ return AssetClass.EQUITY; }
    @Override public Money notional()       { return new Money(quantity.multiply(price), currency); }

    public String instrumentSymbol() { return instrumentSymbol; }
    public BigDecimal quantity()     { return quantity; }
    public BigDecimal price()        { return price; }
    public Currency currency()       { return currency; }
    public Side side()               { return side; }
    public long counterpartyId()     { return counterpartyId; }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof EquityTrade that) && this.tradeRef.equals(that.tradeRef);
    }

    @Override 
    public int hashCode() { 
        return tradeRef.hashCode(); 
    }

    @Override
    public String toString() {
        return "EquityTrade[ref=%s, symbol=%s, qty=%s, price=%s %s, side=%s]"
                .formatted(tradeRef, instrumentSymbol, quantity.toPlainString(), price.toPlainString(), currency.getCurrencyCode(), side);
        // NOTE: counterpartyId deliberately excluded to avoid PII logs.
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

        public Builder tradeRef(TradeRef ref)             { this.tradeRef = ref; return this; }
        public Builder instrumentSymbol(String sym)       { this.instrumentSymbol = sym; return this; }
        public Builder quantity(BigDecimal qty)           { this.quantity = qty; return this; }
        public Builder price(BigDecimal prc)              { this.price = prc; return this; }
        public Builder currency(Currency curr)            { this.currency = curr; return this; }
        public Builder currency(String code)              { return currency(Currency.getInstance(code)); }
        public Builder side(Side s)                       { this.side = s; return this; }
        public Builder tradeDate(LocalDate date)          { this.tradeDate = date; return this; }
        public Builder counterpartyId(long id)            { this.counterpartyId = id; return this; }

        public EquityTrade build() {
            Objects.requireNonNull(tradeRef,         "tradeRef is missing");
            Objects.requireNonNull(instrumentSymbol, "instrumentSymbol is missing");
            Objects.requireNonNull(quantity,         "quantity is missing");
            Objects.requireNonNull(price,            "price is missing");
            Objects.requireNonNull(currency,         "currency is missing");
            Objects.requireNonNull(side,             "side is missing");
            Objects.requireNonNull(tradeDate,        "tradeDate is missing");
            
            if (quantity.signum() <= 0) throw new IllegalStateException("quantity must be > 0");
            if (price.signum() <= 0)    throw new IllegalStateException("price must be > 0");
            
            return new EquityTrade(this);
        }
    }
}