package com.tradeshow.interfaces;

/**
 * What is required of a TradeSubject
 * (i.e., a subject in the sense of the observer pattern)
 *
 * @author  Prof. David Bernstein, James Madison University
 * @version 1.0
 */
public interface TradeSubject
{
    /**
     * Add a TradeObserver to the collection of observers
     *
     * @param observer   The observer to add
     */
    public abstract void addObserver(TradeObserver observer);
    

    /**
     * Notify all current observers of a new movie
     *
     * @param movie       The movie that is available
     */
    public abstract void notifyObservers(String movie);
    

    /**
     * Remove a TradeObserver from the collection of observers
     *
     * @param observer   The observer to remove
     */
    public abstract void removeObserver(TradeObserver observer);
    
}
