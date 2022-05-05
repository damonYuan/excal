package com.damonyuan.tcal.time;

import com.damonyuan.tcal.common.TcalException;

public class DateGeneration {

    public static enum Rule {
        /**
         * Backward from termination date to effective date.
         */
        Backward   (1),

        /**
         * Forward from effective date to termination date.
         */
        Forward   (2),

        /**
         * No intermediate dates between effective date and termination date.
         */
        Zero   (3),

        /**
         * All dates but effective date and termination date are taken to be on the third wednesday of their month (with forward
         * calculation.)
         */
        ThirdWednesday   (4),

        /**
         * All dates but the effective date are taken to be the twentieth of their month (used for CDS schedules in emerging
         * markets.) The termination date is also modified.
         */
        Twentieth   (5),

        /**
         * All dates but the effective date are taken to be the twentieth of an IMM month (used for CDS schedules.) The termination
         * date is also modified.
         */
        TwentiethIMM   (6);


        //
        // private static fields
        //

        private static final String UNKNOWN_DATE_GENERATION_RULE = "unknown date generation rule";


        //
        // private fields
        //

        private final int rule;


        //
        // private constructors
        //

        private Rule(final int rule) {
            this.rule = rule;
        }


        //
        // overrides Object
        //

        @Override
        public String toString() {
            switch (rule) {
                case 1:
                    return "Backward";
                case 2:
                    return "Forward";
                case 3:
                    return "Zero";
                case 4:
                    return "ThirdWednesday";
                case 5:
                    return "Twentieth";
                case 6:
                    return "TwentiethIMM";
                default:
                    throw new TcalException(UNKNOWN_DATE_GENERATION_RULE);
            }
        }

    }

}
