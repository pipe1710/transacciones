package com.example.transacciones.controller;

import com.example.transacciones.domain.Transaction;
import com.example.transacciones.service.TransactionConfirmationService;
import com.example.transacciones.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@RestController
public class MainController {
    @Autowired
    private TransactionService transactionService;

    @Autowired
    private TransactionConfirmationService transactionConfirmationService;

    @PostMapping("/processTransactions")
    public Mono<Void> processTransactions(@RequestBody Flux<Transaction> transactions){
        return transactionService.prepareTransactions(transactions)
                .zipWhen(preparedTransaction -> transactionConfirmationService.confirmTransactions(Flux.just(preparedTransaction)))
                .then();
    }

}
