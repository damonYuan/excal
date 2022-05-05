package com.damonyuan.tcal.common;

import java.util.List;

public interface Observable {

    /**
     * Attaches a observer to the Observable. After attachment the observer gets
     * informed about changes in the Observable.
     *
     * @param observer
     *            The observer to attach to the observable
     */
    public void addObserver(final Observer observer);


    /**
     * Counts how many Observers were attached to this class.
     *
     * @return the number of Observers
     * @see Observer
     */
    public int countObservers();


    /**
     * Returns list of observers registered with the Observable. List returned is
     * unmodifiable list.
     *
     * @return list of observers
     */
    public List<Observer> getObservers();


    /**
     * Detaches a previously attached observer to the observable. After
     * detachment the observer does no longer receive change notifications from
     * the observable.
     *
     * @param observer
     *            The observer to detach from the observable
     */
    public void deleteObserver(final Observer observer);


    /**
     * Detaches all previously attached observer to the observable. After
     * detachment observers do not longer receive change notifications from
     * the observable.
     */
    public void deleteObservers();


    /**
     * Notifies all attached observers about changes in the observable.
     */
    public void notifyObservers();

    /**
     * Notifies all attached observers about changes in the observable.
     *
     * @param arg
     *            an arbitrary Object to be passed to the Observer
     */
    public void notifyObservers(Object arg);
}
