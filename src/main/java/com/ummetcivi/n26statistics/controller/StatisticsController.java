package com.ummetcivi.n26statistics.controller;

import com.ummetcivi.n26statistics.controller.resource.StatisticsResource;
import com.ummetcivi.n26statistics.domain.Statistics;
import com.ummetcivi.n26statistics.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.support.ConfigurableConversionService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StatisticsController {
    private final StatisticsService statisticsService;
    private final ConfigurableConversionService configurableConversionService;

    @Autowired
    public StatisticsController(StatisticsService statisticsService, ConfigurableConversionService configurableConversionService) {
        this.statisticsService = statisticsService;
        this.configurableConversionService = configurableConversionService;
    }

    @GetMapping("/statistics")
    public StatisticsResource getStatistic() {
        Statistics statistics = statisticsService.getStatistic();

        return configurableConversionService.convert(statistics, StatisticsResource.class);
    }
}