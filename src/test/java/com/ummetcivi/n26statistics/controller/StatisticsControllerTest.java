package com.ummetcivi.n26statistics.controller;

import com.ummetcivi.n26statistics.TestConstants;
import com.ummetcivi.n26statistics.TestUtils;
import com.ummetcivi.n26statistics.controller.resource.StatisticsResource;
import com.ummetcivi.n26statistics.domain.Statistics;
import com.ummetcivi.n26statistics.service.StatisticsService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.core.convert.support.ConfigurableConversionService;

public class StatisticsControllerTest {
    @InjectMocks
    private StatisticsController underTest;

    @Mock
    private StatisticsService statisticsService;

    @Mock
    private ConfigurableConversionService configurableConversionService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldReturnStatistics() {
        Statistics statistics = TestUtils.createStatistics(TestConstants.ANY_TIMESTAMP, TestConstants.ANY_AMOUNT);

        StatisticsResource statisticsResource = TestUtils.createStatisticsResource(statistics);

        Mockito.when(statisticsService.getStatistic()).thenReturn(statistics);
        Mockito.when(configurableConversionService.convert(statistics, StatisticsResource.class)).thenReturn(statisticsResource);

        StatisticsResource result = underTest.getStatistic();

        Mockito.verify(statisticsService).getStatistic();

        Assert.assertNotNull(result);
        Assert.assertEquals(statistics.getAverage(), result.getAvg(), 0);
        Assert.assertEquals(statistics.getCount(), result.getCount());
        Assert.assertEquals(statistics.getMax(), result.getMax(), 0);
        Assert.assertEquals(statistics.getMin(), result.getMin(), 0);
        Assert.assertEquals(statistics.getSum(), result.getSum(), 0);
    }

    @Test
    public void shouldReturnEmptyStatisticsWhenNoValidTransactionFound() {
        StatisticsResource statisticsResource = TestUtils.createEmptyStatisticsResource();

        Mockito.when(configurableConversionService.convert(null, StatisticsResource.class)).thenReturn(statisticsResource);

        StatisticsResource result = underTest.getStatistic();

        Mockito.verify(statisticsService).getStatistic();

        Assert.assertNotNull(result);
        Assert.assertEquals(0, result.getAvg(), 0);
        Assert.assertEquals(0, result.getCount());
        Assert.assertEquals(0, result.getMax(), 0);
        Assert.assertEquals(0, result.getMin(), 0);
        Assert.assertEquals(0, result.getSum(), 0);
    }
}