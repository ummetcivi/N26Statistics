package com.ummetcivi.n26statistics.storage;

import com.ummetcivi.n26statistics.domain.Statistics;
import com.ummetcivi.n26statistics.domain.Transaction;

public interface Storage {
    void saveTransaction(Transaction transaction);

    Statistics getStatistics(long currentTimestamp);
}
