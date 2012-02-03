package com.tradeshow.trader;

import java.io.*;
import java.util.*;
import java.net.*;
import com.tradeshow.interfaces.*;

/**
 * A Server responsible for acting as the "dropbox" of the movies available.
 * 
 * @author Ryan Farrell, Bryan Fearson
 * @version 1.0
 */

/*
 * This Work complies with JMU Honor Code
 */

public class TradeServer implements TradeObserver, Runnable {
	private volatile boolean running;
	private int port;
	private ServerSocket sSocket;
	private Thread controlThread;
	private Hashtable<ClientConnectionHandler, ClientConnectionHandler> connectionList;

	/**
	 * Constructor
	 * 
	 * @param the
	 *            serverPort used
	 */
	public TradeServer(int serverPort) {
		port = serverPort;
		// System.out.println("Server Port:" + port);
		try {
			sSocket = new ServerSocket(port, 0, InetAddress.getByName("localhost"));
		}// try
		catch (IOException ioe) {
			ioe.printStackTrace(); // if no connection can be made
			System.exit(1); // something is wrong. Terminate.
		}// catch

		connectionList = new Hashtable<ClientConnectionHandler, ClientConnectionHandler>();
	}// constructor

	/**
	 * Handles the Available Movies presentation to all clients on server.
	 * 
	 */
	public synchronized void handleAvailableMovie(String movie) {

		Enumeration<ClientConnectionHandler> tce;
		ClientConnectionHandler cch;

		tce = connectionList.elements();
		while (tce.hasMoreElements()) {
			cch = tce.nextElement();
			cch.handleMessage(movie);
		}// while

	}// handleAvailableMovie class

	/**
	 * The entry point for the new thread of execution.
	 * 
	 **/

	public void run() {
		Socket sock;
		ClientConnectionHandler cch;
		while (running) {
			if (controlThread.isInterrupted()) {
				running = false;
			} else {
				try {
					sSocket.setSoTimeout(60000);
					sock = sSocket.accept();
					cch = new ClientConnectionHandler(sock, this);
					connectionList.put(cch, cch);
					cch.start();
				} catch (SocketTimeoutException ste) {
					//Timed out, keep going
					//System.out.println(ste);
				} catch (IOException ioe) {
					//ioe.printStackTrace();
					//System.out.println(ioe);
				} catch (Exception e){
					//e.printStackTrace();
				}

				
			}// try
		}// while

		controlThread = null;

	}// run

	/**
	 * start the thread of execution
	 * 
	 */
	public void start() {
		if (controlThread == null) {
			running = true;
			controlThread = new Thread(this);
			controlThread.start();
		}// 
	}// start

	/**
	 * stop the thread of execution
	 * 
	 */
	public void stop() {
		if (controlThread != null) {
			running = false;

			controlThread.interrupt();

			controlThread = null;
			
			Enumeration<ClientConnectionHandler> tce;
			ClientConnectionHandler cch;

			tce = connectionList.elements();
			while (tce.hasMoreElements()) {
				cch = tce.nextElement();
				cch.stop();
			}
		}

	}// stop

}// class TradeServer