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
    static final int WINDOW_SIZE = 60 * 1000;
    /*
        Window size buffer for sliding window
     */
    private static final int ARRAY_SIZE = WINDOW_SIZE * 2;

    private static final Object LOCK = new Object();

    //Visible for testing
    final Statistics[] statisticsArray;

    public InMemoryStorage() {
        statisticsArray = new Statistics[ARRAY_SIZE];
        IntStream.range(0, ARRAY_SIZE).forEach(value -> {
            statisticsArray[value] = new Statistics();
        });
    }

    /*
        Complexity is O(120000) which is 2 times of the window size. It's not related with transaction count.
        Can be decreased with decreasing the sensitivity
     */
    @Override
    public void saveTransaction(Transaction transaction) {
        int index = (int) (transaction.getTimestamp() % ARRAY_SIZE);
        synchronized (LOCK) {
            IntStream.range(index, index + WINDOW_SIZE)
                    .forEachOrdered(i -> {
                        Statistics that = statisticsArray[i % ARRAY_SIZE];
                        if (transaction.getTimestamp() - that.getTimestamp() > DateProvider.MILLISECONDS_IN_SIXTY_SECONDS) {
                            Statistics statistics = new Statistics();
                            statistics.add(transaction.getAmount(), transaction.getTimestamp());
                            statisticsArray[i % ARRAY_SIZE] = statistics;
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
