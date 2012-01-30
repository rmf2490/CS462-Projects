package com.tradeshow;
import com.tradeshow.interfaces.CountdownObserver;
import com.tradeshow.interfaces.CountdownSubject;


public class CountdownReceiver implements CountdownSubject {

	public CountdownReceiver(){
		
	}
	
	@Override
	public void addObserver(CountdownObserver observer) {
		// TODO Auto-generated method stub

	}

	@Override
	public void notifyObservers(String time) {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeObservers(CountdownObserver observer) {
		// TODO Auto-generated method stub

	}

}
