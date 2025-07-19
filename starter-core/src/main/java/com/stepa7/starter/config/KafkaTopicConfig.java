package com.stepa7.starter.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
@ConditionalOnProperty(name = "audit.mode", havingValue = "kafka")
public class KafkaTopicConfig {

    @Bean
    public NewTopic logTopic() {
        return TopicBuilder.name("audit")
                .build();
    }
}
