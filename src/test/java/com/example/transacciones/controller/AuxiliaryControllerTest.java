package com.example.transacciones.controller;

import com.example.transacciones.domain.Transaction;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.List;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class AuxiliaryControllerTest {

    @Autowired
    WebTestClient webTestClient;

    @Test
    void calculateFees() {
        Transaction transaction = new Transaction();
        transaction.setAmount(0);

        webTestClient.post()
                .uri("/calculateFees")
                .bodyValue(transaction)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.fee").isEqualTo(2.0);

    }

    @Test
    void executeBatch() {
        Transaction t1 = new Transaction();
        t1.setAmount(100);
        t1.setFee(2.0);

        Transaction t2 = new Transaction();
        t2.setAmount(200);
        t2.setFee(4.0);

        webTestClient.post()
                .uri("/executeBatch")
                .bodyValue(List.of(t1,t2))
                .exchange()
                .expectStatus().isOk();

    }
}