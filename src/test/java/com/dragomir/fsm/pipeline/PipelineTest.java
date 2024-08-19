package com.dragomir.fsm.pipeline;

import com.dragomir.fsm.entity.Transaction;
import com.dragomir.fsm.pipeline.step.Step;
import com.dragomir.fsm.service.TransactionService;
import com.dragomir.fsm.state.TransactionState;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;

@SpringBootTest
public class PipelineTest {

    @SpyBean
    private TransactionService transactionService;

    @Autowired
    private List<Step> steps;

    @Test
    public void runFullPipeline() throws Exception {
        var t = new Transaction(1L, TransactionState.NEW, LocalDateTime.now());
        var p = Pipeline.<Transaction, Transaction>of(steps);
        var tran = p.execute(t);
        assertTrue(tran.getState() == TransactionState.APPLIED);
    }

    @Test
    public void retryPipelineFromStep() throws Exception {
        var t = new Transaction(1L, TransactionState.WAITING_APPROVAL, LocalDateTime.now());
        int i = t.getState().ordinal();
        var p = Pipeline.<Transaction, Transaction>of(steps, i);
        var tran = p.execute(t);
        assertTrue(tran.getState() == TransactionState.APPLIED);
    }

    @Test
    public void testWithBadStateTransition() {
        var t = new Transaction(1L, TransactionState.NEW, LocalDateTime.now());

        doReturn(new Transaction(1L,
                TransactionState.WAITING_APPROVAL, LocalDateTime.now()))
                .when(transactionService).changeState(any(), eq(TransactionState.APPROVED));

        assertThrows(RuntimeException.class, () -> {
            Pipeline.<Transaction, Transaction>of(steps).execute(t);
        });
    }
}
