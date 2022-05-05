package com.damonyuan.tcal.time;

import com.damonyuan.tcal.common.Settings;
import com.damonyuan.tcal.common.TcalException;

public class IMM {

    /**
     * Checks if Date is an IMM date or not
     *
     * @param date
     * @return
     */
    public static boolean isIMMdate(final Date date) {
        return isIMMdate(date, true);
    }

    /**
     * Returns whether or not the given date is an IMM date. For non main cycle,
     * third Wednesday in a month is returned true,
     *
     * @param date
     * @param mainCycle
     * @return
     */
    public static boolean isIMMdate(final Date date, final boolean mainCycle) {
        if (date.weekday()!=Weekday.Wednesday) {
            return false;
        }

        final int d = date.dayOfMonth();
        if (d<15 || d>21) {
            return false;
        }

        if (!mainCycle) {
            return true;
        }

        final Month m = date.month();
        return (m==Month.March || m==Month.June || m==Month.September || m==Month.December);
    }


    /**
     * Checks if Strings specifies a IMM code or not
     *
     * @param in
     * @return
     */
    public static boolean isIMMcode(final String in) {
        return isIMMcode(in, true);
    }

    /**
     * Returns whether or not the given string is an IMM code
     *
     * @param in
     * @param mainCycle
     * @return
     */
    public static boolean isIMMcode(final String in, final boolean mainCycle) {
        if (in.length() != 2) {
            return false;
        }

        if ("0123456789".indexOf(in.charAt(1))==-1) {
            return false;
        }

        String str1;
        if (mainCycle) {
            str1 = "hmzuHMZU";
        } else {
            str1 = "fghjkmnquvxzFGHJKMNQUVXZ";
        }

        if (str1.indexOf(in.charAt(0))==-1) {
            return false;
        }

        return true;
    }

    /**
     * Returns Date from IMM code
     *
     * @param immCode
     * @return
     */
    public static Date date(final String immCode) {
        return date(immCode, new Date());
    }

    /**
     * Returns the IMM date for the given IMM code (e.g. March 20th, 2013 for H3).
     * When <code>Date.NULL_DATE</code> is passed, <code>Settings.getEvaluationDate</code> is used as
     * a reference date.
     *
     * @param immCode
     * @param refDate
     * @return
     */
    // FIXME: this method is potentially harmful in heavily multi-threaded environments
    public static Date date(final String immCode, final Date refDate) {
        if (!isIMMcode(immCode, false)) {
            throw new TcalException("not a valid IMM code");
        }

        Date referenceDate;
        if (refDate.isNull()) {
            referenceDate = new Settings().evaluationDate();
        } else {
            referenceDate = refDate;
        }

        final char code = immCode.charAt(0);
        final Month m = Month.valueOf(code);

        int y = immCode.charAt(1) - '0';

        /* year<1900 are not valid QuantLib years: to avoid a run-time
           exception few lines below we need to add 10 years right away */
        if (y==0 && referenceDate.year()<=1909) {
            y+=10;
        }
        final int yMod = (referenceDate.year() % 10);
        y += referenceDate.year() - yMod;
        final Date result = nextDate(new Date(1, m, y), false);
        if (result.lt(referenceDate)) {
            return nextDate(new Date(1, m, y+10), false);
        }

        return result;
    }

    /**
     * Returns next main cycle IMM Date using Settings.getEvaluationDate as reference date.
     *
     * @return
     */
    public static Date nextDate() {
        return nextDate(new Date(), true);
    }

    /**
     *  Returns next main cycle IMM Date from the specified date.
     *
     *
     * @param date
     * @return
     */
    public static Date nextDate(final Date date) {
        return nextDate(date, true);
    }

    /**
     * next IMM date following the given date.
     * <p>
     *
     * When <code>Date.NULL_DATE</code> is passed, <code>Settings.getEvaluationDate</code> is used as a reference date.
     *
     * @param date
     * @param mainCycle
     * @return the 1st delivery date for next contract listed in the
     *   International Money Market section of the Chicago Mercantile
     *   Exchange.
     */
    public static Date nextDate(final Date date, final boolean mainCycle) {
        Date refDate;
        if (date.isNull()) {
            refDate = new Settings().evaluationDate();
        } else {
            refDate = date;
        }

        int y = refDate.year();
        int m = refDate.month().value();

        final int offset = mainCycle ? 3 : 1;
        int skipMonths = offset-(m%offset);
        if (skipMonths != offset || refDate.dayOfMonth() > 21) {
            skipMonths += m;
            if (skipMonths<=12) {
                m = skipMonths;
            } else {
                m = skipMonths-12;
                y += 1;
            }
        }

        Date result = Date.nthWeekday(3, Weekday.Wednesday, Month.valueOf(m), y);
        if (result.le(refDate)) {
            result = nextDate(new Date(22, Month.valueOf(m), y), mainCycle);
        }
        return result;
    }



    /**
     * Returns next main cycle IMM date from the given IMM code
     *
     * @param immCode
     * @return
     */
    public static Date nextDate(final String immCode) {
        return nextDate(immCode, true, new Date());
    }


    /**
     * Returns next IMM date from the given IMM code
     *
     * @param immCode
     * @param mainCycle
     * @return
     */
    public static Date nextDate(final String immCode, final boolean mainCycle) {
        return nextDate(immCode, mainCycle, new Date());
    }


    /**
     * Next IMM date following the given IMM code
     *
     * @param IMMcode
     * @param mainCycle
     * @param referenceDate
     * @return the 1st delivery date for next contract listed in the
     *  International Money Market section of the Chicago Mercantile
     *  Exchange.
     */
    public static Date nextDate(final String IMMcode, final boolean mainCycle, final Date referenceDate)  {
        final Date immDate = date(IMMcode, referenceDate);
        return nextDate(immDate.inc(), mainCycle);
    }


    /**
     * Returns next main cycle IMM code from the reference date
     *
     * @return
     */
    public static String nextCode() {
        return nextCode(new Date());
    }

    /**
     * Returns next cycle IMM code from the specified date
     * @param d
     * @return
     */
    public static String nextCode(final Date d) {
        return nextCode(d, true);
    }


    /**
     * Returns next cycle IMM code
     *
     * @param d
     * @param mainCycle
     * @return the IMM code for next contract listed in the
     * International Money Market section of the Chicago Mercantile
     * Exchange.
     */
    public static String nextCode(final Date d,
                                  final boolean mainCycle) {
        final Date date = nextDate(d, mainCycle);
        return code(date);
    }


    /**
     * Returns next main cycle IMM code from the given IMM Code.
     *
     * @param immCode
     * @return
     */
    public static String nextCode(final String immCode) {
        return nextCode(immCode, true);
    }

    /**
     * Returns next IMM code from the given IMM code
     *
     * @param immCode
     * @param mainCycle
     * @return
     */
    public static String nextCode(final String immCode, final boolean mainCycle) {
        return nextCode(immCode, mainCycle, new Date());
    }


    /**
     * Return next IMM code from the given IMM code and reference date
     *
     * @param immCode
     * @param mainCycle
     * @param referenceDate
     * @return the IMM code for next contract listed in the
     * International Money Market section of the Chicago Mercantile
     * Exchange.
     */
    public static String nextCode(final String immCode, final boolean mainCycle, final Date referenceDate) {
        final Date date = nextDate(immCode, mainCycle, referenceDate);
        return code(date);
    }


    /**
     * Returns the IMM code for the given date (e.g. H3 for March 20th, 2013).
     *
     * @param date
     * @return
     */
    // FIXME: this method is potentially harmful in heavily multi-threaded environments
    public static String code(final Date date) {
        if (!isIMMdate(date, false)) {
            throw new TcalException("not an IMM date");
        }

        final int y = date.year() % 10;
        final char code = date.month().getImmChar();
        final StringBuilder sb = new StringBuilder();
        sb.append(code).append(y);

        final String imm = sb.toString();

        if (!isIMMcode(imm, false)) {
            throw new TcalException("the result is an invalid IMM code");
        }
        return imm;
    }

}
