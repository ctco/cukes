package lv.ctco.cukescore.internal.helpers;

public class Time {

    private final int value;
    private final TimeUnitDictionary unit;

    public Time(int value, TimeUnitDictionary unit) {
        this.value = value;
        this.unit = unit;
    }

    public static Time of(int value, String unit) {
        return new Time(value, TimeUnitDictionary.of(unit));
    }

    public int getValue() {
        return value;
    }

    public TimeUnitDictionary getUnitDict() {
        return unit;
    }
}
