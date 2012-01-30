package com.tradeshow.interfaces;


/*********************
 * CountdownSubject Interface
 * 
 * @author - Bryan Fearson, Ryan Farrell
 * @version - 1.0
 *********************/

/*
 * This work complies with the JMU Honor Code
 */

public interface CountdownSubject {
	/**
	 * Adds a new observer of the CountdownSubject
	 * @param observer
	 * 			The CountdownObserver that will be observing
	 */
	public void addObserver(CountdownObserver observer);

	/**
	 * Notifies any observers of an update in the countdown
	 * @param time
	 * 			The message to send to the observers
	 */
	public void notifyObservers(String time);
	
	/**
	 * Removes an observer from the list of observers
	 * @param observer
	 * 			The CountdownObserver to remove
	 */	
	public void removeObservers(CountdownObserver observer);

}

