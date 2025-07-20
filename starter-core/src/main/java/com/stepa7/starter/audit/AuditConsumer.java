package com.stepa7.starter.audit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@ConditionalOnProperty(name = "audit.mode", havingValue = "kafka")
public class AuditConsumer {
    private static final String TOPIC = "audit";
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @KafkaListener(topics = TOPIC, groupId = "my-consumer-group")
    public void consume(String info) {
        logger.info("Received message: " + info);
    }
}