package com.damonyuan.tcal.common;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class DefaultObservable implements Observable {

    final private static String OBSERVABLE_IS_NULL = "observable is null";
    final private static String CANNOT_NOTIFY_OBSERVERS = "could not notify one or more observers";

    //
    // private final fields
    //

    private final List<Observer> observers;
    private final Observable observable;

    //
    // public constructors
    //

    public DefaultObservable(final Observable observable) {
        if (observable == null) {
            throw new TcalException(DefaultObservable.OBSERVABLE_IS_NULL);
        }
        this.observers = new CopyOnWriteArrayList<Observer>();
        this.observable = observable;
    }

    //
    // public methods
    //

    @Override
    public void addObserver(final Observer observer) {
        observers.add(observer);
    }

    @Override
    public int countObservers() {
        return observers.size();
    }

    @Override
    public List<Observer> getObservers() {
        return Collections.unmodifiableList(this.observers);
    }

    @Override
    public void deleteObserver(final Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void deleteObservers() {
        observers.clear();
    }

    @Override
    public void notifyObservers() {
        notifyObservers(null);
    }

    @Override
    public void notifyObservers(final Object arg) {
        Exception exception = null;
        for (final Observer observer : observers) {
            try {
                wrappedNotify(observer, observable, arg);
            } catch (final Exception e) {
                // Quite a dilemma. If we don't catch the exception,
                // other observers will not receive the notification
                // and might be left in an incorrect state. If we do
                // catch it and continue the loop (as we do here) we
                // lose the exception. The least evil might be to try
                // and notify all observers, while raising an
                // exception if something bad happened.
                exception = e;
            }
        }
        if (exception!=null) {
            throw new TcalException(DefaultObservable.CANNOT_NOTIFY_OBSERVERS, exception);
        }
    }

    //
    // protected methods
    //

    /**
     * This method is intended to encapsulate the notification semantics, in
     * order to let extended classes to implement their own version. Possible
     * implementations are:
     * <li>remote notification;</li>
     * <li>notification via SwingUtilities.invokeLater</li>
     * <li>others...</li>
     *
     * <p>
     * The default notification simply does
     * <pre>
     * observer.update(observable, arg);
     * </pre>
     *
     * @param observer
     * @param observable
     * @param arg
     */
    protected void wrappedNotify(final Observer observer, final Observable observable, final Object arg) {
        //XXX::OBS observer.update(observable, arg);
        observer.update();
    }
}
