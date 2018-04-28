package com.ummetcivi.n26statistics.converter;

import com.ummetcivi.n26statistics.controller.dto.TransactionDto;
import com.ummetcivi.n26statistics.domain.Transaction;
import org.springframework.core.convert.converter.Converter;

import javax.validation.constraints.NotNull;

public class TransactionDtoToTransactionConverter implements Converter<TransactionDto, Transaction> {

    @Override
    public Transaction convert(@NotNull TransactionDto transactionDto) {
        Transaction transaction = new Transaction();

        transaction.setAmount(transactionDto.getAmount());
        transaction.setTimestamp(transactionDto.getTimestamp());

        return transaction;
    }
}
