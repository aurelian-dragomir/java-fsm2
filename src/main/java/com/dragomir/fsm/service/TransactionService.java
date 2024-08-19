package com.dragomir.fsm.service;

import com.dragomir.fsm.entity.Transaction;
import com.dragomir.fsm.repository.TransactionRepository;
import com.dragomir.fsm.state.TransactionState;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static com.dragomir.fsm.state.TransactionState.*;

@Service
@RequiredArgsConstructor
public class TransactionService {
    private final TransactionRepository transactionRepository;

    private static final Map<TransactionState, TransactionState> STATE_MAP =
            Map.of(NEW, WAITING_APPROVAL,
                    WAITING_APPROVAL, APPROVED,
                    APPROVED, APPLIED,
                    APPLIED, AUDITED);

    @Transactional
    public Transaction changeState(Transaction tx, TransactionState state) {
        if (STATE_MAP.get(tx.getState()) != state) {
            throw new RuntimeException(String.format("Can't go from state %s to %s!",
                    tx.getState(), state));
        }
        tx.setState(state);
        transactionRepository.save(tx);
        return tx;
    }

    public TransactionState getNextState(TransactionState currentState) {
        return STATE_MAP.get(currentState);
    }

    public List<Transaction> findAll() {
        return transactionRepository.findAll();
    }

    public List<Transaction> findByStateInAndStateElapsedTimeBefore(List<TransactionState> states,
                                                                    LocalDateTime time) {
        return transactionRepository.findByStateInAndStateElapsedTimeBefore(states, time);
    }
}
