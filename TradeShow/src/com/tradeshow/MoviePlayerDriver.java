package com.tradeshow;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MoviePlayerDriver {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		boolean keepPlaying = true;
		
		BufferedReader in
		   = new BufferedReader(new InputStreamReader(System.in));
		
		CountdownSimulator FightClub = new CountdownSimulator("Fight Club", 139, 0, true, 1);

		CountdownSender outgoing = new CountdownSender("localhost", 12345);
		
		FightClub.addObserver(outgoing);
		
		FightClub.start();
		
		while(keepPlaying){
			String line;
			try {
				line = in.readLine();
				if(line.equals("stop") || line.equals("exit")){
					FightClub.stop();
					keepPlaying = false;
				}
			} catch (IOException e) {
				//Just keep going
			}
		}
	}

}
