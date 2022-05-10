package com.damonyuan.tcal.calendar;

import com.damonyuan.tcal.common.TcalException;
import com.damonyuan.tcal.time.Calendar;
import com.damonyuan.tcal.time.Date;
import com.damonyuan.tcal.time.Month;
import com.damonyuan.tcal.time.Weekday;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Optional;

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

            Gson gson = new GsonBuilder().create();
            try {
                JsonReader reader = new JsonReader(Files.newBufferedReader(Paths.get("hk.json")));
                Holidays holidays = gson.fromJson(reader, Holidays.class);
                HashMap<Integer, Integer[]> year = holidays.holidays.get(y);
                if (year != null) {
                    Integer[] days = year.get(m.value());
                    if (days != null) {
                        if (Arrays.asList(days).contains(d)) {
                            return false;
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            return true;
        }
    }
}
