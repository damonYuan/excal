package com.damonyuan.tcal.daycounter;

import com.damonyuan.tcal.time.Date;

public class Actual365Fixed extends DayCounter {


    public Actual365Fixed() {
        super.impl = new Impl();
    }


    //
    // private inner classes
    //

    final private class Impl extends DayCounter.Impl {

        //
        // implements DayCounter
        //

        @Override
        public final String name() /* @ReadOnly */{
            return "Actual/365 (fixed)";
        }

        @Override
        public /*@Time*/ final double yearFraction(
                final Date dateStart, final Date dateEnd,
                final Date refPeriodStart, final Date refPeriodEnd) /* @ReadOnly */{
            return /*@Time*/ dayCount(dateStart, dateEnd)/365.0;
        }

    }

}
