import java.io.*;
import java.util.*;
import interfaces.*;

/*********************
 * CountdownDisplay class
 *
 * @author - Bryan Fearson
 * @version - 
 *********************/
 
 public class CountdownDisplay implements CountdownObserver
 {
 	
	public CountdownDisplay()
	{
	
	}
	public void handleTime( String time)
	{
	System.out.println(time);
	}//
	


}//end CountdownDisplay