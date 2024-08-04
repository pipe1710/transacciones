package com.example.transacciones.controller;

import com.example.transacciones.domain.Transaction;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
public class AuxiliaryController {

    @PostMapping("/calculateFees")
    public Mono<ResponseEntity<Transaction>> calculateFees(@RequestBody @Valid Transaction transaction){
        //simular los calculos de una comision
        transaction.setFee(transaction.getAmount() * 0.02); // comision del 2%
        return Mono.just(ResponseEntity.status(HttpStatus.OK).body(transaction));
    }

    @PostMapping("/executeBatch")
    public Mono<Void> executeBatch(@RequestBody List<Transaction> list){
        //simular ejecucion de transacciones
        list.forEach(transaction -> {
            System.out.println("Executing transaction: "+transaction);
        });
        return Mono.empty();
    }
}
