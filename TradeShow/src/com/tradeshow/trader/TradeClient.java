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
 * @author  Ryan Farrell, Bryan Fearson
 * @version 1.0
 */
public class TradeClient 
{
	private BufferedReader input;
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
			run();
	}//constructor
	
	
	/**
	 * reads the input from the Server
	 *
	 */
	private String getInput()
	{
	String message;
	
	message = null;
	try
	{
	message = input.readLine();
	}
	catch (IOException ioe)
	{
	//do nothing
	}
	return message;
	}//getInput
	
	
	/**
	 * runs the TradeClient
	 *
	 */
	public void run()
	{
		boolean go = true;
		String availMovie;
  		while(go)
		{ 	
		try
			{
			sock = new Socket(host, port);	
			input = new BufferedReader(new InputStreamReader(sock.getInputStream()));
			output = new PrintWriter(sock.getOutputStream());
			availMovie = getInput();
			showMessage(availMovie);
			}
			catch(Exception e)
			{
			//wait until there is some input
			}
		}//while	
	}//run
	
	
	/**
	 * outputs the Available Movie to the User.
	 *
	 * @param the movie to output
	 */
	public void showMessage(String movie)
	{
		JOptionPane.showMessageDialog(null, 
									"New Movie: " + movie + " is now available.");
	}//showMessage
	
	
}//TradeClient class