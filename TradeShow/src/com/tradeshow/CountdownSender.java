package com.tradeshow;
import com.tradeshow.interfaces.CountdownObserver;


public class CountdownSender implements CountdownObserver {

	public CountdownSender(){
		
	}
	
	@Override
	public void handleTime(String time) {
		/*OR-2
	    The CountdownSender class
	    must transmit all of the data it is notified about to a
	    particular UDP port on a particular machine on the plane.
	    */
	    

	}

}
