package interfaces;


/*********************
 * template
 * 
 * @author - Bryan Fearson
 * @version -
 *********************/

public interface CountdownSubject {
	public void addObserver(CountdownObserver observer);

	public void notifyObservers(String time);

	public void removeObservers(CountdownObserver observer);

}// end interface

