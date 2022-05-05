package com.damonyuan.tcal.time;

import com.damonyuan.tcal.common.TcalException;

public class DateParser {

    /**
     * Convert ISO format strings to Date. Ex: 2008-03-31
     *
     * @param str
     * @return Date
     */
    public static Date parseISO(final String str) {
        if (str.length() != 10 || str.charAt(4) != '-' || str.charAt(7) != '-') {
            throw new TcalException("invalid format");
        }

        final int year = Integer.parseInt(str.substring(0, 4));
        final int month = Integer.parseInt(str.substring(5, 7));
        final int day = Integer.parseInt(str.substring(8, 10));

        final Date date = new Date(day, Month.valueOf(month), year);
        // QL.debug(date.isoDate().toString());
        return date;
    }

    /**
     * Convert the String with separator '/' to Date using the format specified.
     *
     * For example: "2008/03/31", "yyyy/MM/dd"
     *
     * @param str
     * @param fmt
     * @return Date
     */
    public static Date parse(final String str, final String fmt) {
        String[] slist = null;
        String[] flist = null;
        int d = 0, m = 0, y = 0;

        slist = str.split("/");
        flist = fmt.split("/");

        Date date;

        if (slist.length != flist.length) {
            date = new Date();
        } else {
            for (int i = 0; i < flist.length; i++) {
                final String sub = flist[i];
                if (sub.equalsIgnoreCase("dd")) {
                    d = Integer.parseInt(slist[i]);
                } else if (sub.equalsIgnoreCase("mm")) {
                    m = Integer.parseInt(slist[i]);
                } else if (sub.equalsIgnoreCase("yyyy")) {
                    y = Integer.parseInt(slist[i]);
                    if (y < 100) {
                        y += 2000;
                    }
                }
            }
            date = new Date(d, m, y);
        }

        // QL.debug(date.isoDate().toString());
        return date;
    }
}
