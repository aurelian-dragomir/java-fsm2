package com.dragomir.fsm.pipeline.step;

import com.dragomir.fsm.entity.Transaction;
import com.dragomir.fsm.state.TransactionState;
import com.dragomir.fsm.service.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(3)
@Slf4j
@RequiredArgsConstructor
public class AppliedStep implements Step<Transaction, Transaction> {
    private final TransactionService transactionService;

    @Override
    public Transaction compute(Transaction input) {

        //update in db
        Transaction t = transactionService.changeState(input, TransactionState.APPLIED);
        log.info("Executed step AppliedStep");
        return t;
    }
}