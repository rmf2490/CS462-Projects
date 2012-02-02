package com.tradeshow.drivers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import com.tradeshow.*;

/***
 * Driver for a movie playing device that transmits information
 * to DeviceDrivers over a network
 * 
 * @author Bryan Fearson, Ryan Farrell
 * @version 1.0
 */

/*
 * This work complies with the JMU Honor Code
 */

public class MoviePlayerDriver {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		boolean keepPlaying = true;
		
		BufferedReader in
		   = new BufferedReader(new InputStreamReader(System.in));
		
		CountdownSimulator FightClub = new CountdownSimulator("Fight Club", 139, 0, false, 10);
		CountdownSimulator PulpFiction = new CountdownSimulator("Pulp Fiction", 154, 45, false, 7);
		CountdownSimulator ToyStoryThree = new CountdownSimulator("Toy Story 3", 102, 0, false, 5);

		CountdownSender outgoing = new CountdownSender();
		
		FightClub.addObserver(outgoing);
		PulpFiction.addObserver(outgoing);
		ToyStoryThree.addObserver(outgoing);
		
		outgoing.start();
		
		FightClub.start();
		PulpFiction.start();
		ToyStoryThree.start();
		
		while(keepPlaying){
			String line;
			try {
				line = in.readLine();
				if(line.equals("stop") || line.equals("exit")){
					FightClub.stop();
					PulpFiction.stop();
					ToyStoryThree.stop();
					outgoing.stop(); //Any better way to stop this?
					keepPlaying = false;
				}
			} catch (IOException e) {
				//Just keep going
			}
		}
	}

}
