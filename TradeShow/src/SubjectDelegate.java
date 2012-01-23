import java.io.*;
import java.util.*;

/*********************
 * template
 * 
 * @author - Bryan Fearson
 * @version -
 *********************/

public class SubjectDelegate implements Runnable {

	private Thread controlThread;
	public SubjectDelegate(){
		controlThread = null;
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

	}

}// end class SubjectDelegate