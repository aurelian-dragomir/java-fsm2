package com.dragomir.fsm.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaAdmin;

@Configuration
public class KafkaAdminConfig {
    @Value("${service2-topic}")
    private String service2Topic;

    @Bean
    public KafkaAdmin.NewTopics createTopicsBean() {
        return new KafkaAdmin.NewTopics(
                TopicBuilder.name(service2Topic)
                        .build());
    }
}
