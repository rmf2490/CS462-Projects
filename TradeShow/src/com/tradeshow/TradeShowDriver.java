package com.tradeshow;
import java.io.*;

/*********************
 * Driver for PA1. Tests all the classes and their functionality
 * 
 * @author - Bryan Fearson, Ryan Farrell
 * @version - 1.0
 *********************/

/*
 * This work complies with the JMU Honor Code
 */

public class TradeShowDriver{

	public static void main(String[] args) throws IOException {
		//CountdownSimulator FightClub = new CountdownSimulator("Fight Club", 139, 0, false, 1);
		//CountdownSimulator CitizenKane = new CountdownSimulator("Citizen Kane", 119, 9, false, 2);
		//CountdownSimulator PulpFiction = new CountdownSimulator("Pulp Fiction", 154, 45, false, 5);
		//CountdownSimulator Avatar = new CountdownSimulator("James Cameron's Avatar", 162, 100, false, 10);
		CountdownSimulator ToyStoryThree = new CountdownSimulator("Toy Story 3", 102, 0, false, 15);
		CountdownDisplay Printer = new CountdownDisplay();
		
		/*FightClub.addObserver(Printer);
		CitizenKane.addObserver(Printer);
		PulpFiction.addObserver(Printer);
		Avatar.addObserver(Printer);*/
		ToyStoryThree.addObserver(Printer);
		
		/*FightClub.start();
		CitizenKane.start();
		PulpFiction.start();
		Avatar.start();*/
		ToyStoryThree.start();
	}

}