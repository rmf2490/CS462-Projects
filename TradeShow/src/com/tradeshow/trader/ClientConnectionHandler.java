package com.tradeshow.trader;

import java.io.*;
import java.net.*;

/***
 * Handles incoming client connections
 * 
 * @author Bryan Fearson, Ryan Farrell
 * @version 1.0
 */


/*
 * This Work complies with JMU Honor Code
 */

public class ClientConnectionHandler implements Runnable {
	private volatile boolean running;
	private PrintWriter output;
	private Socket sock;
	private Thread controlThread;
	private TradeServer tServ;

	/**
	 * Constructor with input
	 * 
	 * @param reqSock
	 *            Socket being used
	 * 
	 * @param reqServ
	 *            Server being used
	 **/
	public ClientConnectionHandler(Socket reqSock, TradeServer reqServ)
			throws IOException {
		// get appropriate server and socket
		sock = reqSock;
		tServ = reqServ;
		// Construct the input and output streams
		//input = new BufferedReader(new InputStreamReader(sock.getInputStream()));
		output = new PrintWriter(sock.getOutputStream());

	}// constructor
	
	public void handleMessage(String message){
		output.println(message);
		output.flush();
	}

	public void run() {
		//String message;

		while (running) {
				try {
					if(controlThread.isInterrupted()){
						running = false;
					}
					else{
						//message = input.readLine();
						//output.println(message);
						//output.flush();
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

		}// while
	}// run

	/*
	 * starts the thread of execution
	 */
	public void start() {
		if (controlThread == null) {
			running = true;
			controlThread = new Thread(this);
			controlThread.start();
		}// if
	}// start

	/*
	 * stops the thread of execution
	 */
	public void stop() {
		if (controlThread != null) {
			running = false;

			controlThread.interrupt();

			controlThread = null;
		}
	}
}// ClientConnectionHandler