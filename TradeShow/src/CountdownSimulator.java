import java.util.*;
import interfaces.*;

/*********************
 * template
 * 
 * @author - Bryan Fearson
 * @version -
 *********************/

public class CountdownSimulator implements Runnable, CountdownSubject {
	private int movieLength;
	private String movieName;
	private boolean realTime;
	private int notifyIncrement;
	//private SubjectDelegate SD;
	private Thread controlThread;
	//private Hashtable<SubjectDelegate, CountdownObserver> observers;
	private ArrayList<CountdownObserver> observers;

	/********************
	 * 
	 * 
	 * @param movie
	 * 			Movie this CountdownSimulator is handling
	 * @param length
	 * 			Length of the movie
	 * @param startTime
	 * 			What time in the movie to start at
	 * @param realTime
	 * 			True if this Simulator runs in real time, false if not
	 * @param notifyIncrement
	 * 			How often to notify the observers.
	 * 				If realTime: The value passed in corresponds to how many minutes to wait (5 = 5 minutes)
	 * 				If !realTime: The value passed in simulates the given number of minutes per second
	 ********************/
	public CountdownSimulator(String movie, int length, int startTime, boolean realTime, int notifyIncrement) {
		movieName = movie;
		movieLength = length - startTime;
		this.realTime = realTime;
		this.notifyIncrement = notifyIncrement;
		observers = new ArrayList<CountdownObserver>();
		

	}// constructor

	public void run() {
		//This is just test code, the code needs to be updated to work as required
		while(movieLength > 0){
			try {
				if(realTime){
					Thread.sleep(notifyIncrement * 60000);
				}
				else{
					Thread.sleep(1000);
				}
			} catch (InterruptedException e) {
				//Just continue
			}
			movieLength = movieLength - notifyIncrement;
			if(movieLength < 0){
				movieLength = 0;
			}
			String message = movieName + "/Time left: " + movieLength;
			//System.out.println(message);
			notifyObservers(message);
		}
	}// run method

	public void addObserver(CountdownObserver observer) {
		observers.add(observer);
	}// addObserver method

	public void notifyObservers(String time) {
		//Change this?
		for(int i=0; i<observers.size(); i++){
			CountdownObserver observer = observers.get(i);
			new SubjectDelegate(time, observer).start();
		}

	}// notifyObservers method

	public void removeObservers(CountdownObserver observer) {
		observers.remove(observer);
	}// removeObservers method
	
	public void start(){
		if(controlThread == null){
			controlThread = new Thread(this);
		}
		controlThread.start();
	}
}// end class CountdownSimulator
