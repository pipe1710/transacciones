package com.example.transacciones.controller;

import com.example.transacciones.domain.Transaction;
import com.example.transacciones.service.TransactionConfirmationService;
import com.example.transacciones.service.TransactionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class MainControllerTest {

    @Autowired
    WebTestClient webTestClient;


    @Test
    void processTransactions() {
        Transaction transaction = new Transaction();
        transaction.setFee(2.0);
        transaction.setAmount(100);


        webTestClient.post()
                .uri("/processTransactions")
                .bodyValue(List.of(transaction))
                .exchange()
                .expectStatus().isOk();
    }
}