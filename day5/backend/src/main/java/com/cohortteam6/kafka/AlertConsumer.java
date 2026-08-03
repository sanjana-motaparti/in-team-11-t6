package com.cohortteam6.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class AlertConsumer {

    private static final Logger log = LoggerFactory.getLogger(AlertConsumer.class);

    public void onAlert(String payload) {
        throw new UnsupportedOperationException("Not implemented");
    }
}
