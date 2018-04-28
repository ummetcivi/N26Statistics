package com.ummetcivi.n26statistics.storage.impl;

import com.ummetcivi.n26statistics.domain.Statistics;
import com.ummetcivi.n26statistics.domain.Transaction;
import com.ummetcivi.n26statistics.storage.Storage;
import com.ummetcivi.n26statistics.util.DateProvider;

import java.util.stream.IntStream;

public class InMemoryStorage implements Storage {
    /*
        Sliding window size
     */
    protected static final int WINDOW_SIZE = 60 * 1000;
    /*
        +1 is A buffer for sliding window
     */
    protected static final int ARRAY_SIZE = 60 * 1000 + 1;

    private static final Object LOCK = new Object();

    //Visible for testing
    protected final Statistics[] statisticsArray;

    public InMemoryStorage() {
        statisticsArray = new Statistics[ARRAY_SIZE];
        IntStream.range(0, ARRAY_SIZE).forEach(value -> {
            statisticsArray[value] = new Statistics();
        });
    }

    /*
        Complexity is O(60000) which is milliseconds in minute, it's not related with transaction count.
        Can be decreased with decreasing the sensitivity
     */
    @Override
    public void saveTransaction(Transaction transaction) {
        int index = (int) (transaction.getTimestamp() % ARRAY_SIZE);
        synchronized (LOCK) {
            IntStream.range(index, index + WINDOW_SIZE)
                    .forEachOrdered(value -> {
                        Statistics that = statisticsArray[value % ARRAY_SIZE];
                        if (transaction.getTimestamp() - that.getTimestamp() > 1) {
                            Statistics statistics = new Statistics();
                            statistics.add(transaction.getAmount(), transaction.getTimestamp());
                            statisticsArray[value % ARRAY_SIZE] = statistics;
                        } else {
                            that.add(transaction.getAmount(), transaction.getTimestamp());
                        }
                    });
        }
    }

    /*
        Complexity is O(1)
     */
    @Override
    public Statistics getStatistics(long currentTimestamp) {
        int index = (int) (currentTimestamp % ARRAY_SIZE);

        synchronized (LOCK) {
            if (statisticsArray[index].getTimestamp() >= currentTimestamp - DateProvider.MILLISECONDS_IN_SIXTY_SECONDS) {
                return statisticsArray[index];
            }
        }
        return new Statistics();
    }
}
