package com.dragomir.fsm.kafka;

import com.dragomir.fsm.entity.Transaction;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class Service2KafkaListener {

    @KafkaListener(topics = "${service2-topic}", groupId = "${service2-group-id}",
            containerFactory = "containerFactory")
    public void listen(Transaction tx) {
        try {

        } catch (Exception e) {
            //swallow error as the service 2 scheduler will handle retry starting from APPLIED state
        }
    }
}
