import interfaces.*;

/*********************
 * CountdownDisplay class
 * 
 * @author - Bryan Fearson
 * @version -
 *********************/

public class CountdownDisplay implements CountdownObserver {
	private String message;

	public CountdownDisplay(String message) {
		this.message = message;
	}
	public CountdownDisplay(){
		//No arguments, just pass message directly to the handleTime() method instead
	}

	public void handleTime() {
		System.out.println(this.message);
	}//

	public void handleTime(String message) {
		System.out.println(message);
	}

}// end CountdownDisplay