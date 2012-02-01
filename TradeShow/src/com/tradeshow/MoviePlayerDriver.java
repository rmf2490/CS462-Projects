package com.tradeshow;

public class MoviePlayerDriver {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		CountdownSimulator FightClub = new CountdownSimulator("Fight Club", 139, 0, false, 5);

		CountdownSender outgoing = new CountdownSender("localhost", 12345);
		
		FightClub.addObserver(outgoing);
		
		FightClub.start();
	}

}
