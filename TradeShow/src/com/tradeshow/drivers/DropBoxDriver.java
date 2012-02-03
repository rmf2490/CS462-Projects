package com.tradeshow.drivers;

import com.tradeshow.trader.*;

/**
 * A driver that can be used test the server-side of the TradeShow available
 * movie system
 * 
 * @author Prof. David Bernstein, James Madison University
 * @version 1.0
 */
public class DropBoxDriver {
	/**
	 * The entry point of the application
	 * 
	 * @param args
	 *            The command line arguments (which are ignored)
	 */
	public static void main(String[] args) {
		int serverPort;
		MovieEnterer enterer;
		TradeServer dropbox;

		serverPort = 22801;
		enterer = new MovieEnterer();

		dropbox = new TradeServer(serverPort);
		enterer.addObserver(dropbox);
		dropbox.start();

	}

}
