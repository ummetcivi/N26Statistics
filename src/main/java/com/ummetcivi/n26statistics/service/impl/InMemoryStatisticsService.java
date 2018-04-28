package com.ummetcivi.n26statistics.service.impl;

import com.ummetcivi.n26statistics.domain.Statistics;
import com.ummetcivi.n26statistics.service.StatisticsService;
import com.ummetcivi.n26statistics.storage.Storage;
import com.ummetcivi.n26statistics.util.DateProvider;

public class InMemoryStatisticsService implements StatisticsService {
    private final Storage storage;
    private final DateProvider dateProvider;

    public InMemoryStatisticsService(Storage storage, DateProvider dateProvider) {
        this.storage = storage;
        this.dateProvider = dateProvider;
    }

    @Override
    public Statistics getStatistic() {
        return storage.getStatistics(dateProvider.getCurrentMillis());
    }
}
