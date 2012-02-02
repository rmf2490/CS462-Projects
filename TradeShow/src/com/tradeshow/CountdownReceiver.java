package com.tradeshow;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.HashMap;
import java.util.Map;

import com.tradeshow.interfaces.CountdownObserver;
import com.tradeshow.interfaces.CountdownSubject;

/***
 * Waits for input from a CountdownSender, and relays the message to all Observers of the CountdownReceiver object
 * 
 * @author Bryan Fearson, Ryan Farrell
 * @version 1.0
 */

/*
 * This work complies with the JMU Honor Code
 */

public class CountdownReceiver implements Runnable, CountdownSubject {

	private volatile boolean keepRunning;
	private volatile boolean needsToPrint; 
	private DatagramSocket dgs;
	private int udpPort;
	private HashMap<CountdownObserver, SubjectDelegate> observers;
	private Thread controlThread;

	/***
	 * Default Constructor
	 */
	public CountdownReceiver(){
		this(12345);
	}
	
	/***
	 * Constructor
	 * @param port
	 * 		port to listen for messages on
	 */
	public CountdownReceiver(int port) {
		udpPort = port;
		observers = new HashMap<CountdownObserver, SubjectDelegate>();
		try {
			dgs = new DatagramSocket(udpPort);
		} catch (SocketException e) {
			// Something wrong, probably incorrect or unavailable port
		}
	}

	/**********
	 * Adds a new observer of this receiver, and sets up the corresponding
	 * SubjectDelegate
	 * 
	 * @param observer
	 *            The CountdownObserver to receive updates
	 * 
	 ***********/
	@Override
	public void addObserver(CountdownObserver observer) {
		observers.put(observer, new SubjectDelegate());
		SubjectDelegate delegate = observers.get(observer);
		delegate.setObserver(observer);

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
	 * Notifies all observers of this receiver
	 * 
	 * @param time
	 *            The time message to send to the observer
	 */
	@Override
	public void notifyObservers(String time) {
		for (Map.Entry<CountdownObserver, SubjectDelegate> entry : observers
				.entrySet()) {
			SubjectDelegate delegate = entry.getValue();
			delegate.setMessage(time);
			delegate.setPrintStatus(true);
			if (delegate.start() == false) {
				delegate.resume();
			}
		}

	}
	
	/***
	 * Removes an observer from the list of those being notified by this
	 * receiver, if it exists
	 * 
	 * @param observer
	 *            The CountdownObserver to be removed
	 */
	@Override
	public void removeObservers(CountdownObserver observer) {
		SubjectDelegate delegate = observers.get(observer);
		delegate.stop();
		observers.remove(observer);

	}

	/***
	 * Monitors for incoming packets, and notifies observers when new messages arrive
	 */
	public void run() {
		while (keepRunning) {
			try {
				if (controlThread.isInterrupted()) {
					keepRunning = false;
				} else {
					byte[] buffer = new byte[255];
					DatagramPacket in = new DatagramPacket(buffer,
							buffer.length);
					dgs.setSoTimeout(10000);
					dgs.receive(in);
					String line = new String(in.getData(), 0, in.getLength());
					if (line != null && line.length() > 0) {
						notifyObservers(line);
					}
				}
			} catch (IOException e) {
				// receive failed. retry
			}
		}
		this.cleanUp();
	}

	/***
	 * Starts a new CountdownReceiver thread
	 */
	public void start() {
		if (controlThread == null) {
			controlThread = new Thread(this);
			keepRunning = true;
			controlThread.start();
		}
	}

	/***
	 * Stops this thread of execution
	 */
	public void stop() {
		if (controlThread != null) {
			keepRunning = false;

			controlThread.interrupt();

			controlThread = null;
		}
	}

}
