package com.tradeshow.trader;

import java.io.*;
import java.util.*;
import java.net.*;
import javax.swing.*;
import java.awt.event.*;
import com.tradeshow.interfaces.*;

/**
 * A Server responsible for acting as the "dropbox" of the movies
 * available.
 *
 * @author  Ryan Farrell, Bryan Fearson
 * @version 1.0
 */
public class TradeServer implements TradeObserver, Runnable
{
	private volatile boolean running;
   private BufferedReader input;
	private int port;
	private PrintWriter output;
	private ServerSocket sSocket;
	private Socket sock;
	private Thread controlThread;
	private TradeClient client;
	//****using hashTable for now because need to cycle through
	//		the available clients.
	private Hashtable<TradeClient, TradeClient> clientList;
	
	
	/**
	 * Constructor
	 *
	 * @param	the serverPort used
	 */
	public TradeServer(int serverPort)
	{
	 this.port = serverPort;
	 running = true;
	 clientList = new Hashtable<TradeClient, TradeClient>();
	}//constructor
	
	
	/**
	 * Handles the Available Movies presentation 
	 * to all clients on server.
	 *
	 */ 
	public synchronized void handleAvailableMovie(String movie)
	{
	
		Enumeration<TradeClient> tce;
		TradeClient current;
		tce = clientList.elements();
		while (tce.hasMoreElements())
		{
		current = tce.nextElement();
		current.showMessage(movie);	
		}//while	
	
	}//handleAvailableMovie class
	
	
	/**
	 *  The entry point for the new thread of execution.
	 *
	 **/
	
	public void run()
	{
	   	  
		System.out.println(sSocket);
		if(setUpConnection())
		{
		System.out.println(sSocket);
		while (running)
		{
			try
			{
			sock = sSocket.accept();
			input = new BufferedReader(
				new InputStreamReader(sock.getInputStream()));
			output = new PrintWriter(sock.getOutputStream());
			output.flush();
			
			/*Needs work for handling multiple clients
			
			//Create new client and add to clientList
			client = new TradeClient("localhost", 22801);
			clientList.put(client,client);
			System.out.println("try block of run");
			
			*/
			
			}//try
			catch (Exception e)
			{
		   System.out.println("catch block of run");  
			//socket timeout, go back to try block
			}//catch
		
		}//while
		
		
		//close open sockets when finished
		try
		{
		sock.close();
		sSocket.close();
		}
		catch(IOException ioe)
		{
		//do nothing
		}	
		}//if
		else
		{
		JOptionPane.showMessageDialog(null, 
									"No Connection was Made");;
		}//else
		
	}//run
	
	/**
	 * establishes a TCP Connection
	 *
	 */
	public boolean setUpConnection()
	{	
		boolean connected = false;
		try
		{
		sSocket = new ServerSocket(port);
		sSocket.setSoTimeout(5000);
		connected = true;
		}//try
		catch(IOException ioe)
		{
		ioe.printStackTrace();		//if no connection can be made
		System.exit(1);				//something is wrong. Terminate.
		}//catch
		
		return connected;
	}//setUpConnection
	
	/**
	 * start the thread of execution
	 *
	 */	
	public void start()
	{
		if (controlThread == null)
		{
		   running = true;
			controlThread = new Thread(this);
			controlThread.start();
		}//if
	}//start
	
	/**
	 * stop the thread of execution
	 *
	 */
	public void stop()
	{
	running = false;
	}//stop

}//class TradeServer