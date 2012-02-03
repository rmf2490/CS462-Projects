package com.tradeshow;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.concurrent.ArrayBlockingQueue;

import com.tradeshow.interfaces.CountdownObserver;

/***
 * Stores incoming messages in a queue and sends them over the network using UDP
 * to be handled by a CountdownReceiver
 * 
 * @author Bryan Fearson, Ryan Farrell
 * @version 1.0
 */

/*
 * This work complies with the JMU Honor Code
 */

public class CountdownSender implements Runnable, CountdownObserver {

	private ArrayBlockingQueue<DatagramPacket> queue;
	private volatile boolean keepRunning;
	private DatagramSocket ds;
	private InetAddress address;
	private int udpPort;
	private Thread controlThread;

	/***
	 * Constructor
	 * 
	 * @param address
	 *            address to send packets to
	 * @param udpPort
	 *            port on the target machine to send packets to
	 */
	public CountdownSender(String address, int udpPort) {
		try {
			this.address = (InetAddress) InetAddress.getByName(address);
			this.udpPort = udpPort;
			ds = new DatagramSocket();
			queue = new ArrayBlockingQueue<DatagramPacket>(512);
		} catch (UnknownHostException e) {
			// This address is bad
		} catch (SocketException e) {
			// Something went wrong
		}
	}

	/***
	 * Constructor
	 * 
	 * @param address
	 *            Address to send packets to
	 */

	public CountdownSender(String address) {
		this(address, 12345);
	}

	/***
	 * Default Constructor
	 */
	public CountdownSender() {
		this("localhost", 12345);
	}

	/***
	 * Retrieves a message from the SubjectDelegate and adds it to the queue to
	 * be handled in run()
	 * 
	 * @param time
	 *            The message to be sent
	 */
	@Override
	public void handleTime(String time) {
		/*
		 * OR-2 The CountdownSender class must transmit all of the data it is
		 * notified about to a particular UDP port on a particular machine on
		 * the plane.
		 */

		byte[] data = time.getBytes();
		DatagramPacket dp = new DatagramPacket(data, data.length, address,
				udpPort);
		queue.offer(dp);

	}

	/***
	 * Waits for new DatagramPackets to be added to the queue, and sends them to
	 * the corresponding address/port
	 */

	@Override
	public void run() {
		while (keepRunning) {
			try {
				DatagramPacket outPacket = queue.take();
				ds.send(outPacket);
			} catch (InterruptedException e) {
				// Do nothing, code will drop out of the loop
			} catch (IOException e) {
				// Keep going
			}
		}

	}

	/***
	 * Changes the host to send packets to
	 * 
	 * @param address
	 *            name of the host that will receive packets
	 */

	public void setAddress(String address) {
		try {
			this.address = (InetAddress) InetAddress.getByName(address);
		} catch (UnknownHostException e) {
			// Bad address
		}
	}

	/***
	 * Changes the UDP port this CountdownSender will send to
	 * 
	 * @param udpPort
	 *            An available UDP port
	 */

	public void setPort(int udpPort) {
		this.udpPort = udpPort;
	}

	/***
	 * Starts a new CountdownSender thread
	 * 
	 * WARNING: Only one CountdownSender should send to a specific address/port
	 * combination!
	 */

	public void start() {
		if (controlThread == null) {
			controlThread = new Thread(this);
			keepRunning = true;
			controlThread.start();
		}
	}

	/***
	 * Stops the thread of execution if it is running
	 */

	public void stop() {
		if (controlThread != null) {
			keepRunning = false;

			controlThread.interrupt();

			controlThread = null;
		}
	}

}
