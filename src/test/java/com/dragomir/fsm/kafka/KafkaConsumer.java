package com.dragomir.fsm.kafka;

import com.dragomir.fsm.entity.Transaction;
import lombok.Getter;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Getter
public class KafkaConsumer {
    private Transaction tx;

    @KafkaListener(topics = "${service2-topic}", groupId = "${service2-group-id}",
            concurrency = "${parallelism}", containerFactory = "containerFactory")
    public void listen(Transaction tx) {
        this.tx = tx;
    }
}
