package com.dragomir.fsm;

import com.dragomir.fsm.entity.Transaction;
import com.dragomir.fsm.state.TransactionState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.time.LocalDateTime;

@SpringBootApplication
@EnableScheduling
public class FsmApplication {

    public static void main(String[] args) {
        SpringApplication.run(FsmApplication.class, args);
    }

    @Autowired
    private KafkaTemplate<Void, Transaction> prod;

    //    @Bean
    public CommandLineRunner runAtStartup() {
        return args -> {
            var t = new Transaction(1L, TransactionState.NEW, LocalDateTime.now());
            prod.sendDefault(t);
        };
    }
}
