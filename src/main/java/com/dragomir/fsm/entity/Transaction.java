package com.dragomir.fsm.entity;

import com.dragomir.fsm.state.TransactionState;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @Enumerated(EnumType.STRING)
    private TransactionState state;

    @Column
    private LocalDateTime stateElapsedTime;

    public Transaction(TransactionState state) {
        this.state = state;
    }
}
