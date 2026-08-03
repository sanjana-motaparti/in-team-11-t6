package com.cohortteam6.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

public final class TradeFactory {

    private TradeFactory() { }

    public static TradeType create(String assetClass, Map<String, Object> reqPayload) {
        TradeType.AssetClass typeOfAsset = TradeType.AssetClass.valueOf(assetClass.toUpperCase());
        return switch (typeOfAsset) {
            case EQUITY     -> buildEquity(reqPayload);
            case FX         -> buildFx(reqPayload);
            case BOND       -> buildBond(reqPayload);
            case DERIVATIVE -> buildDerivative(reqPayload);
        };
    }

    private static EquityTrade buildEquity(Map<String, Object> reqPayload) {
        return EquityTrade.builder()
                .tradeRef(TradeRef.of((String) reqPayload.get("tradeRef")))
                .instrumentSymbol((String) reqPayload.get("symbol"))
                .quantity(new BigDecimal(reqPayload.get("quantity").toString()))
                .price(new BigDecimal(reqPayload.get("price").toString()))
                .currency((String) reqPayload.get("currency"))
                .side(Side.valueOf((String) reqPayload.get("side")))
                .tradeDate(LocalDate.parse((String) reqPayload.get("tradeDate")))
                .counterpartyId(((Number) reqPayload.get("counterpartyId")).longValue())
                .build();
    }

    private static FXTrade buildFx(Map<String, Object> reqPayload) {
        return FXTrade.builder()
                .tradeRef(TradeRef.of((String) reqPayload.get("tradeRef")))
                .ccy1((String) reqPayload.get("ccy1"))
                .ccy2((String) reqPayload.get("ccy2"))
                .notionalCcy1(new BigDecimal(reqPayload.get("notionalCcy1").toString()))
                .fxRate(new BigDecimal(reqPayload.get("fxRate").toString()))
                .side(Side.valueOf((String) reqPayload.get("side")))
                .tradeDate(LocalDate.parse((String) reqPayload.get("tradeDate")))
                .counterpartyId(((Number) reqPayload.get("counterpartyId")).longValue())
                .build();
    }

    private static BondTrade buildBond(Map<String, Object> reqPayload) {
        return BondTrade.builder()
                .tradeRef(TradeRef.of((String) reqPayload.get("tradeRef")))
                .isin((String) reqPayload.get("isin"))
                .faceValue(new BigDecimal(reqPayload.get("faceValue").toString()))
                .couponRate(new BigDecimal(reqPayload.get("couponRate").toString()))
                .maturityDate(LocalDate.parse((String) reqPayload.get("maturityDate")))
                .currency((String) reqPayload.get("currency"))
                .side(Side.valueOf((String) reqPayload.get("side")))
                .tradeDate(LocalDate.parse((String) reqPayload.get("tradeDate")))
                .counterpartyId(((Number) reqPayload.get("counterpartyId")).longValue())
                .build();
    }

    private static DerivativeTrade buildDerivative(Map<String, Object> reqPayload) {
        return DerivativeTrade.builder()
                .tradeRef(TradeRef.of((String) reqPayload.get("tradeRef")))
                .underlying((String) reqPayload.get("underlying"))
                .strike(new BigDecimal(reqPayload.get("strike").toString()))
                .quantity(new BigDecimal(reqPayload.get("quantity").toString()))
                .expiry(LocalDate.parse((String) reqPayload.get("expiry")))
                .optionType(DerivativeTrade.OptionType.valueOf((String) reqPayload.get("optionType")))
                .currency((String) reqPayload.get("currency"))
                .side(Side.valueOf((String) reqPayload.get("side")))
                .tradeDate(LocalDate.parse((String) reqPayload.get("tradeDate")))
                .counterpartyId(((Number) reqPayload.get("counterpartyId")).longValue())
                .build();
    }
}