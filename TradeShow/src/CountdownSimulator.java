import java.util.*;
import interfaces.*;

/*********************
 * Simulates the playing of a movie
 * 
 * @author - Bryan Fearson, Ryan Farrell
 * @version - 1.0
 *********************/

/*
 * This work complies with the JMU Honor Code
 */

public class CountdownSimulator implements Runnable, CountdownSubject {
	private ArrayList<CountdownObserver> observers;
	private boolean realTime;
	private int movieLength;
	private int notifyIncrement;
	private String movieName;
	private Thread controlThread;
	//private Hashtable<SubjectDelegate, CountdownObserver> observers;
	

	/********************
	 * Constructor for a CountdownSimulator.
	 * 
	 * @param movie
	 * 			Movie this CountdownSimulator is handling
	 * @param length
	 * 			Length of the movie
	 * @param startTime
	 * 			What time in the movie to start at
	 * @param realTime
	 * 			True if this Simulator runs in real time, false if not
	 * @param notifyIncrement
	 * 			How often to notify the observers.
	 * 				If realTime: The value passed in corresponds to how many minutes to wait (5 = 5 minutes)
	 * 				If !realTime: The value passed in simulates the given number of minutes per second
	 ********************/
	public CountdownSimulator(String movie, int length, int startTime, boolean realTime, int notifyIncrement) {
		movieName = movie;
		movieLength = length - startTime;
		this.realTime = realTime;
		this.notifyIncrement = notifyIncrement;
		observers = new ArrayList<CountdownObserver>();
		

	}
	
	
	/**********
	 * Adds a new observer of this simulator
	 * 
	 * @param observer
	 * 			The CountdownObserver to receive updates
	 * 
	 ***********/
	public void addObserver(CountdownObserver observer) {
		observers.add(observer);
	}

	/****
	 * Notifies all observers of this simulator
	 * 
	 * @param time
	 * 			The time message to send to the observer
	 */
	public void notifyObservers(String time) {
		//Change this to use an iterator?
		for(int i=0; i<observers.size(); i++){
			CountdownObserver observer = observers.get(i);
			new SubjectDelegate(time, observer).start();
		}

	}

	/***
	 * Removes an observer from the list of those being notified by this simulation, if it exists
	 * 
	 * @param observer
	 * 			The CountdownObserver to be removed
	 */
	public void removeObservers(CountdownObserver observer) {
		observers.remove(observer);
	}
	
	/***
	 * Waits a given amount of time, and then notifies all observers of this simulation of the current time left
	 */
	public void run() {
		while(movieLength > 0){
			try {
				if(realTime){
					Thread.sleep(notifyIncrement * 60000);
				}
				else{
					Thread.sleep(1000);
				}
			} catch (InterruptedException e) {
				//Just continue
			}
			movieLength = movieLength - notifyIncrement;
			if(movieLength < 0){
				movieLength = 0;
			}
			String message = movieName + "/Time left: " + movieLength;
			notifyObservers(message);
		}
	}
	
	/***
	 * Starts CountdownSimulator's run method in a new thread
	 */
	public void start(){
		if(controlThread == null){
			controlThread = new Thread(this);
		}
		controlThread.start();
	}
}
