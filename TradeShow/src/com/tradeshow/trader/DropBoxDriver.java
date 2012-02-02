package com.tradeshow.trader;

import java.io.*;
import com.tradeshow.interfaces.*;
import com.tradeshow.*;
import com.tradeshow.trader.*;


/**
 * A driver that can be used test the server-side of the
 * TradeShow available movie system
 *
 * @author  Bryan Fearson, Ryan Farrell
 * @version 1.0
 */

/*
 * This work complies with the JMU Honor Code
 */

public class DropBoxDriver
{
    /**
     * The entry point of the application
     *
     * @param args  The command line arguments (which are ignored)
     */
    public static void main(String[] args)
    {
       int                        serverPort;       
       MovieEnterer               enterer;
       TradeServer                dropbox;
       
       

       serverPort = 22801;       
       enterer = new MovieEnterer();

       try
		 {
       
      
          dropbox = new TradeServer(serverPort);
          enterer.addObserver(dropbox);
       }
       catch (Exception ioe)
       {
          ioe.printStackTrace();
       }
       
    }
    
}
