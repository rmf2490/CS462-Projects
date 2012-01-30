package com.tradeshow.interfaces;
/**
 * The requirements of an object that wants to be informed
 * of available movies
 *
 * @author  Prof. David Bernstein, James Madison University
 * @version 1.0
 */
public interface TradeObserver
{
    /**
     * Handle a report about an available movie
     *
     * @param movie   The name of the movie
     */
    public abstract void handleAvailableMovie(String movie);
    
}
