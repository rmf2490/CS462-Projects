package com.tradeshow.trader;

import java.io.*;
import java.net.*;

/**
 * Acts as the passengers machine that is watching a movie.
 * 
 * @author Ryan Farrell, Bryan Fearson
 * @version 1.0
 */

/*
 * This Work complies with JMU Honor Code
 */

public class TradeClient {
	private BufferedReader input;
	private int port;
	private Socket sock;
	private String host;

	/**
	 * Constructor for a TradeClient that runs on the host
	 * 
	 * @param reqHost
	 *            the host name to connect to
	 * 
	 * @param reqPort
	 *            the port to connect client to
	 */
	public TradeClient(String reqHost, int reqPort) throws IOException,
			SocketException {
		port = reqPort;
		host = reqHost;
		System.out.println("TRADE CLIENT PORT:" + port);
		sock = new Socket(host, port);
		boolean isConnected = true;

		input = new BufferedReader(new InputStreamReader(sock.getInputStream()));

		while (sock.isConnected() && isConnected) {
			String message = input.readLine();
			if (message == null) {
				isConnected = false;
			} else {
				showMessage(message);
			}

		}

	}// constructor

	/**
	 * outputs the Available Movie to the User.
	 * 
	 * @param movie
	 *            movie to output
	 */
	public void showMessage(String movie) {
		System.out.println("New Movie: " + movie + " is now available.");
	}// showMessage

}// TradeClient class