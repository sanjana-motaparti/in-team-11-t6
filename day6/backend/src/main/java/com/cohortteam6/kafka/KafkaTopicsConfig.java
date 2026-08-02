package com.cohortteam6.kafka;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("!dev & !test")
public class KafkaTopicsConfig {

}
