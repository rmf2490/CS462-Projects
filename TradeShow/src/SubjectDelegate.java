import interfaces.CountdownObserver;

/*********************
 * template
 * 
 * @author - Bryan Fearson
 * @version -
 *********************/

public class SubjectDelegate implements Runnable {

	
	private Thread controlThread;
	private String message;
	private CountdownObserver observer;
	
	public SubjectDelegate(String message, CountdownObserver obs){
		controlThread = null;
		this.message = message;
		observer = obs;
		
	}
	public void start() {
		if(controlThread == null){
			controlThread = new Thread(this);
		}
		controlThread.start();
	}//

	public void stop() {

	}//

	public void run() {
		observer.handleTime(message);
	}

}// end class SubjectDelegate