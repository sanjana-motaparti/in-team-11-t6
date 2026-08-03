package com.cohortteam6.kafka;

import com.cohortteam6.dto.TradeEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class ReconciliationConsumer {

    private static final Logger log = LoggerFactory.getLogger(ReconciliationConsumer.class);

    public void onTradeEvent(TradeEvent event) {
        throw new UnsupportedOperationException("Not implemented");
    }
}
