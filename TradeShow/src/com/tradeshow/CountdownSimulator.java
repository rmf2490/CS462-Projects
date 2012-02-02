package com.tradeshow;
import java.util.*;

import com.tradeshow.interfaces.*;

/*********************
 * Simulates the playing of a movie
 * 
 * CURRENT BUGS:
 * 1) Sometimes the last message (where time = 0) won't print due to a race condition. Debug using breakpoints
 * 
 * @author - Bryan Fearson, Ryan Farrell
 * @version - 1.0
 *********************/

/*
 * This work complies with the JMU Honor Code
 */

public class CountdownSimulator implements Runnable, CountdownSubject {
	// private ArrayList<CountdownObserver> observers;
	private volatile boolean isPlaying;
	private boolean realTime;
	private int movieLength;
	private int notifyIncrement;
	private String movieName;
	private volatile Thread controlThread;
	private HashMap<CountdownObserver, SubjectDelegate> observers;

	/********************
	 * Constructor for a CountdownSimulator.
	 * 
	 * @param movie
	 *            Movie this CountdownSimulator is handling
	 * @param length
	 *            Length of the movie
	 * @param startTime
	 *            What time in the movie to start at
	 * @param realTime
	 *            True if this Simulator runs in real time, false if not
	 * @param notifyIncrement
	 *            How often to notify the observers. If realTime: The value
	 *            passed in corresponds to how many minutes to wait (5 = 5
	 *            minutes). If !realTime: The value passed in simulates the given
	 *            number of minutes per second
	 ********************/
	public CountdownSimulator(String movie, int length, int startTime,
			boolean realTime, int notifyIncrement) {
		movieName = movie;
		movieLength = length - startTime;
		this.realTime = realTime;
		this.notifyIncrement = notifyIncrement;
		// observers = new ArrayList<CountdownObserver>();
		observers = new HashMap<CountdownObserver, SubjectDelegate>();

	}

	/**********
	 * Adds a new observer of this simulator, and sets up the corresponding
	 * SubjectDelegate
	 * 
	 * @param observer
	 *            The CountdownObserver to receive updates
	 * 
	 ***********/
	public void addObserver(CountdownObserver observer) {
		observers.put(observer, new SubjectDelegate());
		SubjectDelegate delegate = observers.get(observer);
		delegate.setObserver(observer);
		delegate.start();
	}

	/***
	 * End any running SubjectDelegate threads
	 */
	private void cleanUp() {
		for(Map.Entry<CountdownObserver, SubjectDelegate> entry: observers.entrySet()) {
			SubjectDelegate aDelegate = entry.getValue();
			aDelegate.stop();
		}
	}
	
	/****
	 * Notifies all observers of this simulator
	 * 
	 * @param time
	 *            The time message to send to the observer
	 */
	public void notifyObservers(String time) {
		for(Map.Entry<CountdownObserver, SubjectDelegate> entry: observers.entrySet()) {
			SubjectDelegate delegate = entry.getValue();
			delegate.setMessage(time);
			delegate.setPrintStatus(true);
			delegate.resume();
			
		}

	}

	/***
	 * Removes an observer from the list of those being notified by this
	 * simulation, if it exists
	 * 
	 * @param observer
	 *            The CountdownObserver to be removed
	 */
	public void removeObservers(CountdownObserver observer) {

		SubjectDelegate delegate = observers.get(observer);
		delegate.stop();
		observers.remove(observer);
	}

	/***
	 * Waits a given amount of time, and then notifies all observers of this
	 * simulation of the current time left
	 */
	public void run() {
		while (isPlaying) {
			try {
				if (realTime) {
					Thread.sleep(notifyIncrement * 60000);
				} else {
					Thread.sleep(1000);
				}
				movieLength = movieLength - notifyIncrement;
				if (movieLength <= 0) {
					movieLength = 0;
					isPlaying = false;
				}
				String message = movieName + " / Time left: " + movieLength;
				notifyObservers(message);
			} catch (InterruptedException e) {
				//Do nothing, code will drop out of loop
			}

		}
		this.stop();
		this.cleanUp();
	}

	/***
	 * Starts CountdownSimulator's run method in a new thread
	 */
	public void start() {
		if (controlThread == null) {
			controlThread = new Thread(this);
			isPlaying = true;
			controlThread.start();
		}

	}
	
	/***
	 * Terminates the thread
	 */
	public void stop() {
		if(controlThread != null)
		{
			isPlaying = false;

			controlThread.interrupt();

			controlThread = null;
		}
	}
}
