package com.ummetcivi.n26statistics.storage.impl;

import com.ummetcivi.n26statistics.TestConstants;
import com.ummetcivi.n26statistics.TestUtils;
import com.ummetcivi.n26statistics.domain.Statistics;
import com.ummetcivi.n26statistics.domain.Transaction;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

public class InMemoryStorageTest {
    @InjectMocks
    private InMemoryStorage underTest;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldAddStatisticsIntoStorageArrayAndShiftWindowSizeTimes() {
        Transaction transaction = TestUtils.createTransaction(0, TestConstants.ANY_AMOUNT);
        Statistics statistics = new Statistics();
        statistics.add(transaction.getAmount(), transaction.getTimestamp());

        underTest.saveTransaction(transaction);

        for (int i = 0; i < InMemoryStorage.WINDOW_SIZE; i++) {
            Assert.assertEquals(statistics.getSum(), underTest.statisticsArray[i].getSum(), 0);
        }
    }

    @Test
    public void shouldCalculateNewStatisticsAndShiftWindowSizeTimesWhenOtherStatisticsExist() {
        Transaction existingTransaction = TestUtils.createTransaction(0, TestConstants.ANY_AMOUNT);
        Statistics existingStatistics = new Statistics();
        existingStatistics.add(existingTransaction.getAmount(), existingTransaction.getTimestamp());

        Transaction transactionToAdd = TestUtils.createTransaction(1, TestConstants.ANY_OTHER_AMOUNT);
        Statistics statisticsToAdd = new Statistics();
        statisticsToAdd.add(transactionToAdd.getAmount(), transactionToAdd.getTimestamp());

        Statistics combinedStatistics = new Statistics();
        combinedStatistics.add(existingTransaction.getAmount(), existingTransaction.getTimestamp());
        combinedStatistics.add(transactionToAdd.getAmount(), transactionToAdd.getTimestamp());

        underTest.saveTransaction(existingTransaction);

        underTest.saveTransaction(transactionToAdd);

        Assert.assertEquals(existingStatistics.getCount(), underTest.statisticsArray[0].getCount());
        for (int i = 1; i < InMemoryStorage.WINDOW_SIZE; i++) {
            Assert.assertEquals(combinedStatistics.getCount(), underTest.statisticsArray[i].getCount());
        }
        Assert.assertEquals(statisticsToAdd.getCount(), underTest.statisticsArray[InMemoryStorage.WINDOW_SIZE].getCount());
    }

    @Test
    public void shouldAddNewStatisticsWhenExistingStatisticsIsOneMinuteOlderThanNewOne() {
        Transaction existingTransaction = TestUtils.createTransaction(0, TestConstants.ANY_AMOUNT);
        Statistics existingStatistics = new Statistics();
        existingStatistics.add(existingTransaction.getAmount(), existingTransaction.getTimestamp());

        Transaction transactionToAdd = TestUtils.createTransaction(2 * 60 * 1000, TestConstants.ANY_OTHER_AMOUNT);
        Statistics statisticsToAdd = new Statistics();
        statisticsToAdd.add(transactionToAdd.getAmount(), transactionToAdd.getTimestamp());

        underTest.saveTransaction(existingTransaction);

        underTest.saveTransaction(transactionToAdd);

        Assert.assertEquals(statisticsToAdd.getSum(), underTest.statisticsArray[0].getSum(), 0);
    }

    @Test
    public void shouldReturnStatisticsWhenValidTransitionsExist() {
        Statistics expectedStatistics = new Statistics();

        for (int i = 0; i < 5; i++) {
            Transaction transaction = TestUtils.createTransaction(i, TestConstants.ANY_AMOUNT);
            expectedStatistics.add(transaction.getAmount(), transaction.getTimestamp());
            underTest.saveTransaction(transaction);
        }

        Statistics statistics = underTest.getStatistics(60 * 1000 - 1);

        Assert.assertEquals(expectedStatistics.getCount(), statistics.getCount(), 0);
        Assert.assertEquals(expectedStatistics.getSum(), statistics.getSum(), 0);
        Assert.assertEquals(expectedStatistics.getAverage(), statistics.getAverage(), 0);
        Assert.assertEquals(expectedStatistics.getMax(), statistics.getMax(), 0);
    }

    @Test
    public void shouldReturnStatisticsOnlyForValidTransactions() {
        Statistics expectedStatistics = new Statistics();

        for (int i = 0; i < 5; i++) {
            Transaction transaction = TestUtils.createTransaction(i, TestConstants.ANY_AMOUNT);
            underTest.saveTransaction(transaction);
            if (i == 4) {
                expectedStatistics.add(transaction.getAmount(), transaction.getTimestamp());
            }
        }

        Statistics statistics = underTest.getStatistics(60 * 1000 + 3);

        Assert.assertEquals(expectedStatistics.getCount(), statistics.getCount(), 0);
        Assert.assertEquals(expectedStatistics.getSum(), statistics.getSum(), 0);
        Assert.assertEquals(expectedStatistics.getAverage(), statistics.getAverage(), 0);
        Assert.assertEquals(expectedStatistics.getMax(), statistics.getMax(), 0);
    }

    @Test
    public void shouldReturnEmptyStatisticsWhenNoValidTransactionsExist() {
        Statistics expectedStatistics = new Statistics();

        for (int i = 0; i < 100; i++) {
            Transaction transaction = TestUtils.createTransaction(i, TestConstants.ANY_AMOUNT);
            underTest.saveTransaction(transaction);
        }

        Statistics statistics = underTest.getStatistics(60 * 1000 + 100);

        Assert.assertEquals(expectedStatistics.getCount(), statistics.getCount(), 0);
        Assert.assertEquals(expectedStatistics.getSum(), statistics.getSum(), 0);
        Assert.assertEquals(expectedStatistics.getAverage(), statistics.getAverage(), 0);
        Assert.assertEquals(expectedStatistics.getMax(), statistics.getMax(), 0);
    }
}