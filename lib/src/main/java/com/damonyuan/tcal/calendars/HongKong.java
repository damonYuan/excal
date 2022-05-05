package com.damonyuan.tcal.calendars;

import com.damonyuan.tcal.common.TcalException;
import com.damonyuan.tcal.time.Calendar;
import com.damonyuan.tcal.time.Date;
import com.damonyuan.tcal.time.Month;
import com.damonyuan.tcal.time.Weekday;

import static com.damonyuan.tcal.time.Month.*;
import static com.damonyuan.tcal.time.Weekday.Monday;

public class HongKong extends Calendar {

    public static enum Market {
        /**
         * Hong Kong stock exchange
         */
        HKEx
    }

    //
    // public constructor
    //

    public HongKong() {
        this(Market.HKEx);
    }

    public HongKong(final Market m) {
        switch (m) {
            case HKEx:
                impl = new HkexImpl();
                break;

            default:
                throw new TcalException(UNKNOWN_MARKET);
        }
    }


    //
    // private final inner classes
    //

    private final class HkexImpl extends Calendar.WesternImpl {
        @Override
        public String name() {
            return "Hong Kong stock exchange";
        }

        @Override
        public boolean isBusinessDay(final Date date) {
            final Weekday w = date.weekday();
            final int d = date.dayOfMonth(), dd = date.dayOfYear();
            final Month m = date.month();
            final int y = date.year();
            final int em = easterMonday(y);

            if (isWeekend(w)
                    // New Year's Day
                    || ((d == 1 || ((d == 2 || d == 3) && w == Monday)) && m == January)
                    // Ching Ming Festival
                    || (d == 5 && m == April)
                    // Good Friday
                    || (dd == em - 3)
                    // Easter MONDAY
                    || (dd == em)
                    // Labor Day
                    || (d == 1 && m == May)
                    // SAR Establishment Day
                    || ((d == 1 || ((d == 2 || d == 3) && w == Monday)) && m == July)
                    // National Day
                    || ((d == 1 || ((d == 2 || d == 3) && w == Monday)) && m == October)
                    // Christmas Day
                    || (d == 25 && m == December)
                    // Boxing Day
                    || ((d == 26 || ((d == 27 || d == 28) && w == Monday)) && m == December)) {
                return false;
            }

            if (y == 2004) {
                if (// Lunar New Year
                        ((d == 22 || d == 23 || d == 24) && m == January)
                                // Buddha's birthday
                                || (d == 26 && m == May)
                                // Tuen NG festival
                                || (d == 22 && m == June)
                                // Mid-autumn festival
                                || (d == 29 && m == September)
                                // Chung Yeung
                                || (d == 29 && m == September)) {
                    return false;
                }
            }

            if (y == 2005) {
                if (// Lunar New Year
                        ((d == 9 || d == 10 || d == 11) && m == February)
                                // Buddha's birthday
                                || (d == 16 && m == May)
                                // Tuen NG festival
                                || (d == 11 && m == June)
                                // Mid-autumn festival
                                || (d == 19 && m == September)
                                // Chung Yeung festival
                                || (d == 11 && m == October)) {
                    return false;
                }
            }

            if (y == 2006) {
                if (// Lunar New Year
                        ((d >= 28 && d <= 31) && m == January)
                                // Buddha's birthday
                                || (d == 5 && m == May)
                                // Tuen NG festival
                                || (d == 31 && m == May)
                                // Mid-autumn festival
                                || (d == 7 && m == October)
                                // Chung Yeung festival
                                || (d == 30 && m == October)) {
                    return false;
                }
            }

            if (y == 2007) {
                if (// Lunar New Year
                        ((d >= 17 && d <= 20) && m == February)
                                // Buddha's birthday
                                || (d == 24 && m == May)
                                // Tuen NG festival
                                || (d == 19 && m == June)
                                // Mid-autumn festival
                                || (d == 26 && m == September)
                                // Chung Yeung festival
                                || (d == 19 && m == October)) {
                    return false;
                }
            }

            if (y == 2008) {
                if (// Lunar New Year
                        ((d >= 7 && d <= 9) && m == February)
                                // Ching Ming Festival
                                || (d == 4 && m == April)
                                // Buddha's birthday
                                || (d == 12 && m == May)
                                // Tuen NG festival
                                || (d == 9 && m == June)
                                // Mid-autumn festival
                                || (d == 15 && m == September)
                                // Chung Yeung festival
                                || (d == 7 && m == October)) {
                    return false;
                }
            }

            return true;
        }
    }
}
