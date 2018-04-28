package com.ummetcivi.n26statistics.config;

import com.ummetcivi.n26statistics.service.StatisticsService;
import com.ummetcivi.n26statistics.service.TransactionService;
import com.ummetcivi.n26statistics.service.impl.InMemoryStatisticsService;
import com.ummetcivi.n26statistics.service.impl.InMemoryTransactionService;
import com.ummetcivi.n26statistics.storage.Storage;
import com.ummetcivi.n26statistics.storage.impl.InMemoryStorage;
import com.ummetcivi.n26statistics.util.DateProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("inMemory")
public class InMemoryStorageConfiguration {
    @Bean
    public TransactionService transactionService(DateProvider dateProvider) {
        return new InMemoryTransactionService(storage(), dateProvider);
    }

    @Bean
    public StatisticsService statisticService(DateProvider dateProvider) {
        return new InMemoryStatisticsService(storage(), dateProvider);
    }

    @Bean
    public Storage storage() {
        return new InMemoryStorage();
    }
}
