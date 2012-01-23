import java.io.*;
import java.util.*;

public class TradeShowDriver{

	private CountdownDisplay display;
	private CountdownSimulator sim;

	public static void main(String[] args) throws IOException {
		CountdownSimulator FightClub = new CountdownSimulator("Fight Club", "139", true);
		CountdownSimulator CitizenKane = new CountdownSimulator("Citizen Kane", "119", true);
		CountdownSimulator PulpFiction = new CountdownSimulator("Pulp Fiction", "154", true);
		CountdownSimulator Avatar = new CountdownSimulator("James Cameron's Avatar", "162", true);
		CountdownSimulator ToyStoryThree = new CountdownSimulator("Toy Story 3", "102", true);
		CountdownDisplay Printer = new CountdownDisplay();
		
		FightClub.addObserver(Printer);
		CitizenKane.addObserver(Printer);
		PulpFiction.addObserver(Printer);
		Avatar.addObserver(Printer);
		ToyStoryThree.addObserver(Printer);
		
		FightClub.start();
		CitizenKane.start();
		PulpFiction.start();
		Avatar.start();
		ToyStoryThree.start();
	}

}// end class