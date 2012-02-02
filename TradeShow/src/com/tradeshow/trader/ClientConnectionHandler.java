import java.io.*;
import java.net.*;
import java.util.*;

public class ClientConnectionHandler implements Runnable
{
	private volatile boolean running;
	private BufferedReader	input;
	private PrintWriter output;
	private Socket sock;
	private Thread controlThread;
	private TradeServer tServ;
	
	
	/**
	 * Constructor with input
	 *
	 * @param reqSock	Socket being used 
	 *
	 * @param reqServ Server being used 
	 **/
	 public ClientConnectionHandler(Socket reqSock, TradeServer reqServ) throws
	 																		IOException
	 {
	 	//get appropriate server and socket
		tServ = reqServ;
		sock = reqSock;
		
		//Construct the input and output streams
		input = new BufferedReader( new InputStreamReader(sock.getInputStream()));
		output = new PrintWriter(sock.getOutputStream());
		
	 }//constructor
	public void run()
	{
		String message;
		
		while(running)
		{
			try
			{
				message = input.readLine();
				
			}
			catch (Exception e)
			{
			
			}//catch
		}//while
	}//run
	
	/*
	 * starts the thread of execution
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
	 
	 
	 /*
	 * stops the thread of execution
	 *
	 */
	 public void stop()
	 {
	 running = false;
	 }
}//ClientConnectionHandler