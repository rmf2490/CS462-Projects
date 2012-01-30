package com.tradeshow;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.tradeshow.interfaces.CountdownObserver;


/*********************
 * Passes on a message to be sent to a CountdownObserver in it's own thread of
 * execution
 * 
 * @author - Bryan Fearson, Ryan Farrell
 * @version - 1.0
 *********************/

/*
 * This work complies with the JMU Honor Code
 */

public class SubjectDelegate implements Runnable {

	private boolean keepRunning;
	private CountdownObserver observer;
	private final Object lock = new Object();
	private String message;
	private volatile Thread controlThread;
	
	
	public SubjectDelegate() {
		controlThread = null;
		message = "";
		observer = null;

	}

	/***
	 * Resumes execution of the thread.
	 * 
	 * This is used for notifying the SubjectDelegate that a new message needs
	 * to be sent
	 */
	public void resume() {
		synchronized (lock) {
			lock.notifyAll();
		}
	}

	/***
	 * Notifies a CountdownObserver and waits until resume() is called (when a
	 * new message is received)
	 */
	public void run() {
		while (keepRunning) {
			synchronized (lock) {
				
				observer.handleTime(message);

				try {
					
					lock.wait();
				} catch (InterruptedException ie) {
				}
			}
		}

		controlThread = null;
	}

	/***
	 * Sets the message to be sent to a CountdownObserver
	 * 
	 * @param message
	 *            The message to be sent
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/***
	 * Sets the CountdownObservers this SubjectDelegate will be notifying
	 * 
	 * @param observer
	 *            The CountdownObserver to notify
	 */
	public void setObserver(CountdownObserver observer) {
		this.observer = observer;
	}

	/***
	 * Starts a new thread of execution
	 * 
	 * @return TRUE if a new thread was created, FALSE if this object is already
	 *         running in a thread
	 */
	public boolean start() {
		if (controlThread == null) {
			controlThread = new Thread(this);
			keepRunning = true;
			controlThread.start();
			return true;
		}
		return false;
	}//

	/***
	 * Terminates the thread of execution
	 */
	public void stop() {
		if (controlThread != null) {

			synchronized (lock) {
				keepRunning = false;

				controlThread.interrupt();
				
				controlThread = null;
			}
		}

	}//

}// end class SubjectDelegate