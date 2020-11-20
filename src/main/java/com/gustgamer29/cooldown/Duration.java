package com.gustgamer29.cooldown;

import java.util.concurrent.TimeUnit;

public class Duration {

    private final long duration;
    private final TimeUnit unit;

    public Duration(long duration, TimeUnit unit) {
        this.duration = duration;
        this.unit = unit;
    }

    public static Duration createWithSuitableUnit(long sourceDuration, TimeUnit sourceUnit) {
        long durationMillis = Math.abs(TimeUnit.MILLISECONDS.convert(sourceDuration, sourceUnit));

        TimeUnit targetUnit;
        if (durationMillis > 1000L * 60L * 60L * 24L) {
            targetUnit = TimeUnit.DAYS;
        } else if (durationMillis > 1000L * 60L * 60L) {
            targetUnit = TimeUnit.HOURS;
        } else if (durationMillis > 1000L * 60L) {
            targetUnit = TimeUnit.MINUTES;
        } else {
            targetUnit = TimeUnit.SECONDS;
        }

        long durationInTargetUnit = targetUnit.convert(sourceDuration, sourceUnit);
        return new Duration(durationInTargetUnit, targetUnit);
    }

    public long getDuration() {
        return duration;
    }

    public TimeUnit getTimeUnit() {
        return unit;
    }
}
