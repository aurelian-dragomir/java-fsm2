package com.dragomir.fsm.repository;

import com.dragomir.fsm.entity.Transaction;
import com.dragomir.fsm.state.TransactionState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TransactionRepository extends
        JpaRepository<Transaction, Long> {

    List<Transaction> findByStateInAndStateElapsedTimeBefore(List<TransactionState> states,
                                                             LocalDateTime time);
}
