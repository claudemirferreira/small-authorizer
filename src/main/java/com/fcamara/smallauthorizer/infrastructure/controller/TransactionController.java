package com.fcamara.smallauthorizer.infrastructure.controller;

import com.fcamara.smallauthorizer.application.usecases.CreateTransactionUsecase;
import com.fcamara.smallauthorizer.infrastructure.controller.dto.TransactionResquestDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping(value = "/transacoes")
@RequiredArgsConstructor
public class TransactionController {

    private final CreateTransactionUsecase createTransactionUsecase;

    @PostMapping
    public ResponseEntity<?> newTransaction(@Valid @RequestBody final TransactionResquestDTO transactionResquestDTO) {
        var newTransaction = createTransactionUsecase.execute(transactionResquestDTO.getNumber(), transactionResquestDTO.getAmount(), transactionResquestDTO.getPassword());
        return ResponseEntity.created(URI.create("transaction/" + newTransaction.getId())).build();
    }


}
