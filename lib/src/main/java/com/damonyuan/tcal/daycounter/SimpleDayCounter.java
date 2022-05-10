package com.damonyuan.tcal.daycounter;

import com.damonyuan.tcal.time.Date;

public class SimpleDayCounter extends DayCounter {


    public SimpleDayCounter() {
        super.impl = new Impl();
    }


    //
    // private inner classes
    //

    final private class Impl extends DayCounter.Impl {

        private final DayCounter fallback = new Thirty360();

        //
        // implements DayCounter
        //

        @Override
        protected final String name() /* @ReadOnly */{
            return "Simple";
        }

        @Override
        protected long dayCount(final Date dateStart, final Date dateEnd) /* @ReadOnly */ {
            return fallback.dayCount(dateStart, dateEnd);
        }

        @Override
        protected /*@Time*/ final double yearFraction(
                final Date dateStart, final Date dateEnd,
                final Date refPeriodStart, final Date refPeriodEnd) /* @ReadOnly */{
            final int dm1 = dateStart.dayOfMonth();
            final int dm2 = dateEnd.dayOfMonth();
            final int mm1 = dateStart.month().value();
            final int mm2 = dateEnd.month().value();
            final int yy1 = dateStart.year();
            final int yy2 = dateEnd.year();

            if (dm1 == dm2 ||
                    // e.g., Aug 30 -> Feb 28 ?
                    (dm1 > dm2 && Date.isEndOfMonth(dateEnd)) ||
                    // e.g., Feb 28 -> Aug 30 ?
                    (dm1 < dm2 && Date.isEndOfMonth(dateStart)))
                return (yy2 - yy1) + (mm2 - mm1) / 12.0;
            else
                return fallback.yearFraction(dateStart, dateEnd, refPeriodStart, refPeriodEnd);
        }

    }

}