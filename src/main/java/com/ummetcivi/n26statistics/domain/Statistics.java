package com.ummetcivi.n26statistics.domain;

import java.util.DoubleSummaryStatistics;

public class Statistics extends DoubleSummaryStatistics {
    private long timestamp;

    public long getTimestamp() {
        return timestamp;
    }

    public void add(double amount, long timestamp) {
        super.accept(amount);
        this.timestamp = Long.max(timestamp, this.timestamp);
    }
}
