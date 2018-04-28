package com.ummetcivi.n26statistics.service.impl;

import com.ummetcivi.n26statistics.domain.Transaction;
import com.ummetcivi.n26statistics.service.TransactionService;
import com.ummetcivi.n26statistics.storage.Storage;
import com.ummetcivi.n26statistics.util.DateProvider;

public class InMemoryTransactionService implements TransactionService {
    private final Storage storage;
    private final DateProvider dateProvider;

    public InMemoryTransactionService(Storage storage, DateProvider dateProvider) {
        this.storage = storage;
        this.dateProvider = dateProvider;
    }


    @Override
    public boolean saveTransaction(Transaction transaction) {
        long currentTime = dateProvider.getCurrentMillis();

        if (currentTime < transaction.getTimestamp() ||
                currentTime > transaction.getTimestamp() + DateProvider.SIXTY_SECONDS_IN_MILLIS) {
            return false;
        }

        storage.saveTransaction(transaction);
        return true;
    }
}
