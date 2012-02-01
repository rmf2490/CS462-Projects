package com.tradeshow;

public class DeviceDriver {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		CountdownReceiver incoming = new CountdownReceiver(12345);
		
		CountdownDisplay display = new CountdownDisplay();
		
		incoming.addObserver(display);
		
		incoming.start();

	}

}
