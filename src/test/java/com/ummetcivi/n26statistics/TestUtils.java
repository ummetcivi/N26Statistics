package com.ummetcivi.n26statistics;

import com.ummetcivi.n26statistics.controller.dto.TransactionDto;
import com.ummetcivi.n26statistics.controller.resource.StatisticsResource;
import com.ummetcivi.n26statistics.converter.StatisticsToStatisticsResourceConverter;
import com.ummetcivi.n26statistics.converter.TransactionDtoToTransactionConverter;
import com.ummetcivi.n26statistics.domain.Statistics;
import com.ummetcivi.n26statistics.domain.Transaction;

public class TestUtils {
    private static final TransactionDtoToTransactionConverter TRANSACTION_DTO_TO_TRANSACTION_CONVERTER =
            new TransactionDtoToTransactionConverter();
    private static final StatisticsToStatisticsResourceConverter STATISTICS_TO_STATISTICS_RESOURCE_CONVERTER =
            new StatisticsToStatisticsResourceConverter();

    public static TransactionDto createTransactionDto(long timestamp, double amount) {
        TransactionDto transactionDto = new TransactionDto();
        transactionDto.setAmount(amount);
        transactionDto.setTimestamp(timestamp);
        return transactionDto;
    }

    public static Transaction createTransaction(TransactionDto transactionDto) {
        return TRANSACTION_DTO_TO_TRANSACTION_CONVERTER.convert(transactionDto);
    }

    public static Transaction createTransaction(long timestamp, double amount) {
        Transaction transaction = new Transaction();
        transaction.setTimestamp(timestamp);
        transaction.setAmount(amount);
        return transaction;
    }

    public static StatisticsResource createEmptyStatisticsResource() {
        return new StatisticsResource();
    }

    public static StatisticsResource createStatisticsResource(Statistics statistics) {
        return STATISTICS_TO_STATISTICS_RESOURCE_CONVERTER.convert(statistics);
    }

    public static Statistics createStatistics(long timestamp, double amount) {
        Statistics statistics = new Statistics();
        statistics.add(amount, timestamp);
        return statistics;
    }
}
