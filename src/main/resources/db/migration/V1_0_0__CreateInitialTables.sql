CREATE TABLE transaction
(
    id   INTEGER generated always AS identity,
    state VARCHAR(32) check(state in ('NEW','WAITING_APPROVAL', 'APPROVED','APPLIED',
        'AUDITED','PUBLISHED','ARCHIVED')),
    state_elapsed_time timestamp,
    CONSTRAINT pk_person PRIMARY KEY(id)
);