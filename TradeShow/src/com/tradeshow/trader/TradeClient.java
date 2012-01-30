package com.tradeshow.trader;

import java.io.*;
import java.util.*;
import java.net.*;


public class TradeClient 
{
	private BufferedReader input;
	private int port;
	private PrintWriter output;
	private Socket sock;
	private String host;
	
	
	public TradeClient(String reqHost, int reqPort)
	{
			this.port = reqPort;
			this.host = reqHost;
		
	}//constructor
	
	public void run()
	{
   	try
			{
			sock = new Socket(host, port);	
			input = new BufferedReader(new InputStreamReader(sock.getInputStream()));
			output = new PrintWriter(sock.getOutputStream());
			}
			catch(Exception e)
			{
			
			}
	}//run

}//TradeClient class