package com.dragomir.fsm.controller;

import com.dragomir.fsm.entity.Transaction;
import com.dragomir.fsm.state.TransactionState;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/kafka")
@RequiredArgsConstructor
public class KafkaController {

    private final KafkaTemplate<Void, Transaction> prod;

    @PostMapping
    public String send() {
        prod.executeInTransaction(kt -> kt.sendDefault(new Transaction(1L, TransactionState.NEW,
                LocalDateTime.now())));
        return "OK";
    }
}
