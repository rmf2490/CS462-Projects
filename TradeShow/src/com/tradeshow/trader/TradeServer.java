package com.tradeshow.trader;

import java.io.*;
import java.util.*;
import java.net.*;
import javax.swing.*;

import com.tradeshow.interfaces.TradeObserver;

import java.awt.event.*;


public class TradeServer implements TradeObserver, Runnable
{
	private volatile boolean running;
   private BufferedReader input;
	private int port;
	private PrintWriter output;
	private ServerSocket sSocket;
	private Socket sock;
	private Thread controlThread;
	private HashMap<TradeClient, TradeClient> clientList;
	
	public TradeServer(int serverPort)
	{
	 this.port = serverPort;
	 running = true;
	}//constructor
	
	
	public synchronized void handleAvailableMovie(String movie)
	{
	run();
	JOptionPane.showMessageDialog(null, 
									"New Movie: " + movie + " is now available.");
	/*********WORK IN PROGRESS CODE************
	Enumeration<TradeClient> tce;
	tce = clientList.elements();
	while (tce.hasMoreElements())
	{
		JOptionPane.showMessageDialog(null, 
									"New Movie: " + movie + " is now available.");
	}//while	
	/****************END OF WIPC****************/
	
	}//handleAvailableMovie class
	
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
		ioe.printStackTrace();
		System.exit(1);
		}//catch
		
		return connected;
	}//setUpConnection
	
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
			clientList = new HashMap<TradeClient, TradeClient>();
			sock = sSocket.accept();
			input = new BufferedReader(
				new InputStreamReader(sock.getInputStream()));
			output = new PrintWriter(sock.getOutputStream());
			System.out.println("try block of run");
			}//try
			catch (Exception e)
			{
		   System.out.println("catch block of run");  
			//socket timeout, go back to try block
			}//catch
		
		}//while
		}//if
		else
		{
		JOptionPane.showMessageDialog(null, 
									"No Connection was Made");;
		
		}//else
		System.out.println("end of run");
	}//run
	public void start()
	{
		if (controlThread == null)
		{
		   running = true;
			controlThread = new Thread(this);
			controlThread.start();
		}//if
	}//start
	public void stop()
	{
	running = false;
	}//stop

}//class TradeServer