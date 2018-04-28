package com.ummetcivi.n26statistics.util;

import java.util.Calendar;
import java.util.TimeZone;

public class DateProvider {
    public static final int SIXTY_SECONDS_IN_MILLIS = 60 * 1000;
    private final TimeZone timeZone;

    public DateProvider(TimeZone timeZone) {
        this.timeZone = timeZone;
    }

    public long getCurrentMillis() {
        return Calendar.getInstance(timeZone).getTimeInMillis();
    }
}
