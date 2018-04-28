package com.ummetcivi.n26statistics.config;

import com.ummetcivi.n26statistics.util.DateProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.TimeZone;

@Configuration
public class DateTimeConfig {
    @Bean
    public TimeZone timeZone(){
        return TimeZone.getTimeZone("UTC");
    }

    @Bean
    public DateProvider dateProvider(){
        return new DateProvider(timeZone());
    }
}
