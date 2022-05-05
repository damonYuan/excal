package com.damonyuan.tcal.time;

public class MakeSchedule implements Cloneable {
    private Calendar calendar_;
    private Date effectiveDate_;
    private Date terminationDate_;
    private Period tenor_;
    private BusinessDayConvention convention_;
    private BusinessDayConvention terminationDateConvention_;
    private DateGeneration.Rule rule_;
    private boolean endOfMonth_;
    private Date firstDate_;
    private Date nextToLastDate_;

    public MakeSchedule(final Date effectiveDate, final Date terminationDate,
                        final Period tenor, final Calendar calendar,
                        final BusinessDayConvention convention) {
        this.calendar_ = calendar;
        this.effectiveDate_ = effectiveDate;
        this.terminationDate_ = terminationDate;
        this.tenor_ = tenor;
        this.convention_ = convention;
        this.terminationDateConvention_ = convention;
        this.rule_ = DateGeneration.Rule.Backward;
        this.endOfMonth_ = false;
        this.firstDate_ = new Date();
        this.nextToLastDate_ = new Date();
    }

    public MakeSchedule withTerminationDateConvention(
            final BusinessDayConvention conv) {
        terminationDateConvention_ = conv;
        return this.clone();
    }

    public MakeSchedule withRule(final DateGeneration.Rule r) {
        rule_ = r;
        return this.clone();

    }

    public MakeSchedule forwards() {
        rule_ = DateGeneration.Rule.Forward;
        return this.clone();
    }

    public MakeSchedule backwards() {
        rule_ = DateGeneration.Rule.Backward;
        return this.clone();

    }

    public MakeSchedule endOfMonth() {
        return endOfMonth(true);

    }

    public MakeSchedule endOfMonth(final boolean flag) {
        endOfMonth_ = flag;
        return this.clone();
    }

    public MakeSchedule withFirstDate(final Date d) {
        firstDate_ = d;
        return this.clone();
    }

    public MakeSchedule withNextToLastDate(final Date d) {
        nextToLastDate_ = d;
        return this.clone();
    }

    /**
     * MakeSchedule::operator Schedule() const { return Schedule(effectiveDate_,
     * terminationDate_, tenor_, calendar_, convention_,
     * terminationDateConvention_, rule_, endOfMonth_, firstDate_,
     * nextToLastDate_);
     *
     * @return
     */
    public Schedule schedule() {
        return new Schedule(effectiveDate_, terminationDate_, tenor_,
                calendar_, convention_, terminationDateConvention_, rule_,
                endOfMonth_, firstDate_, nextToLastDate_);
    }

    @Override
    public MakeSchedule clone() {
        final MakeSchedule clone = new MakeSchedule(effectiveDate_,
                terminationDate_, tenor_, calendar_, convention_);
        clone.calendar_ = calendar_;
        clone.effectiveDate_ = effectiveDate_.clone();
        clone.terminationDate_ = terminationDate_.clone();
        clone.tenor_ = tenor_;
        clone.convention_ = convention_;
        clone.terminationDateConvention_ = terminationDateConvention_;
        clone.rule_ = rule_;
        clone.endOfMonth_ = endOfMonth_;
        clone.firstDate_ = firstDate_.clone();
        clone.nextToLastDate_ = nextToLastDate_.clone();

        return clone;
    }
}
