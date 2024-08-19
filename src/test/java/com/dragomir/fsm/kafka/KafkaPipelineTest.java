package com.dragomir.fsm.kafka;

import com.dragomir.fsm.entity.Transaction;
import com.dragomir.fsm.state.TransactionState;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.Environment;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Testcontainers
@Import(KafkaConsumer.class)
@Slf4j
public class KafkaPipelineTest {

    @Autowired
    private KafkaConsumer kafkaConsumer;

    @Autowired
    private Environment env;

    @Autowired
    private KafkaTemplate<Void, Transaction> prod;

    @Container
    static KafkaContainer kafkaContainer = new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:6.2.1"));

    @DynamicPropertySource
    static void kafkaProperties(DynamicPropertyRegistry registry) {
        log.info(kafkaContainer.getBootstrapServers());
        registry.add("spring.kafka.bootstrap-servers", kafkaContainer::getBootstrapServers);
    }

    @Test
    @Rollback(value = false)
    public void test1() throws IOException {
        var tx = new Transaction(1L, TransactionState.NEW, LocalDateTime.now());
        prod.executeInTransaction(kt -> kt.sendDefault(tx));

        await()
                .atMost(10, TimeUnit.SECONDS)
                .pollInterval(1, TimeUnit.SECONDS)
                .until(() -> kafkaConsumer.getTx() != null);
        assertTrue(kafkaConsumer.getTx().getState() == TransactionState.APPLIED);
    }
}
