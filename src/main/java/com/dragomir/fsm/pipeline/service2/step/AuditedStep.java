package com.dragomir.fsm.pipeline.service2.step;

import com.dragomir.fsm.entity.Transaction;
import com.dragomir.fsm.pipeline.step.Step;
import com.dragomir.fsm.service.TransactionService;
import com.dragomir.fsm.state.TransactionState;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(3)
@Slf4j
@RequiredArgsConstructor
public class AuditedStep implements Step<Transaction, Transaction> {
    private final TransactionService transactionService;

    @Override
    public Transaction compute(Transaction input) {

        //update in db
        Transaction t = transactionService.changeState(input, TransactionState.AUDITED);
        log.info("Executed step AuditedStep");
        return t;
    }
}