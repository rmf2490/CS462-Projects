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
 * @author Bryan Fearson, Ryan Farrell
 * @version 1.0
 */

/*
 * This work complies with the JMU Honor Code
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
		try {
			sSocket = new ServerSocket(port);
			sSocket.setSoTimeout(5000);
			System.out.println(sSocket.getSoTimeout());
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
		TradeClient current;
		ClientConnectionHandler cch;

		tce = connectionList.elements();
		while (tce.hasMoreElements()) {
			cch = tce.nextElement();
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
				System.out.println(sock);
				current = new TradeClient("localhost", sock.getPort(),
						sock.getInetAddress());
				try {
					cch = new ClientConnectionHandler(sock, this);
					// add to respective lists
					connectionList.put(cch, cch);
					clientList.put(cch, current);

					cch.start();
				}// nested try
				catch (Exception e) {
					// do nothing
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
	 * establishes a TCP Connection
	 * 
	 */
	public void setUpConnection() {
		try {
			sSocket = new ServerSocket(port);
			sSocket.setSoTimeout(5000);
		}// try
		catch (IOException ioe) {
			ioe.printStackTrace(); // if no connection can be made
			System.exit(1); // something is wrong. Terminate.
		}// catch

	}// setUpConnection

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