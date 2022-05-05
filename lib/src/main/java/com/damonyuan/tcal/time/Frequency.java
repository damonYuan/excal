package com.damonyuan.tcal.time;

import com.damonyuan.tcal.common.TcalException;

/**
 * Frequency of events
 *
 */
public enum Frequency {
    /**
     * null frequency
     */
    NoFrequency(-1),
    /**
     * only once
     */
    Once(0),
    /**
     * once a year
     */
    Annual(1),
    /**
     * twice a year
     */
    Semiannual(2),
    /**
     * every fourth month
     */
    EveryFourthMonth(3),
    /**
     * every third month
     */
    Quarterly(4),
    /**
     * every second month
     */
    Bimonthly(6),
    /**
     * once a month
     */
    Monthly(12),
    /**
     * every fourth week
     */
    EveryFourthWeek(13),
    /**
     * every second week
     */
    Biweekly(26),
    /**
     * once a week
     */
    Weekly(52),
    /**
     * once a day
     */
    Daily(365),
    /**
     * some other unknown frequency
     */
    OtherFrequency(999);

    private final int enumValue;

    Frequency(final int frequency) {
        this.enumValue = frequency;
    }

    static public Frequency valueOf(final int value) {
        switch (value) {
            case -1:
                return Frequency.NoFrequency;
            case 0:
                return Frequency.Once;
            case 1:
                return Frequency.Annual;
            case 2:
                return Frequency.Semiannual;
            case 3:
                return Frequency.EveryFourthMonth;
            case 4:
                return Frequency.Quarterly;
            case 6:
                return Frequency.Bimonthly;
            case 12:
                return Frequency.Monthly;
            case 13:
                return Frequency.EveryFourthWeek;
            case 26:
                return Frequency.Biweekly;
            case 52:
                return Frequency.Weekly;
            case 365:
                return Frequency.Daily;
            case 999:
                return Frequency.OtherFrequency;
            default:
                throw new TcalException("value must be one of -1,0,1,2,3,4,6,12,13,26,52,365,999");
        }
    }

    public int toInteger() {
        return this.enumValue;
    }

}
