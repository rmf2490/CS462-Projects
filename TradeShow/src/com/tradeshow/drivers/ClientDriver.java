package com.tradeshow.drivers;

import java.io.*;
import com.tradeshow.trader.*;

/**
 * A Driver that can test the Client-side of the TradeShow Program.
 * 
 * @author Ryan Farrell, Bryan Fearson
 * @version 1.0
 */
public class ClientDriver {

	public static void main(String[] args) throws IOException {

		TradeClient client;
		client = new TradeClient("localhost", 22801);

	}// main
}// ClientDriver