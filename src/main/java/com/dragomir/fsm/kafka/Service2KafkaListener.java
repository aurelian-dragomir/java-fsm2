package com.dragomir.fsm.kafka;

import com.dragomir.fsm.entity.Transaction;
import com.dragomir.fsm.pipeline.Pipeline;
import com.dragomir.fsm.pipeline.step.Step;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class Service2KafkaListener {

    private final List<Step> steps;

    @KafkaListener(topics = "${service2-topic}", groupId = "${service2-group-id}",
            containerFactory = "containerFactory")
    public void listen(Transaction tx) {
        try {
            Pipeline.of(steps, tx.getState()).execute(tx);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }
}
