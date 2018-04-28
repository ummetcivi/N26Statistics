package com.ummetcivi.n26statistics.service.impl;

import com.ummetcivi.n26statistics.TestConstants;
import com.ummetcivi.n26statistics.TestUtils;
import com.ummetcivi.n26statistics.domain.Transaction;
import com.ummetcivi.n26statistics.storage.Storage;
import com.ummetcivi.n26statistics.util.DateProvider;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class InMemoryTransactionServiceTest {
    @Mock
    private Storage storage;

    @Mock
    private DateProvider dateProvider;

    @InjectMocks
    private InMemoryTransactionService underTest;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldCallStorageSaveAndReturnTrueWhenTimestampIsValid() {
        Transaction transaction = TestUtils.createTransaction(TestConstants.ANY_TIMESTAMP, TestConstants.ANY_AMOUNT);

        long currentTimeMillis = System.currentTimeMillis();

        Mockito.when(dateProvider.getCurrentMillis()).thenReturn(currentTimeMillis);

        boolean result = underTest.saveTransaction(transaction);

        Mockito.verify(storage).saveTransaction(transaction);
        Assert.assertTrue(result);
    }

    @Test
    public void shouldNotCallStorageAndReturnFalseWhenTimestampIsAheadNow() {
        Transaction transaction = TestUtils.createTransaction(TestConstants.ANY_TIMESTAMP + 60 * 1000,
                TestConstants.ANY_AMOUNT);

        long currentTimeMillis = System.currentTimeMillis();

        Mockito.when(dateProvider.getCurrentMillis()).thenReturn(currentTimeMillis);

        boolean result = underTest.saveTransaction(transaction);

        Mockito.verify(storage, Mockito.never()).saveTransaction(transaction);
        Assert.assertFalse(result);
    }

    @Test
    public void shouldNotCallStorageAndReturnFalseWhenTimestampIsBehindSixtyFiveSecondsThanNow() {
        Transaction transaction = TestUtils.createTransaction(TestConstants.ANY_TIMESTAMP - 65 * 1000,
                TestConstants.ANY_AMOUNT);

        long currentTimeMillis = System.currentTimeMillis();

        Mockito.when(dateProvider.getCurrentMillis()).thenReturn(currentTimeMillis);

        boolean result = underTest.saveTransaction(transaction);

        Mockito.verify(storage, Mockito.never()).saveTransaction(transaction);
        Assert.assertFalse(result);
    }
}