package com.damonyuan.tcal.calendars;

import com.damonyuan.tcal.common.TcalException;
import com.damonyuan.tcal.time.Calendar;
import com.damonyuan.tcal.time.Date;
import com.damonyuan.tcal.time.Month;
import com.damonyuan.tcal.time.Weekday;

public class UnitedStates extends Calendar {

    /**
     * US calendars
     */
    public static enum Market {
        SETTLEMENT,     // generic settlement calendar
        NYSE,           // New York stock exchange calendar
        GOVERNMENTBOND, // government-bond calendar
        NERC            // off-peak days for NERC
    }


    //
    // public constructors
    //

    public UnitedStates() {
        this(Market.SETTLEMENT);
    }

    public UnitedStates(final Market market) {
        switch (market) {
            case SETTLEMENT:
                impl = new SettlementImpl();
                break;
            case NYSE:
                impl = new NyseImpl();
                break;
            case GOVERNMENTBOND:
                impl = new GovernmentBondImpl();
                break;
            case NERC:
                impl = new NercImpl();
                break;
            default:
                throw new TcalException(UNKNOWN_MARKET);
        }
    }


    //
    // private final inner classes
    //

    private final class SettlementImpl extends WesternImpl {

        @Override
        public String name() { return "US settlement"; }

        @Override
        public boolean isBusinessDay(final Date date) {
            final Weekday w = date.weekday();
            final int d = date.dayOfMonth(), dd = date.dayOfYear();
            final Month m = date.month();
            final int y = date.year();
            final int em = easterMonday(y);
            if (isWeekend(w)
                    // New Year's Day (possibly moved to Monday if on Sunday)
                    || ((d == 1 || (d == 2 && w == Weekday.Monday)) && m == Month.January)
                    // (or to Friday if on Saturday)
                    || (d == 31 && w == Weekday.Friday && m == Month.December)
                    // Martin Luther King's birthday (third Monday in January)
                    || ((d >= 15 && d <= 21) && w == Weekday.Monday && m == Month.January)
                    // Washington's birthday (third Monday in February)
                    || ((d >= 15 && d <= 21) && w == Weekday.Monday && m == Month.February)
                    // Memorial Day (last Monday in May)
                    || (d >= 25 && w == Weekday.Monday && m == Month.May)
                    // Independence Day (Monday if Sunday or Friday if Saturday)
                    || ((d == 4 || (d == 5 && w == Weekday.Monday) ||
                    (d == 3 && w == Weekday.Friday)) && m == Month.July)
                    // Labor Day (first Monday in September)
                    || (d <= 7 && w == Weekday.Monday && m == Month.September)
                    // Columbus Day (second Monday in October)
                    || ((d >= 8 && d <= 14) && w == Weekday.Monday && m == Month.October)
                    // Veteran's Day (Monday if Sunday or Friday if Saturday)
                    || ((d == 11 || (d == 12 && w == Weekday.Monday) ||
                    (d == 10 && w == Weekday.Friday)) && m == Month.November)
                    // Thanksgiving Day (fourth Thursday in November)
                    || ((d >= 22 && d <= 28) && w == Weekday.Thursday && m == Month.November)
                    // Christmas (Monday if Sunday or Friday if Saturday)
                    || ((d == 25 || (d == 26 && w == Weekday.Monday) ||
                    (d == 24 && w == Weekday.Friday)) && m == Month.December))
                return false;
            return true;
        }
    }

    private final class NyseImpl extends WesternImpl {

        @Override
        public String name() { return "New York stock exchange"; }

        @Override
        public boolean isBusinessDay(final Date date) {
            final Weekday w = date.weekday();
            final int d = date.dayOfMonth(), dd = date.dayOfYear();
            final Month m = date.month();
            final int y = date.year();
            final int em = easterMonday(y);
            if (isWeekend(w)
                    // New Year's Day (possibly moved to Monday if on Sunday)
                    || ((d == 1 || (d == 2 && w == Weekday.Monday)) && m == Month.January)
                    // Washington's birthday (third Monday in Month.FEBRUARY)
                    || ((d >= 15 && d <= 21) && w == Weekday.Monday && m == Month.February)
                    // Good Weekday.FRIDAY
                    || (dd == em-3)
                    // Memorial Day (last Weekday.MONDAY in Month.MAY)
                    || (d >= 25 && w == Weekday.Monday && m == Month.May)
                    // Independence Day (Weekday.MONDAY if Sunday or Weekday.FRIDAY if Saturday)
                    || ((d == 4 || (d == 5 && w == Weekday.Monday) ||
                    (d == 3 && w == Weekday.Friday)) && m == Month.July)
                    // Labor Day (first Weekday.MONDAY in Month.SEPTEMBER)
                    || (d <= 7 && w == Weekday.Monday && m == Month.September)
                    // Thanksgiving Day (fourth Weekday.THURSDAY in Month.NOVEMBER)
                    || ((d >= 22 && d <= 28) && w == Weekday.Thursday && m == Month.November)
                    // Christmas (Weekday.MONDAY if Sunday or Weekday.FRIDAY if Saturday)
                    || ((d == 25 || (d == 26 && w == Weekday.Monday) ||
                    (d == 24 && w == Weekday.Friday)) && m == Month.December)
            )
                return false;

            if (y >= 1998) {
                if (// Martin Luther King's birthday (third Weekday.MONDAY in JANUARY)
                        ((d >= 15 && d <= 21) && w == Weekday.Monday && m == Month.January)
                                // President Reagan's funeral
                                || (y == 2004 && m == Month.June && d == 11)
                                // Month.SEPTEMBER 11, 2001
                                || (y == 2001 && m == Month.September && (11 <= d && d <= 14))
                                // President Ford's funeral
                                || (y == 2007 && m == Month.January && d == 2)
                )
                    return false;
            } else if (y <= 1980) {
                if (// Presidential election days
                        ((y % 4 == 0) && m == Month.November && d <= 7 && w == Weekday.Tuesday)
                                // 1977 Blackout
                                || (y == 1977 && m == Month.July && d == 14)
                                // Funeral of former President Lyndon B. Johnson.
                                || (y == 1973 && m == Month.January && d == 25)
                                // Funeral of former President Harry S. Truman
                                || (y == 1972 && m == Month.December && d == 28)
                                // National Day of Participation for the lunar exploration.
                                || (y == 1969 && m == Month.July && d == 21)
                                // Funeral of former President Eisenhower.
                                || (y == 1969 && m == Month.March && d == 31)
                                // Closed all day - heavy snow.
                                || (y == 1969 && m == Month.February && d == 10)
                                // Day after Independence Day.
                                || (y == 1968 && m == Month.July && d == 5)
                                // Month.JUNE 12-Dec. 31, 1968
                                // Four day week (closed on Wednesdays) - Paperwork Crisis
                                || (y == 1968 && dd >= 163 && w == Weekday.Wednesday)
                )
                    return false;
            } else if (// Nixon's funeral
                    (y == 1994 && m == Month.April && d == 27)
            )
                return false;
            return true;
        }
    }

    private final class GovernmentBondImpl extends WesternImpl {

        @Override
        public String name() { return "US government bond market"; }

        @Override
        public boolean isBusinessDay(final Date date) {
            final Weekday w = date.weekday();
            final int d = date.dayOfMonth(), dd = date.dayOfYear();
            final Month m = date.month();
            final int y = date.year();
            final int em = easterMonday(y);
            if (isWeekend(w)
                    // New Year's Day (possibly moved to Weekday.MONDAY if on Sunday)
                    || ((d == 1 || (d == 2 && w == Weekday.Monday)) && m == Month.January)
                    // Martin Luther King's birthday (third Weekday.MONDAY in Month.JANUARY)
                    || ((d >= 15 && d <= 21) && w == Weekday.Monday && m == Month.January)
                    // Washington's birthday (third Weekday.MONDAY in Month.FEBRUARY)
                    || ((d >= 15 && d <= 21) && w == Weekday.Monday && m == Month.February)
                    // Good Weekday.FRIDAY
                    || (dd == em-3)
                    // Memorial Day (last Monday in Month.MAY)
                    || (d >= 25 && w == Weekday.Monday && m == Month.May)
                    // Independence Day (Monday if Sunday or Weekday.FRIDAY if Saturday)
                    || ((d == 4 || (d == 5 && w == Weekday.Monday) ||
                    (d == 3 && w == Weekday.Friday)) && m == Month.July)
                    // Labor Day (first Monday in Month.SEPTEMBER)
                    || (d <= 7 && w == Weekday.Monday && m == Month.September)
                    // Columbus Day (second Monday in October)
                    || ((d >= 8 && d <= 14) && w == Weekday.Monday && m == Month.October)
                    // Veteran's Day (Monday if Sunday or Weekday.FRIDAY if Saturday)
                    || ((d == 11 || (d == 12 && w == Weekday.Monday) ||
                    (d == 10 && w == Weekday.Friday)) && m == Month.November)
                    // Thanksgiving Day (fourth Weekday.THURSDAY in Month.NOVEMBER)
                    || ((d >= 22 && d <= 28) && w == Weekday.Thursday && m == Month.November)
                    // Christmas (Monday if Sunday or Weekday.FRIDAY if Saturday)
                    || ((d == 25 || (d == 26 && w == Weekday.Monday) ||
                    (d == 24 && w == Weekday.Friday)) && m == Month.December))
                return false;
            return true;
        }
    }

    private final class NercImpl extends WesternImpl {

        @Override
        public String name() { return "North American Energy Reliability Council";  }

        @Override
        public boolean isBusinessDay(final Date date) {
            final Weekday w = date.weekday();
            final int d = date.dayOfMonth();
            final Month m = date.month();
            if (isWeekend(w)
                    // New Year's Day (possibly moved to Monday if on Sunday)
                    || ((d == 1 || (d == 2 && w == Weekday.Monday)) && m == Month.January)
                    // Memorial Day (last Monday in Month.MAY)
                    || (d >= 25 && w == Weekday.Monday && m == Month.May)
                    // Independence Day (Monday if Sunday)
                    || ((d == 4 || (d == 5 && w == Weekday.Monday)) && m == Month.July)
                    // Labor Day (first Monday in Month.SEPTEMBER)
                    || (d <= 7 && w == Weekday.Monday && m == Month.September)
                    // Thanksgiving Day (fourth Weekday.THURSDAY in Month.NOVEMBER)
                    || ((d >= 22 && d <= 28) && w == Weekday.Thursday && m == Month.November)
                    // Christmas (Monday if Sunday)
                    || ((d == 25 || (d == 26 && w == Weekday.Monday)) && m == Month.December))
                return false;
            return true;
        }
    }
}
