package com.damonyuan.tcal.daycounter;

import com.damonyuan.tcal.common.TcalException;
import com.damonyuan.tcal.time.Date;

public class Thirty360 extends DayCounter {

    /**
     * 30/360 Calendar Conventions
     */
    public enum Convention {
        USA, BondBasis,
        European, EurobondBasis,
        Italian;
    }


    //
    // public constructors
    //

    public Thirty360() {
        this(Convention.BondBasis);
    }

    public Thirty360(final Thirty360.Convention c) {
        switch (c) {
            case USA:
            case BondBasis:
                super.impl = new Impl_US();
                break;
            case European:
            case EurobondBasis:
                super.impl = new Impl_EU();
                break;
            case Italian:
                super.impl = new Impl_IT();
                break;
            default:
                throw new TcalException("unknown 30/360 convention"); // TODO: message
        }
    }


    //
    // private inner classes
    //

    /**
     * Implementation of Thirty360 class abstraction according to US convention
     *
     * @see <a href="http://en.wikipedia.org/wiki/Bridge_pattern">Bridge pattern</a>
     *
     * @author Richard Gomes
     */
    private final class Impl_US extends DayCounter.Impl {

        @Override
        public final String name() /* @ReadOnly */{
            return "30/360 (Bond Basis)";
        }

        @Override
        protected long dayCount(final Date d1, final Date d2) /* @ReadOnly */ {
            final int dd1 = d1.dayOfMonth();
            int dd2 = d2.dayOfMonth();
            final int mm1 = d1.month().value();
            int mm2 = d2.month().value();
            final int yy1 = d1.year();
            final int yy2 = d2.year();

            if (dd2 == 31 && dd1 < 30) { dd2 = 1; mm2++; }

            return 360*(yy2-yy1) + 30*(mm2-mm1-1) + Math.max(0, 30-dd1) + Math.min(30, dd2);
        }

        @Override
        public /*@Time*/ final double yearFraction(
                final Date dateStart, final Date dateEnd,
                final Date refPeriodStart, final Date refPeriodEnd) /* @ReadOnly */{
            return /*@Time*/ dayCount(dateStart, dateEnd) / 360.0;
        }

    }

    /**
     * Implementation of Thirty360 class abstraction according to European convention
     *
     * @see <a href="http://en.wikipedia.org/wiki/Bridge_pattern">Bridge pattern</a>
     *
     * @author Richard Gomes
     */
    private final class Impl_EU extends DayCounter.Impl {

        @Override
        public final String name() /* @ReadOnly */{
            return "30E/360 (Eurobond Basis)";
        }

        @Override
        public /*@Time*/ final double yearFraction(
                final Date dateStart, final Date dateEnd,
                final Date refPeriodStart, final Date refPeriodEnd) /* @ReadOnly */{
            return /*@Time*/ dayCount(dateStart, dateEnd) / 360.0;
        }

        @Override
        protected long dayCount(final Date d1, final Date d2) /* @ReadOnly */ {
            final int dd1 = d1.dayOfMonth();
            final int dd2 = d2.dayOfMonth();
            final int mm1 = d1.month().value();
            final int mm2 = d2.month().value();
            final int yy1 = d1.year();
            final int yy2 = d2.year();

            return 360*(yy2-yy1) + 30*(mm2-mm1-1) + Math.max(0, 30-dd1) + Math.min(30, dd2);
        }

    }

    /**
     * Implementation of Thirty360 class abstraction according to Italian convention
     *
     * @see <a href="http://en.wikipedia.org/wiki/Bridge_pattern">Bridge pattern</a>
     *
     * @author Richard Gomes
     */
    private final class Impl_IT extends DayCounter.Impl {

        @Override
        protected final String name() /* @ReadOnly */{
            return "30/360 (Italian)";
        }

        @Override
        public /*@Time*/ final double yearFraction(
                final Date dateStart, final Date dateEnd,
                final Date refPeriodStart, final Date refPeriodEnd) /* @ReadOnly */{
            return /*@Time*/ dayCount(dateStart, dateEnd) / 360.0;
        }

        @Override
        protected long dayCount(final Date d1, final Date d2) /* @ReadOnly */ {
            int dd1 = d1.dayOfMonth();
            int dd2 = d2.dayOfMonth();
            final int mm1 = d1.month().value();
            final int mm2 = d2.month().value();
            final int yy1 = d1.year();
            final int yy2 = d2.year();

            if (mm1 == 2 && dd1 > 27) {
                dd1 = 30;
            }
            if (mm2 == 2 && dd2 > 27) {
                dd2 = 30;
            }

            return 360*(yy2-yy1) + 30*(mm2-mm1-1) + Math.max(0, 30-dd1) + Math.min(30, dd2);
        }

    }

}
