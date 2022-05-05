package com.damonyuan.tcal.time;

import com.damonyuan.tcal.common.TcalException;

/**
 * Month names
 *
 * @author Srinivas Hasti
 */
public enum Month {
    January   (1),
    February  (2),
    March     (3),
    April     (4),
    May       (5),
    June      (6),
    July      (7),
    August    (8),
    September (9),
    October   (10),
    November  (11),
    December  (12);

    private final int enumValue;

    private Month(final int month) {
        this.enumValue = month;
    }

    /**
     * Returns the ordinal number of this Month
     *
     * @return the ordinal number of this Month
     */
    public int value() {
        return enumValue;
    }

    /**
     * Returns a new Month given it's ordinal number
     *
     * @param month is the ordinal number
     * @return a new Month given it's ordinal number
     */
    static public Month valueOf(final int month) {
        final Month returnMonth;
        switch (month) {
            case 1:
                returnMonth = Month.January;
                break;
            case 2:
                returnMonth = Month.February;
                break;
            case 3:
                returnMonth = Month.March;
                break;
            case 4:
                returnMonth = Month.April;
                break;
            case 5:
                returnMonth = Month.May;
                break;
            case 6:
                returnMonth = Month.June;
                break;
            case 7:
                returnMonth = Month.July;
                break;
            case 8:
                returnMonth = Month.August;
                break;
            case 9:
                returnMonth = Month.September;
                break;
            case 10:
                returnMonth = Month.October;
                break;
            case 11:
                returnMonth = Month.November;
                break;
            case 12:
                returnMonth =  Month.December;
                break;
            default:
                throw new TcalException("value must be [1,12]");
        }
        return returnMonth;
    }

    /**
     * Returns the IMM char for this Month
     *
     * @return the IMM char for this Month
     * @see IMM
     */
    public char getImmChar() {
        final char returnChar;
        switch (enumValue) {
            case 1:
                returnChar =  'F';
                break;
            case 2:
                returnChar =  'G';
                break;
            case 3:
                returnChar =  'H';
                break;
            case 4:
                returnChar =  'J';
                break;
            case 5:
                returnChar =  'K';
                break;
            case 6:
                returnChar =  'M';
                break;
            case 7:
                returnChar =  'N';
                break;
            case 8:
                returnChar =  'Q';
                break;
            case 9:
                returnChar =  'U';
                break;
            case 10:
                returnChar =  'V';
                break;
            case 11:
                returnChar =  'X';
                break;
            case 12:
                returnChar =  'Z';
                break;
            default:
                throw new TcalException("value must be [1,12]");
        }
        return returnChar;
    }

    /**
     * Returns a new month given it's IMM code
     *
     * @param immCode is the IMM code
     * @return a new month given it's IMM code
     */
    static public Month valueOf(final char immCode) {
        final Month returnMonth;
        switch (immCode) {
            case 'F': returnMonth = Month.January;break;
            case 'G': returnMonth = Month.February;break;
            case 'H': returnMonth = Month.March;break;
            case 'J': returnMonth = Month.April;break;
            case 'K': returnMonth = Month.May;break;
            case 'M': returnMonth = Month.June;break;
            case 'N': returnMonth = Month.July;break;
            case 'Q': returnMonth = Month.August;break;
            case 'U': returnMonth = Month.September;break;
            case 'V': returnMonth = Month.October;break;
            case 'X': returnMonth = Month.November;break;
            case 'Z': returnMonth = Month.December;break;
            default:
                throw new TcalException("value must be one of F,G,H,J,K,M,N,Q,U,V,X,Z");
        }
        return returnMonth;
    }

    @Override
    public String toString() {
        final String returnString;
        switch (enumValue) {
            case 1:
                returnString = "January";
                break;
            case 2:
                returnString = "February";
                break;
            case 3:
                returnString = "March";
                break;
            case 4:
                returnString = "April";
                break;
            case 5:
                returnString = "May";
                break;
            case 6:
                returnString = "June";
                break;
            case 7:
                returnString = "July";
                break;
            case 8:
                returnString = "August";
                break;
            case 9:
                returnString = "September";
                break;
            case 10:
                returnString = "October";
                break;
            case 11:
                returnString = "November";
                break;
            case 12:
                returnString = "December";
                break;
            default:
                throw new TcalException("value must be [1,12]");
        }
        return returnString;
    }
}
