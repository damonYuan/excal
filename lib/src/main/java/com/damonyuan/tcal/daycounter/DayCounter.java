package com.damonyuan.tcal.daycounter;

import com.damonyuan.tcal.common.TcalException;
import com.damonyuan.tcal.time.Date;

public class DayCounter {

    private static final String NO_IMPLEMENTATION_PROVIDED = "no implementation provided";

    //
    // protected fields
    //

    protected Impl impl;


    //
    // public constructors
    //

    public DayCounter() {
        // nothing
    }


    //
    // public methods
    //

    /**
     * Returns whether or not the day counter is initialized
     */
    public boolean empty() /* @ReadOnly */ {
        return impl == null;
    }

    /**
     * @return the name of this DayCounter
     */
    public String name() /* @ReadOnly */ {
        if (impl == null) {
            throw new TcalException(NO_IMPLEMENTATION_PROVIDED);
        }
        return impl.name();
    }


    /**
     * Returns the number of days between two dates
     *
     * @param dateStart is the starting Date
     * @param dateEnd is the ending Date
     * @return the number of days between two dates.
     */
    public long dayCount(final Date dateStart, final Date dateEnd) /* @ReadOnly */ {
        if (impl == null) {
            throw new TcalException(NO_IMPLEMENTATION_PROVIDED);
        }
        return impl.dayCount(dateStart, dateEnd);
    }

    /**
     * Returns the period between two dates as a fraction of year
     *
     * @param dateStart
     * @param dateEnd
     * @return the period between two dates as a fraction of year
     */
    public /*@Time*/ double yearFraction(final Date dateStart, final Date dateEnd) /* @ReadOnly */ {
        return yearFraction(dateStart, dateEnd, null, null);
    }


    /**
     * Returns the period between two dates as a fraction of year, considering referencing dates for both.
     *
     * @param dateStart
     * @param dateEnd
     * @param refPeriodStart
     * @param refPeriodEnd
     * @return the period between two dates as a fraction of year, considering referencing dates for both.
     */
    public /*@Time*/ double yearFraction(final Date dateStart, final Date dateEnd, final Date refPeriodStart, final Date refPeriodEnd) /* @ReadOnly */ {
        if (impl == null) {
            throw new TcalException(NO_IMPLEMENTATION_PROVIDED);
        }
        return impl.yearFraction(dateStart, dateEnd, refPeriodStart, refPeriodEnd);
    }

    /**
     * Returns <tt>true</tt> if <code>this</code> and <code>other</code> belong to the same derived class.
     */
    public boolean eq(final DayCounter another) {
        return equals(another);
    }

    /**
     * @return the negation of {@link DayCounter#eq(DayCounter)}
     */
    public boolean ne(final DayCounter another) {
        return !equals(another);
    }


    //
    // overrides Object
    //

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((impl == null) ? 0 : impl.hashCode());
        result = prime * result + ((impl == null) ? 0 : name().hashCode());
        return result;
    }

//    @Override
//    public boolean equals(final Object obj) {
//        if (this == obj)
//            return true;
//        if (obj == null || !(obj instanceof DayCounter))
//            return false;
//
//        final DayCounter other = (DayCounter) obj;
//        if (this.empty() && other.empty())
//            return true;
//        if (this.name().equals(other.name()))
//            return true;
//        return false;
//    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;

        return obj instanceof DayCounter &&
                ((DayCounter) obj).fEquals(this);

    }

    @Override
    public String toString() {
        return (impl == null) ? "null" : impl.name();
    }


    //
    // protected methods
    //

    protected boolean fEquals(DayCounter other) {
        if (this.empty() && other.empty())
            return true;
        if (this.name().equals(other.name()))
            return true;
        return false;
    }

    //
    // protected inner classes
    //

    /**
     * Base class for day counter implementations.
     */
    protected abstract class Impl {

        //
        // protected abstract methods
        //

        protected abstract String name() /* @ReadOnly */;
        protected abstract /*@Time*/ double yearFraction(final Date dateStart, final Date dateEnd, final Date refPeriodStart, final Date refPeriodEnd) /* @ReadOnly */;


        //
        // protected methods
        //

        /**
         * To be overloaded by more complex day counters
         *
         * @param dateStart is the starting Date
         * @param dateEnd is the ending Date
         * @return the period between two dates as a fraction of year
         */
        protected long dayCount(final Date dateStart, final Date dateEnd) /* @ReadOnly */ {
            return dateEnd.sub(dateStart);
        }

    }

}
