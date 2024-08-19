insert into transaction(state, state_elapsed_time) values('APPROVED',CURRENT_TIMESTAMP - interval '10' minute );
insert into transaction(state, state_elapsed_time) values('NEW',CURRENT_TIMESTAMP - interval '10' minute );
insert into transaction(state, state_elapsed_time) values('WAITING_APPROVAL',CURRENT_TIMESTAMP - interval '10' minute );
insert into transaction(state, state_elapsed_time) values('APPLIED',CURRENT_TIMESTAMP - interval '10' minute );