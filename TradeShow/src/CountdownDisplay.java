import interfaces.*;

/*********************
 * Observer that displays a message on the system console
 * 
 * @author - Bryan Fearson
 * @version -
 *********************/

/*
 * This work complies with the JMU Honor Code
 */

public class CountdownDisplay implements CountdownObserver {

	/***
	 * Constructor for a CountdownDisplay
	 ***/
	public CountdownDisplay(){
		//No arguments, just pass message directly to the handleTime() method instead
	}

	/***
	 * Prints a message to the console
	 * @param message
	 * 			The message to be printed
	 ***/
	public synchronized void handleTime(String message) {
		System.out.println(message);
		System.out.flush();
	}

}// end CountdownDisplay