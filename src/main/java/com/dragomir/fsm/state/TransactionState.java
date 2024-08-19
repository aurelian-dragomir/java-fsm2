package com.dragomir.fsm.state;

public enum TransactionState {
    NEW,
    WAITING_APPROVAL,
    APPROVED,
    APPLIED,
    AUDITED,
    PUBLISHED,
    ARCHIVED
}
