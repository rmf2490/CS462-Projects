package com.tradeshow;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Inet4Address;
import java.net.SocketException;
import java.net.UnknownHostException;

import com.tradeshow.interfaces.CountdownObserver;


public class CountdownSender implements CountdownObserver {

	private Inet4Address address;
	private int udpPort;
	private DatagramSocket ds;
	
	public CountdownSender(String address, int udpPort){
		try {
			this.address = (Inet4Address) Inet4Address.getByName(address);
			this.udpPort = udpPort;
			ds = new DatagramSocket();
		} catch (UnknownHostException e) {
			//This address is bad
		}catch (SocketException e) {
			//Something went wrong
		}
	}
	
	public CountdownSender(){
		
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

}
