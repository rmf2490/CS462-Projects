package com.tradeshow.trader;

import java.io.*;
import java.util.*;
import javax.swing.JOptionPane;
import java.net.*;
import com.tradeshow.interfaces.*;

/**
 * Acts as the passengers machine that is watching a movie.
 * 
 * @author Ryan Farrell, Bryan Fearson
 * @version 1.0
 */
public class TradeClient {
	private BufferedReader input;
	private InetAddress ip;
	private int port;
	private PrintWriter output;
	private Socket sock;
	private String host;
	private String movie;

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
		ip = sock.getInetAddress();

		input = new BufferedReader(new InputStreamReader(sock.getInputStream()));
		output = new PrintWriter(sock.getOutputStream());
		output.flush();

		while (sock.isConnected()) {
			String message = input.readLine();
			showMessage(message);
		}

	}// constructor

	/**
	 * Constructor for a TradeClient to be used to keep track of listening clients. Used by TradeServer
	 * 
	 * @param reqHost
	 * 		the host name to connect to
	 * @param reqPort
	 * 		the port to connect client to
	 * @param addr
	 * 		address of the client
	 */
	public TradeClient(String reqHost, int reqPort, InetAddress addr)
			throws IOException, SocketException {
		this.port = reqPort;
		this.host = reqHost;
		this.ip = addr;

		sock = new Socket(host, port);
		output = new PrintWriter(sock.getOutputStream());
		input = new BufferedReader(new InputStreamReader(sock.getInputStream()));

	}// constructor

	/**
	 * outputs the Available Movie to the User.
	 * 
	 * @param the
	 *            movie to output
	 */
	public void showMessage(String movie) {
		System.out.println("New Movie: " + movie + " is now available.");
	}// showMessage

}// TradeClient class