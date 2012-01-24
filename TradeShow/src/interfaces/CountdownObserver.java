package interfaces;

/*********************
 * CountdownObserver Interface
 * 
 * @author - Bryan Fearson, Ryan Farrell
 * @version - 1.0
 *********************/

/*
 * This work complies with the JMU Honor Code
 */

public interface CountdownObserver {
	/**
	 * Handles an incoming message
	 * @param time
	 * 			The message to handle
	 */
	public void handleTime(String time);

}

