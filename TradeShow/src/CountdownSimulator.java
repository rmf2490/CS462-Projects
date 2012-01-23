import java.util.*;
import interfaces.*;

/*********************
 * template
 * 
 * @author - Bryan Fearson
 * @version -
 *********************/

public class CountdownSimulator implements Runnable, CountdownSubject {
	private int movieTime;
	private String movieName;
	private boolean realTime;
	//private SubjectDelegate SD;
	private Thread controlThread;
	private Hashtable<SubjectDelegate, CountdownObserver> observers;
	//private ArrayList<CountdownObserver> Observers;

	/********************
	 * 
	 * 
	 * @param movie
	 * 			Movie this CountdownSimulator is handling
	 * @param time
	 * 			Time left in the movie
	 * @param isRealTime
	 * 			True for a real time simulation, false for faster than real time
	 ********************/
	public CountdownSimulator(String movie, int time, boolean isRealTime) {
		movieName = movie;
		movieTime = time;
		realTime = isRealTime;
		observers = new Hashtable<SubjectDelegate, CountdownObserver>();
		

	}// constructor

	public void run() {
		//This is just test code, the code needs to be updated to work as required
		while(movieTime > 0){
			String message = movieName + "/Time left: " + movieTime;
			new CountdownDisplay().handleTime(message);
		}
	}// run method

	public void addObserver(CountdownObserver observer) {
		observers.put(new SubjectDelegate(), observer);
	}// addObserver method

	public void notifyObservers(String time) {
		movieTime = movieTime - 1;

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
