package com.tradeshow.trader;

import java.io.*;
import java.util.*;
import java.net.*;
import javax.swing.*;
import java.awt.event.*;
import com.tradeshow.interfaces.*;

/**
 * A Server responsible for acting as the "dropbox" of the movies available.
 * 
 * @author Ryan Farrell, Bryan Fearson
 * @version 1.0
 */
public class TradeServer implements TradeObserver, Runnable {
	private volatile boolean running;
	private BufferedReader input;
	private int port;
	private PrintWriter output;
	private ServerSocket sSocket;
	private Socket sock;
	private Thread controlThread;
	private TradeClient client;
	private Hashtable<ClientConnectionHandler, ClientConnectionHandler> connectionList;
	private Hashtable<ClientConnectionHandler, TradeClient> clientList;

	/**
	 * Constructor
	 * 
	 * @param the
	 *            serverPort used
	 */
	public TradeServer(int serverPort) {
		this.port = serverPort;
		// System.out.println("Server Port:" + port);
		try {
			sSocket = new ServerSocket(port);
			sSocket.setSoTimeout(5000);
		}// try
		catch (IOException ioe) {
			ioe.printStackTrace(); // if no connection can be made
			System.exit(1); // something is wrong. Terminate.
		}// catch

		connectionList = new Hashtable<ClientConnectionHandler, ClientConnectionHandler>();
		clientList = new Hashtable<ClientConnectionHandler, TradeClient>();
	}// constructor

	/**
	 * Handles the Available Movies presentation to all clients on server.
	 * 
	 */
	public synchronized void handleAvailableMovie(String movie) {

		Enumeration<ClientConnectionHandler> tce;
		TradeClient current;
		ClientConnectionHandler cch;

		// System.out.println("handleAvailableMovie");
		tce = connectionList.elements();
		while (tce.hasMoreElements()) {
			cch = tce.nextElement();
			// System.out.println(cch);
			current = clientList.get(cch);

			current.showMessage(movie);
		}// while

	}// handleAvailableMovie class

	/**
	 * The entry point for the new thread of execution.
	 * 
	 **/

	public void run() {
		Socket sock;
		ClientConnectionHandler cch;
		TradeClient current;
		while (running) {
			try {
				sock = sSocket.accept();
				// System.out.println(sock);
				// System.out.println("PORT: " +sock.getPort());
				output = new PrintWriter(sock.getOutputStream());
				input = new BufferedReader(new InputStreamReader(
						sock.getInputStream()));
				try {
					current = new TradeClient("localhost", sock.getPort(),
							sock.getInetAddress());
					System.out.println("current");
					cch = new ClientConnectionHandler(sock, this);
					// add to respective lists
					connectionList.put(cch, cch);
					clientList.put(cch, current);

					cch.start();
				}// nested try
				catch (IOException e) {
					System.out.println(e);// do nothing
				}
			}// try
			catch (Exception e) {
				System.out.println("TIME");
				// socket timeout, go back to try block
			}// catch

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
		}// if
	}// start

	/**
	 * stop the thread of execution
	 * 
	 */
	public void stop() {
		running = false;
	}// stop

}// class TradeServer