package com.dragomir.fsm.scheduler;

import com.dragomir.fsm.entity.Transaction;
import com.dragomir.fsm.pipeline.Pipeline;
import com.dragomir.fsm.pipeline.step.Step;
import com.dragomir.fsm.service.TransactionService;
import com.dragomir.fsm.state.TransactionState;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

import static com.dragomir.fsm.state.TransactionState.*;

//@Component
@RequiredArgsConstructor
@Slf4j
public class RetryScheduler {
    private final TransactionService transactionService;
    private final List<Step> steps;
    private static final List<TransactionState> STATE_LIST =
            List.of(NEW, WAITING_APPROVAL, APPROVED);

    @Scheduled(fixedRate = 5000)
    public void retryPipeline() {
        List<Transaction> transactions = transactionService.findByStateInAndStateElapsedTimeBefore(STATE_LIST,
                LocalDateTime.now().minusMinutes(5));
        if (transactions.isEmpty()) {
            log.info("No transactions to retry");
            return;
        }

        log.info("Found {} transactions to retry", transactions.size());
        transactions.forEach(tx -> {
            log.info("Retrying transaction {}", tx.getId());
            Pipeline.of(steps, tx.getState()).execute(tx);
        });
    }
}
