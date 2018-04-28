package com.ummetcivi.n26statistics.converter;

import com.ummetcivi.n26statistics.controller.resource.StatisticsResource;
import com.ummetcivi.n26statistics.domain.Statistics;
import org.springframework.core.convert.converter.Converter;

public class StatisticsToStatisticsResourceConverter implements Converter<Statistics, StatisticsResource> {
    @Override
    public StatisticsResource convert(Statistics statistics) {
        StatisticsResource statisticsResource = new StatisticsResource();

        if (statistics != null) {
            statisticsResource.setAvg(statistics.getAverage());
            statisticsResource.setCount(statistics.getCount());
            statisticsResource.setMax(statistics.getMax());
            statisticsResource.setMin(statistics.getMin());
            statisticsResource.setSum(statistics.getSum());
        }

        return statisticsResource;
    }
}
