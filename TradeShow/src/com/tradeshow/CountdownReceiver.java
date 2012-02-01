package com.tradeshow;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.HashMap;
import java.util.Map;

import com.tradeshow.interfaces.CountdownObserver;
import com.tradeshow.interfaces.CountdownSubject;


public class CountdownReceiver implements CountdownSubject, Runnable {
	
	private boolean keepRunning;
	private DatagramSocket dgs;
	private int udpPort;
	private HashMap<CountdownObserver, SubjectDelegate> observers;
	private Thread controlThread;
	
	public CountdownReceiver(int port){
		udpPort = port;
		observers = new HashMap<CountdownObserver, SubjectDelegate>();
		try {
			dgs = new DatagramSocket(port);
		} catch (SocketException e) {
			//Something wrong, probably incorrect or unavailable port
		}
	}
	
	@Override
	public void addObserver(CountdownObserver observer) {
		observers.put(observer, new SubjectDelegate());
		SubjectDelegate delegate = observers.get(observer);
		delegate.setObserver(observer);

	}

	@Override
	public void notifyObservers(String time) {
		for(Map.Entry<CountdownObserver, SubjectDelegate> entry: observers.entrySet()) {
			SubjectDelegate delegate = entry.getValue();
			delegate.setMessage(time);
			if (delegate.start() == false) {
				delegate.resume();
			}
		}

	}

	@Override
	public void removeObservers(CountdownObserver observer) {
		SubjectDelegate delegate = observers.get(observer);
		delegate.stop();
		observers.remove(observer);

	}
	
	public void run(){
		while(keepRunning){
			try {
				byte[] buffer = new byte[255];
				DatagramPacket in = new DatagramPacket(buffer, buffer.length);
				dgs.receive(in);
				String line = new String(in.getData(), 0, in.getLength());
				notifyObservers(line);
			} catch (IOException e) {
				//receive failed. retry
			}
			
		}
	}
	
	public void start(){
		if (controlThread == null) {
			controlThread = new Thread(this);
			keepRunning = true;
			controlThread.start();
		}
	}
	
	public void stop(){
		if(controlThread != null)
		{
			keepRunning = false;

			controlThread.interrupt();

			controlThread = null;
		}
	}

}
