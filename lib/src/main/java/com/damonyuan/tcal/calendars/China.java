package com.damonyuan.tcal.calendars;

import com.damonyuan.tcal.common.TcalException;
import com.damonyuan.tcal.time.Calendar;
import com.damonyuan.tcal.time.Date;
import com.damonyuan.tcal.time.Month;
import com.damonyuan.tcal.time.Weekday;

import static com.damonyuan.tcal.time.Calendar.UNKNOWN_MARKET;
import static com.damonyuan.tcal.time.Month.*;
import static com.damonyuan.tcal.time.Weekday.Saturday;
import static com.damonyuan.tcal.time.Weekday.Sunday;

public class China extends Calendar {

    public static enum Market {
        /**
         * Shanghai stock exchange
         */
        SSE
    }

    //
    // public constructors
    //

    public China() {
        this(Market.SSE);
    }

    public China(final Market m) {
        switch (m) {
            case SSE:
                impl = new SseImpl();
                break;
            default:
                throw new TcalException(UNKNOWN_MARKET);
        }
    }

    //
    // private final inner classes
    //

    private final class SseImpl extends Impl {

        @Override
        public String name() {
            return "Shanghai stock exchange";
        }

        @Override
        public boolean isWeekend(final Weekday w) {
            return w == Saturday || w == Sunday;
        }

        @Override
        public boolean isBusinessDay(final Date date) {
            final Weekday w = date.weekday();
            final int d = date.dayOfMonth();
            final Month m = date.month();
            final int y = date.year();

            if (isWeekend(w)
                    // New Year's Day
                    || (d == 1 && m == January)
                    || (d == 3 && m == January && y == 2005)
                    || ((d == 2 || d == 3) && m == January && y == 2006)
                    || (d <= 3 && m == January && y == 2007)
                    || (d == 31 && m == December && y == 2007)
                    || (d == 1 && m == January && y == 2008)
                    || (d == 1 && m == January && y == 200)
                    || (d == 2 && m == January && y == 2009)
                    // Chinese New Year
                    || (d >= 19 && d <= 28 && m == January && y == 2004) || (d >= 7 && d <= 15 && m == February && y == 2005)
                    || (((d >= 26 && m == January) || (d <= 3 && m == February)) && y == 2006)
                    || (d >= 17 && d <= 25 && m == February && y == 2007)
                    || (d >= 6 && d <= 12 && m == February && y == 2008)
                    || (d >= 26 && d <= 30 && m == January && y == 2009)
                    // Ching Ming Festival
                    || (d == 4 && m == April && y == 2008)
                    || (d == 6 && m == April && y == 2009)
                    // Labor Day
                    || (d >= 1 && d <= 7 && m == May && y <= 2007) || (d >= 1 && d <= 2 && m == May && y == 2008)
                    || (d == 1 && m == May && y == 2009)
                    // Tuen Ng Festival
                    || (d == 9 && m == June && y == 2008) || (d >= 28 && d <= 29 && m == May && y == 2009)
                    // Mid-Autumn Festival
                    || (d == 15 && m == September && y == 2008)
                    // National Day
                    || (d >= 1 && d <= 7 && m == October && y <= 2007) || (d >= 29 && m == September && y == 2008)
                    || (d <= 3 && m == October && y == 2008) || (d >= 1 && d <= 8 && m == October && y == 2009)) {
                return false;
            }
            return true;

        }
    }
}
