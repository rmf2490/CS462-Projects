package com.tradeshow;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.concurrent.ArrayBlockingQueue;

import com.tradeshow.interfaces.CountdownObserver;

/***
 * Stores incoming messages in a queue and sends them over the network using UDP to be handled by a CountdownReceiver
 * 
 * @author Bryan Fearson, Ryan Farrell
 *
 */

public class CountdownSender implements Runnable, CountdownObserver {

	private ArrayBlockingQueue<DatagramPacket> queue;
	private InetAddress address;
	private int udpPort;
	private DatagramSocket ds;
	private Thread controlThread;
	
	public CountdownSender(String address, int udpPort){
		try {
			this.address = (InetAddress) Inet4Address.getByName(address);
			this.udpPort = udpPort;
			ds = new DatagramSocket();
		} catch (UnknownHostException e) {
			//This address is bad
		}catch (SocketException e) {
			//Something went wrong
		}
	}
	
	public CountdownSender(String address){
		this(address, 12345);
	}
	
	@Override
	public void handleTime(String time) {
		/*OR-2
	    The CountdownSender class
	    must transmit all of the data it is notified about to a
	    particular UDP port on a particular machine on the plane.
	    */
	    	    
	    try {		
			byte[] data = time.getBytes();
			DatagramPacket dp = new DatagramPacket(data, data.length, address, udpPort);
			ds.send(dp);
		} catch (IOException e) {
			//Something is wrong
		}

	}
	
	public void setAddress(String address){
		try {
			this.address = (Inet4Address) Inet4Address.getByName(address);
		} catch (UnknownHostException e) {
			//Bad address
		}
	}
	
	public void setPort(int udpPort){
		this.udpPort = udpPort;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}
	
	public void stop(){
		
	}

}
