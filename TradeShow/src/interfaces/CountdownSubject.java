package interfaces;

import java.io.*;
import java.util.*;

/*********************
 * template
 *
 * @author - Bryan Fearson
 * @version - 
 *********************/
 
 public interface CountdownSubject
 {
 public void addObserver(CountdownObserver observer);
  public String notifyObservers(String time);
  public void removeObservers(CountdownObserver observer);


}//end interface
	


