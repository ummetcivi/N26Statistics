package com.ummetcivi.n26statistics.service;

import com.ummetcivi.n26statistics.domain.Transaction;

public interface TransactionService {
    boolean saveTransaction(Transaction transaction);
}
