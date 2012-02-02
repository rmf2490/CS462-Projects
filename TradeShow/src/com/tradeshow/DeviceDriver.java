package com.tradeshow;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/***
 * Driver for a device monitoring the status of playing movies.
 * @author Bryan Fearson, Ryan Farrell
 * @version 1.0
 */

/*
 * This work complies with the JMU Honor Code
 */

public class DeviceDriver {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		boolean keepListening = true;
		
		BufferedReader in
		   = new BufferedReader(new InputStreamReader(System.in));
		
		CountdownReceiver incoming = new CountdownReceiver();
		
		CountdownDisplay display = new CountdownDisplay();
		
		incoming.addObserver(display);
		
		incoming.start();
		
		while(keepListening){
			String line;
			try {
				line = in.readLine();
				if(line.equals("stop") || line.equals("exit")){
					incoming.stop();
					keepListening = false;
				}
			} catch (IOException e) {
				//Just keep going
			}
		}

	}

}
