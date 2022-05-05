package com.damonyuan.tcal.time;

public class TimeSeries<V> extends Series<Date,V> {

    public TimeSeries(final Class<V> classV) {
        super(Date.class, classV);
    }

}
