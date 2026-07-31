package com.cohortteam6.dto;

import com.cohortteam6.repository.entity.Trade;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TradeMapper {

    @Mapping(source = "instrument.id", target = "instrumentId")
    @Mapping(source = "instrument.symbol", target = "instrumentSymbol")
    @Mapping(source = "counterparty.id", target = "counterpartyId")
    @Mapping(source = "counterparty.name", target = "counterpartyName")
    TradeResponse toResponse(Trade trade);
}
