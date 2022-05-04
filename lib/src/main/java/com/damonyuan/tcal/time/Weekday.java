package com.damonyuan.tcal.time;

import com.damonyuan.tcal.common.ExcalException;

/**
 * Day's serial number MOD 7
 * <p>
 * WEEKDAY Excel function is the same except for Sunday = 7.
 *
 * @author Richard Gomes
 */
public enum Weekday {
    Sunday(1), Monday(2), Tuesday(3), Wednesday(4), Thursday(5), Friday(6), Saturday(7);

    private final int enumValue;

    Weekday(final int weekday) {
        this.enumValue = weekday;
    }

    /**
     * Returns a new Weekday object given its cardinality
     *
     * @param value is the cardinality from 1 (Sunday) till 7 (Saturday)
     * @return a new Weekday object given its cardinality
     */
    static public Weekday valueOf(final int value) {
        switch (value) {
            case 1:
                return Weekday.Sunday;
            case 2:
                return Weekday.Monday;
            case 3:
                return Weekday.Tuesday;
            case 4:
                return Weekday.Wednesday;
            case 5:
                return Weekday.Thursday;
            case 6:
                return Weekday.Friday;
            case 7:
                return Weekday.Saturday;
            default:
                throw new ExcalException("value must be [1,7]"); // TODO: message
        }
    }

    /**
     * Returns the week day as a number where Sunday (1) till Saturday (7)
     *
     * @return the week day as a number where Sunday (1) till Saturday (7)
     */
    public int value() {
        return enumValue;
    }

    /**
     * Returns the name of weekdays in long format
     *
     * @return the name of weekdays in long format
     * @see Weekday#getLongFormat
     */
    @Override
    public String toString() {
        switch (enumValue) {
            case 1:
                return "Sunday";
            case 2:
                return "Monday";
            case 3:
                return "Tuesday";
            case 4:
                return "Wednesday";
            case 5:
                return "Thursday";
            case 6:
                return "Friday";
            case 7:
                return "Saturday";
        }
        throw new ExcalException("value must be [1,7]"); // TODO: message
    }

    /**
     * Returns the name of weekdays in long format
     *
     * @return the name of weekdays in long format
     * @see Weekday#toString
     */
    public String getLongFormat() {
        return this.toString();
    }

    /**
     * Returns the name of weekdays in short format (3 letters)
     *
     * @return the name of weekdays in short format (3 letters)
     */
    public String getShortFormat() {
        return getAsShortFormat();
    }

    /**
     * Returns the name of weekdays in shortest format. (2 letters)
     *
     * @return the name of weekdays in shortest format (2 letters)
     */
    public String getShortestFormat() {
        return getAsShortestFormat();
    }

    /**
     * Returns the name of weekdays in short format (3 letters)
     */
    private String getAsShortFormat() {
        final StringBuilder sb = new StringBuilder();
        sb.append(this);
        sb.setLength(3);
        return sb.toString();
    }

    /**
     * Returns the name of weekdays in shortest format (2 letters)
     */
    private String getAsShortestFormat() {
        final StringBuilder sb = new StringBuilder();
        sb.append(this);
        sb.setLength(2);
        return sb.toString();

    }
}
