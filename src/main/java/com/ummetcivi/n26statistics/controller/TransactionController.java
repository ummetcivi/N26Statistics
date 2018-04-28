package com.ummetcivi.n26statistics.controller;

import com.ummetcivi.n26statistics.controller.dto.TransactionDto;
import com.ummetcivi.n26statistics.domain.Transaction;
import com.ummetcivi.n26statistics.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.support.ConfigurableConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TransactionController {
    private final TransactionService transactionService;
    private final ConfigurableConversionService configurableConversionService;

    @Autowired
    public TransactionController(TransactionService transactionService, ConfigurableConversionService configurableConversionService) {
        this.transactionService = transactionService;
        this.configurableConversionService = configurableConversionService;
    }

    @PostMapping("/transactions")
    public ResponseEntity<Void> saveTransaction(@RequestBody TransactionDto transactionDto) {
        Transaction transaction = configurableConversionService.convert(transactionDto, Transaction.class);
        boolean isSaved = transactionService.saveTransaction(transaction);

        if (!isSaved) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
