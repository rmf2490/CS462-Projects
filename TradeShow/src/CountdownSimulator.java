import java.io.*;
import java.util.*;
import interfaces.*;

/*********************
 * template
 *
 * @author - Bryan Fearson
 * @version - 
 *********************/
 
 public class CountdownSimulator implements interfaces.Runnable, CountdownSubject
 {
   private String movieTime;
	private String movieName;
	private SubjectDelegate SD;
	ArrayList<CountdownObserver> Observers;
	/********************
	 *
	 *
	 *@param args command line arguments - unused
	 ********************/
   public CountdownSimulator(String movie, String time)
	{
	movieName = movie;
	movieTime = time;
	Observers = new ArrayList<CountdownObserver>();
	
	}//constructor
	
	public void run()
	{
   
   }//run method


  public void addObserver(CountdownObserver observer)
  {
  Observers.add(observer);
  }//addObserver method
  
  public String notifyObservers(String time)
  {
	 movieTime = Integer.toString(Integer.parseInt(time) - 1);
	 
	 return movieTime;
	 
	   
  }//notifyObservers method
  
  public void removeObservers(CountdownObserver observer)
  {
	 Observers.remove(observer);
  }//removeObservers method

}//end class CountdownSimulator
