package com.damonyuan.tcal.time;

import com.damonyuan.tcal.common.Settings;
import com.damonyuan.tcal.common.TcalException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Schedule {

    //
    // private final fields
    //

    private final boolean fullInterface_;
    private final Calendar calendar_;
    private final BusinessDayConvention convention_;
    private final BusinessDayConvention terminationDateConvention_;
    private final boolean endOfMonth_;
    private final boolean finalIsRegular_;
    private final List< Date > dates_;
    private final List<Boolean> isRegular_;


    //
    // private fields
    //

    private Period tenor_;
    private DateGeneration.Rule rule_;
    private Date firstDate_;
    private Date nextToLastDate_;



    //
    // public methods
    //

    public Schedule(final List<Date> dates) {
        this(dates, new NullCalendar(), BusinessDayConvention.Unadjusted);
    }

    public Schedule(final List<Date> dates, final Calendar calendar) {
        this(dates, calendar, BusinessDayConvention.Unadjusted);
    }

    public Schedule(final List<Date> dates, final Calendar calendar, final BusinessDayConvention convention) {
        this.dates_ = dates;
        this.isRegular_ = new ArrayList<Boolean>(); // TODO: use a data structure backed by primitive types instead

        this.calendar_ = calendar;
        this.convention_ = convention;

        //Default values
        this.fullInterface_ = false;
        this.tenor_ = new Period();
        this.terminationDateConvention_ = convention;
        this.rule_ = DateGeneration.Rule.Forward;
        this.endOfMonth_ = false;
        this.finalIsRegular_ = true;
    }

    public Schedule(final Date  effectiveDate,
                    final Date  terminationDate,
                    final Period  tenor,
                    final Calendar  calendar,
                    final BusinessDayConvention convention,
                    final BusinessDayConvention terminationDateConvention,
                    final DateGeneration.Rule rule,
                    final boolean endOfMonth) {
        this(effectiveDate, terminationDate, tenor, calendar, convention, terminationDateConvention, rule, endOfMonth, new Date(), new Date());
    }

    public Schedule(final Date  effectiveDate,
                    final Date  terminationDate,
                    final Period  tenor,
                    final Calendar  calendar,
                    BusinessDayConvention convention,
                    final BusinessDayConvention terminationDateConvention,
                    final DateGeneration.Rule rule,
                    final boolean endOfMonth,
                    final Date firstDate,
                    final Date nextToLastDate) {

        this.dates_ = new ArrayList<Date>(); // TODO: use a data structure backed by primitive types instead
        this.isRegular_ = new ArrayList<Boolean>(); // TODO: use a data structure backed by primitive types instead

        this.fullInterface_ = true;
        this.tenor_ = tenor;
        this.calendar_ = calendar;
        this.convention_ = convention;
        this.terminationDateConvention_ = terminationDateConvention;
        this.rule_ = rule;
        this.endOfMonth_ = endOfMonth;
        this.firstDate_ = firstDate;
        this.nextToLastDate_ = nextToLastDate;
        this.finalIsRegular_ = true;

        // sanity checks
        if (effectiveDate == null || effectiveDate.isNull()) {
            throw new TcalException("null effective date");
        }
        if (terminationDate == null || terminationDate.isNull()) {
            throw new TcalException("null termination date");
        }
        if (effectiveDate.ge(terminationDate)) {
            throw new TcalException("effective date (" + effectiveDate
                    + ") later than or equal to termination date ("
                    + terminationDate + ")");
        }

        if (tenor.length()==0) {
            rule_ = DateGeneration.Rule.Zero;
        } else {
            if (tenor.length() <= 0) {
                throw new TcalException("non positive tenor (" + tenor + ") not allowed");
            }
        }

        if ( firstDate != null && !firstDate.isNull() ) {
            switch (rule_) {
                case Backward:
                case Forward:
                    if (firstDate.le(effectiveDate) || firstDate.ge(terminationDate)) {
                        throw new TcalException("first date (" + firstDate +
                                ") out of [effective (" + effectiveDate +
                                "), termination (" + terminationDate +
                                ")] date range");
                    }
                    break;
                case ThirdWednesday:
                    if (!IMM.isIMMdate(firstDate, false)) {
                        throw new TcalException("first date (" + firstDate +
                                ") is not an IMM date");
                    }
                    break;
                case Zero:
                case Twentieth:
                case TwentiethIMM:
                    String errMsg = "first date incompatible with " + rule_ +
                            " date generation rule";
                    throw new TcalException(errMsg);
                default:
                    errMsg = "unknown Rule (" + rule_ + ")";
                    throw new TcalException(errMsg);
            }
        }
        if ( nextToLastDate != null && !nextToLastDate.isNull() ) {
            switch (rule_) {
                case Backward:
                case Forward:
                    if (nextToLastDate.le(effectiveDate) || nextToLastDate.ge(terminationDate)) {
                        throw new TcalException("next to last date (" + nextToLastDate +
                                ") out of [effective (" + effectiveDate +
                                "), termination (" + terminationDate +
                                ")] date range");
                    }
                    break;
                case ThirdWednesday:
                    if (!IMM.isIMMdate(nextToLastDate, false)) {
                        throw new TcalException("first date (" + firstDate +
                                ") is not an IMM date");
                    }
                case Zero:
                case Twentieth:
                case TwentiethIMM:
                    String errMsg = "next to last date incompatible with " + rule_ +
                            " date generation rule";
                    throw new TcalException(errMsg);
                default:
                    errMsg = "unknown Rule (" + rule_ + ")";
                    throw new TcalException(errMsg);
            }
        }


        // calendar needed for endOfMonth adjustment
        final Calendar nullCalendar = new NullCalendar();
        int periods = 1;
        Date seed, exitDate;
        switch (rule_) {

            case Zero:
                tenor_ = new Period(0, TimeUnit.Days);
                dates_.add(effectiveDate);
                dates_.add(terminationDate);
                isRegular_.add(Boolean.TRUE);
                break;

            case Backward:

                dates_.add(terminationDate);

                seed = terminationDate.clone();
                if ( nextToLastDate != null && !nextToLastDate.isNull() ) {
                    dates_.add(0, nextToLastDate);
                    final Date temp = nullCalendar.advance(seed, tenor_.mul(periods).negative(), convention, endOfMonth);
                    if (temp.ne(nextToLastDate)) {
                        isRegular_.add(0, Boolean.FALSE);
                    } else {
                        isRegular_.add(0, Boolean.TRUE);
                    }
                    seed = nextToLastDate.clone();
                }

                exitDate = effectiveDate.clone();
                if ( firstDate != null && !firstDate.isNull() ) {
                    exitDate = firstDate.clone();
                }

                while (true) {
                    final Date temp = nullCalendar.advance(seed, tenor_.mul(periods).negative(), convention, endOfMonth);
                    if (temp .lt(exitDate)) {
                        break;
                    } else {
                        dates_.add(0, temp);
                        isRegular_.add(0, Boolean.TRUE);
                        ++periods;
                    }
                }

                if (endOfMonth && calendar.isEndOfMonth(seed)) {
                    convention = BusinessDayConvention.Preceding;
                }

                if (calendar.adjust(dates_.get(0),convention).ne(
                        calendar.adjust(effectiveDate, convention))) {
                    dates_.add(0, effectiveDate);
                    isRegular_.add(0, Boolean.FALSE);
                }
                break;

            case Twentieth:
            case TwentiethIMM:
            case ThirdWednesday:
                if (endOfMonth) {
                    throw new TcalException("endOfMonth convention incompatible with " + rule_ +
                            " date generation rule");
                }
                // fall through
            case Forward:

                dates_.add(effectiveDate);

                seed = effectiveDate.clone();

                if (firstDate != null && !firstDate.isNull() ) {
                    dates_.add(firstDate);
                    final Date temp = nullCalendar.advance(seed, tenor_.mul(periods), convention, endOfMonth);
                    if (temp.ne(firstDate) ) {
                        isRegular_.add(Boolean.FALSE);
                    } else {
                        isRegular_.add(Boolean.TRUE);
                    }
                    seed = firstDate.clone();
                } else if (rule_ == DateGeneration.Rule.Twentieth ||
                        rule_ == DateGeneration.Rule.TwentiethIMM) {
                    final Date next20th = nextTwentieth(effectiveDate, rule_);
                    if (next20th.ne(effectiveDate)) {
                        dates_.add(next20th);
                        isRegular_.add(Boolean.FALSE);
                        seed = next20th.clone();
                    }
                }

                exitDate = terminationDate.clone();
                if ( nextToLastDate != null && !nextToLastDate.isNull() ) {
                    exitDate = nextToLastDate.clone();
                }

                while (true) {
                    final Date temp = nullCalendar.advance(seed, tenor_.mul(periods), convention, endOfMonth);
                    if ( temp.gt(exitDate) ) {
                        break;
                    } else {
                        dates_.add(temp);
                        isRegular_.add(Boolean.TRUE);
                        ++periods;
                    }
                }

                if (endOfMonth && calendar.isEndOfMonth(seed)) {
                    convention = BusinessDayConvention.Preceding;
                }

                if (calendar.adjust(dates_.get(dates_.size()-1),terminationDateConvention).ne(
                        calendar.adjust(terminationDate, terminationDateConvention)))
                    if (rule_ == DateGeneration.Rule.Twentieth ||
                            rule_ == DateGeneration.Rule.TwentiethIMM) {
                        dates_.add(nextTwentieth(terminationDate, rule_));
                        isRegular_.add(Boolean.valueOf(true));
                    } else {
                        dates_.add(terminationDate);
                        isRegular_.add(Boolean.valueOf(false));
                    }

                break;

            default:
                final String errMsg = "unknown Rule (" + rule_ + ")";
                throw new TcalException(errMsg); // TODO: message
        }

        // adjustments
        if (rule_== DateGeneration.Rule.ThirdWednesday) {
            for (int i=1; i<dates_.size()-1; ++i) {
                dates_.set(i, Date.nthWeekday(3, Weekday.Wednesday,
                        dates_.get(i).month(),
                        dates_.get(i).year()));
            }
        }

        for (int i=0; i<dates_.size()-1; ++i) {
            dates_.set(i, calendar.adjust(dates_.get(i), convention));
        }

        // termination date is NOT adjusted as per ISDA
        // specifications, unless otherwise specified in the
        // confirmation of the deal or unless we're creating a CDS
        // schedule
        if (terminationDateConvention != BusinessDayConvention.Unadjusted
                || rule_ == DateGeneration.Rule.Twentieth
                || rule_ == DateGeneration.Rule.TwentiethIMM) {
            dates_.set(dates_.size()-1, calendar.adjust(dates_.get(dates_.size()-1),
                    terminationDateConvention));
        }
    }

    // Date access
    public int size() /* @ReadOnly */ {
        return dates_.size();
    }

    public final Date at(final int i) /* @ReadOnly */ {
        return dates_.get(i);
    }

    public final Date date(final int i) /* @ReadOnly */ {
        return dates_.get(i);
    }


    public Date previousDate(final Date  refDate) /* @ReadOnly */ {
        final int index = Date.lowerBound(dates_, refDate);
        if ( index > 0 )
            return dates_.get(index-1).clone();
        else
            return new Date();
    }

    public Date nextDate(final Date  refDate) /* @ReadOnly */ {
        final int index = Date.lowerBound(dates_, refDate);
        if ( index < dates_.size() )
            return dates_.get(index).clone();
        else
            return new Date();
    }

    public List<Date> dates() /* @ReadOnly */ {
        return dates_;
    }

    public boolean isRegular(final int i) /* @ReadOnly */ {
        if (!fullInterface_) {
            throw new TcalException("full interface not available");
        }
        if (i > isRegular_.size() || i <= 0) {
            throw new TcalException("index (" + i + ") must be in [1, " +
                    isRegular_.size() +"]");
        }
        return isRegular_.get(i-1);
    }

    // Other inspectors

    public boolean empty() /* @ReadOnly */ {
        return  dates_.isEmpty();
    }

    public final Calendar calendar() /* @ReadOnly */ {
        return calendar_;
    }

    public final Date  startDate() /* @ReadOnly */ {
        return dates_.isEmpty() ? null :  dates_.get(0);
    }

    public final Date  endDate() /* @ReadOnly */ {
        return dates_.isEmpty() ? null : dates_.get(dates_.size()-1);
    }

    public final Period  tenor() /* @ReadOnly */ {
        if (!fullInterface_) {
            throw new TcalException("full interface not available");
        }
        return tenor_;
    }
    public BusinessDayConvention businessDayConvention() /* @ReadOnly */ {
        return convention_;
    }

    public BusinessDayConvention terminationDateBusinessDayConvention() /* @ReadOnly */ {
        if (!fullInterface_) {
            throw new TcalException("full interface not available");
        }
        return terminationDateConvention_;
    }

    public DateGeneration.Rule rule() /* @ReadOnly */ {
        if (!fullInterface_) {
            throw new TcalException("full interface not available");
        }
        return rule_;
    }

    public boolean endOfMonth() /* @ReadOnly */ {
        if (!fullInterface_) {
            throw new TcalException("full interface not available");
        }
        return endOfMonth_;
    }

    // Iterators

    @Deprecated
    //FIXME: this method will probably disappear as begin() and end() does not make sense withou pointers
    public Iterator<Date> begin() /* @ReadOnly */ {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    //FIXME: this method will probably disappear as begin() and end() does not make sense withou pointers
    public Iterator<Date> end() /* @ReadOnly */ {
        throw new UnsupportedOperationException();
    }

    public int lowerBound() /* @ReadOnly */ {
        return lowerBound( new Date() );
    }

    public int lowerBound(final Date refDate) /* @ReadOnly */{
        final Date d = (refDate.isNull() ? new Settings().evaluationDate() : refDate);
        return Date.lowerBound(dates_, d.clone());
    }



    //TODO :: operator Schedule() const;


    private Date nextTwentieth(final Date d, final DateGeneration.Rule rule) {
        final Date result = new Date(20, d.month(), d.year());
        if (result.lt(d) ) {
            result.addAssign(new Period(1, TimeUnit.Months)); //result +=1*Months
        }
        if (rule == DateGeneration.Rule.TwentiethIMM) {
            final Month m = result.month();
            final int mVal = m.value();
            if (mVal % 3 != 0) { // not a main IMM nmonth
                final int skip = 3 - mVal % 3;
//                result += skip*Months;
                result.addAssign(new Period(skip, TimeUnit.Months));
            }
        }
        return result;
    }


    /**
     * Standard C++ Library Reference lower_bound Finds the position of the first element in an ordered range that has a value greater than or equivalent to a specified value, where the ordering criterion may be specified by a binary predicate.
     *
     * @see "http://www.sgi.com/tech/stl/lower_bound.html"
     */
    // FIXME: http://bugs.jquantlib.org/view.php?id=67
    private Iterator<Date> std_lower_bound(final Date date) {

        final List<Date> ldates = new ArrayList<Date>();

        if (dates_.size() > 0) {
            int index = -1;
            for (int i = 0; i < dates_.size(); i++) {
                final Date d = dates_.get(i);
                if (d.equals(date)) {
                    index = i;
                    break;
                }
            }
            if (index > 0) {
                for (int i = index; i < dates_.size(); i++) {
                    ldates.add(dates_.get(i));
                }
                return ldates.iterator();
            }
        }
        return ldates.iterator();
    }

    public Iterator<Date> getDatesAfter(final Date date) {
        return std_lower_bound(date);
    }
}
