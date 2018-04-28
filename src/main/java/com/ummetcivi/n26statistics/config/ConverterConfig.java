package com.ummetcivi.n26statistics.config;

import com.ummetcivi.n26statistics.converter.StatisticsToStatisticsResourceConverter;
import com.ummetcivi.n26statistics.converter.TransactionDtoToTransactionConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.support.ConfigurableConversionService;

@Configuration
public class ConverterConfig {
    @Bean
    public ConfigurableConversionService configurableConversionService(ConfigurableConversionService conversionService) {
        conversionService.addConverter(new TransactionDtoToTransactionConverter());
        conversionService.addConverter(new StatisticsToStatisticsResourceConverter());
        return conversionService;
    }
}
