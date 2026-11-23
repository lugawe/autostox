package org.autostox.core.domain.marketdata;

public enum Interval {
    MINUTES_1,
    MINUTES_5,
    HOURS_1,
    DAYS_1,
    WEEKS_1;

    public boolean isIntraday() {
        return switch (this) {
            case MINUTES_1, MINUTES_5, HOURS_1 -> true;
            case DAYS_1, WEEKS_1 -> false;
        };
    }
}
