package com.damonyuan.tcal.time;

public class NullCalendar extends Calendar {

    //
    // public constructors
    //

    public NullCalendar() {
        impl = new Impl();
    }


    //
    // private final inner classes
    //

    private final class Impl extends Calendar.Impl {
        @Override
        public String name() /* @ReadOnly */{
            return "Null";
        }

        @Override
        public boolean isWeekend(final Weekday weekday) /* @ReadOnly */{
            return false;
        }

        @Override
        public boolean isBusinessDay(final Date date) /* @ReadOnly */{
            return true;
        }
    }

}
