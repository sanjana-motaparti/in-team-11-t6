package com.cohortteam6.model;

import java.math.BigDecimal;
import java.math.RoundingMode;

public enum ReconciliationRule {

    EXACT(BigDecimal.ZERO, BigDecimal.ZERO),
    PRICE_TOLERANCE_1PCT(new BigDecimal("0.01"), BigDecimal.ZERO),
    PRICE_TOLERANCE_50BPS(new BigDecimal("0.005"), BigDecimal.ZERO),
    QTY_TOLERANCE_5UNITS(BigDecimal.ZERO, new BigDecimal("5")),
    LOOSE(new BigDecimal("0.05"), new BigDecimal("10"));

    private final BigDecimal priceTolerancePct;
    private final BigDecimal qtyToleranceAbs;

    ReconciliationRule(BigDecimal priceTolerancePct, BigDecimal qtyToleranceAbs) {
        this.priceTolerancePct = priceTolerancePct;
        this.qtyToleranceAbs   = qtyToleranceAbs;
    }

    public BigDecimal priceTolerancePct() { return priceTolerancePct; }
    public BigDecimal qtyToleranceAbs()   { return qtyToleranceAbs; }

    public boolean matches(BigDecimal internalPrice, BigDecimal internalQty,
                           BigDecimal externalPrice, BigDecimal externalQty) {
        BigDecimal absPriceDiff = internalPrice.subtract(externalPrice).abs();
        BigDecimal priceDriftPct = internalPrice.signum() == 0
                ? BigDecimal.ZERO
                : absPriceDiff.divide(internalPrice, 6, RoundingMode.HALF_UP);
        
        BigDecimal absQtyDiff = internalQty.subtract(externalQty).abs();

        boolean isPriceOk = priceDriftPct.compareTo(priceTolerancePct) <= 0;
        boolean isQtyOk   = absQtyDiff.compareTo(qtyToleranceAbs) <= 0;
        
        return isPriceOk && isQtyOk;
    }
}