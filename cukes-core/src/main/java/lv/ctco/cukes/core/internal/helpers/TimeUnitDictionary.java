package lv.ctco.cukes.core.internal.helpers;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public enum TimeUnitDictionary {
    MILLISECONDS(TimeUnit.MILLISECONDS, "ms", "milli", "millisecond", "milliseconds"),
    SECONDS(TimeUnit.SECONDS, "s", "sec", "second", "seconds"),
    MINUTES(TimeUnit.MINUTES, "m", "min", "minute", "minutes"),
    HOURS(TimeUnit.HOURS, "h", "hour", "hours");


    private final List<String> keys;
    private final TimeUnit timeUnit;

    TimeUnitDictionary(TimeUnit timeUnit, String... keys) {
        this.timeUnit = timeUnit;
        this.keys = Arrays.asList(keys);
    }

    public static TimeUnitDictionary of(String key) {
        for (TimeUnitDictionary timeUnit : values()) {
            if (timeUnit.keys.contains(key.toLowerCase())) return timeUnit;
        }
        throw new IllegalArgumentException("No TimeUnit found for " + key);
    }

    public TimeUnit getTimeUnit() {
        return timeUnit;
    }
}
