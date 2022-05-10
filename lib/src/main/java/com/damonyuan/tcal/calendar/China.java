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

            if (isWeekend(w)) {
                return false;
            }

            Gson gson = new GsonBuilder().create();
            try {
                JsonReader reader = new JsonReader(Files.newBufferedReader(Paths.get("china.json")));
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
