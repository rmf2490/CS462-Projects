package com.tradeshow.drivers;

import java.io.*;
import com.tradeshow.trader.*;

/**
 * A Driver that can test the Client-side of the TradeShow Program.
 * 
 * @author Bryan Fearson, Ryan Farrell
 * @version 1.0
 */

/*
 * This Work complies with JMU Honor Code
 */

public class ClientDriver {

	public static void main(String[] args) throws IOException {

		TradeClient client;
		client = new TradeClient("localhost", 22801);

	}// main
}// ClientDriver