import interfaces.CountdownObserver;

/*********************
 * Passes on a message to be sent to a CountdownObserver in it's own thread of execution
 * 
 * @author - Bryan Fearson, Ryan Farrell
 * @version - 1.0
 *********************/

/*
 * This work complies with the JMU Honor Code
 */

public class SubjectDelegate implements Runnable {

	
	private Thread controlThread;
	private String message;
	private CountdownObserver observer;
	private boolean interrupt;
	
	public SubjectDelegate(String message, CountdownObserver obs){
		controlThread = null;
		this.message = message;
		observer = obs;
		
	}
	public void run() {
		observer.handleTime(message);
	}
	
	public void start() {
		if(controlThread == null){
			controlThread = new Thread(this);
		}
		controlThread.start();
	}//

	public void stop() {
		interrupt = true;
	}//

	

}// end class SubjectDelegate