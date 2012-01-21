import java.io.*;
import java.util.*;

public class TradeShowDriver implements Runnable{

	private CountdownDisplay display;
	private CountdownSimulator sim;
	private Thread controlThread;

	public static void main(String[] args) throws IOException {
		CountdownSimulator FightClub = new CountdownSimulator("Fight Club", "139", true);
		CountdownSimulator CitizenKane = new CountdownSimulator("Citizen Kane", "119", true);
		CountdownSimulator PulpFiction = new CountdownSimulator("Pulp Fiction", "154", true);
		CountdownSimulator Avatar = new CountdownSimulator("James Cameron's Avatar", "162", true);
		CountdownSimulator ToyStoryThree = new CountdownSimulator("Toy Story 3", "102", true);
		CountdownDisplay Printer = new CountdownDisplay();
		
		new TradeShowDriver(FightClub, Printer).start();
		new TradeShowDriver(CitizenKane, Printer).start();
		new TradeShowDriver(PulpFiction, Printer).start();
		new TradeShowDriver(Avatar, Printer).start();
		new TradeShowDriver(ToyStoryThree, Printer).start();
		
		//System.out.println("Done creating simulator objects");
	}
	public TradeShowDriver(CountdownSimulator sim, CountdownDisplay display){
		this.sim = sim;
		this.display = display;
		controlThread = new Thread(this);
	}
	public void run(){
		sim.addObserver(display);
		sim.run();
	}
	public void start(){
		controlThread.start();
	}

}// end class