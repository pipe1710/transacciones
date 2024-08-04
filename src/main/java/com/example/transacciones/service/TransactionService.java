package com.example.transacciones.service;

import com.example.transacciones.domain.Transaction;
import com.example.transacciones.domain.TransactionRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class TransactionService {

    private final TransactionRepository repository;
    private final IProcessRestClient client;


    public TransactionService(TransactionRepository repository, IProcessRestClient client){
        this.repository = repository;
        this.client = client;
    }


    public Mono<Transaction> prepareTransactions(Flux<Transaction> transactions){
        return transactions
                .map(this::validateTransaction)
                .flatMap(this::calculateFees)
                .reduce(this::aggregateTransactions)
                .flatMap(repository::save);

    }

    private Transaction validateTransaction(Transaction transaction) {
        if(transaction.getAmount() <= 0){
            throw new IllegalArgumentException("Invalid transaction amount");
        }
        return transaction;
    }

    private Mono<Transaction> calculateFees(Transaction transaction){
        return client.processFee(transaction).map(res -> {
                    transaction.setFee(res.getFee());
                    return transaction;
                });


    }

    private Transaction aggregateTransactions(Transaction t1, Transaction t2) {
        t1.setAmount(t1.getAmount() + t2.getAmount());
        t1.setFee(t1.getFee() + t2.getFee());
        return t1;
    }
}
