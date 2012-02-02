package com.tradeshow.trader;

import java.io.*;
import java.util.*;
import javax.swing.JOptionPane;
import java.net.*;
import com.tradeshow.interfaces.*;

/******************NOT SURE IF TRADECLIENT NEEDS TO BE IN OWN THREAD*****************/
/******************CURRENTLY NOT SET UP THAT WAY************************************/

/**
 * Acts as the passengers machine that is watching a movie.
 *
 * @author  Bryan Fearson, Ryan Farrell
 * @version 1.0
 */

/*
 * This work complies with the JMU Honor Code
 */

public class TradeClient 
{
	private BufferedReader input;
	private InetAddress ip;
	private int port;
	private PrintWriter output;
	private Socket sock;
	private String host;
	private String movie;
	
	/**
	 * Constructor
	 *
	 * @param reqHost		the host name to connect to
	 * 
	 * @param reqPort		the port to connect client to
	 */
	public TradeClient(String reqHost, int reqPort)
	{
			this.port = reqPort;
			this.host = reqHost;
			
			try
			{
			sock = new Socket(host, port);
			//System.out.println(sock);
			ip = sock.getInetAddress();	
			input = new BufferedReader(new InputStreamReader(sock.getInputStream()));
			output = new PrintWriter(sock.getOutputStream());
			}
			catch(Exception e)
			{
			//wait until there is some input
			}
			
	}//constructor
	
	/**
	 * Constructor
	 *
	 * @param reqHost		the host name to connect to
	 * 
	 * @param reqPort		the port to connect client to
	 */
	public TradeClient(String reqHost, int reqPort, InetAddress addr)
	{
			this.port = reqPort;
			this.host = reqHost;
			this.ip = addr;
			
			try
			{
			sock = new Socket(host, port);
			//System.out.println(sock);	
			input = new BufferedReader(new InputStreamReader(sock.getInputStream()));
			output = new PrintWriter(sock.getOutputStream());
			}
			catch(Exception e)
			{
			//wait until there is some input
			}
			
	}//constructor
	
	
	/**
	 * outputs the Available Movie to the User.
	 *
	 * @param the movie to output
	 */
	public void showMessage(String movie)
	{
			System.out.println("New Movie: " + movie + " is now available.");
	}//showMessage
	
	
}//TradeClient class