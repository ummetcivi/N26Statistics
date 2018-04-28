package com.ummetcivi.n26statistics.service.impl;

import com.ummetcivi.n26statistics.TestConstants;
import com.ummetcivi.n26statistics.TestUtils;
import com.ummetcivi.n26statistics.domain.Statistics;
import com.ummetcivi.n26statistics.storage.Storage;
import com.ummetcivi.n26statistics.util.DateProvider;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class InMemoryStatisticsServiceTest {
    @Mock
    private Storage storage;

    @Mock
    private DateProvider dateProvider;

    @InjectMocks
    private InMemoryStatisticsService underTest;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldReturnStatisticsFromStorage() {
        Statistics statistics = TestUtils.createStatistics(TestConstants.ANY_TIMESTAMP, TestConstants.ANY_AMOUNT);

        Mockito.when(dateProvider.getCurrentMillis()).thenReturn(TestConstants.ANY_TIMESTAMP);
        Mockito.when(storage.getStatistics(TestConstants.ANY_TIMESTAMP)).thenReturn(statistics);

        Statistics result = underTest.getStatistic();

        Assert.assertNotNull(result);
        Assert.assertEquals(statistics.getAverage(), result.getAverage(), 0);
        Assert.assertEquals(statistics.getCount(), result.getCount());
        Assert.assertEquals(statistics.getMax(), result.getMax(), 0);
        Assert.assertEquals(statistics.getMin(), result.getMin(), 0);
        Assert.assertEquals(statistics.getSum(), result.getSum(), 0);
    }
}