package home_electricity_agents;

import jade.core.Agent;
import java.util.Random;

public class ApplianceAgent extends Agent {
	//How much electricity the appliance consumes
	//And how varied the consumption can be
	private int electConsumption = 0;
	private int consumeRange = 0;
	
	//TODO: add home ID
	
	//Computes the current consumption and returns it as a string
	public String GetConsumption()
	{
		Random theRandom = new Random();
		//Get a number between -consumeRange and consumeRange
		int currentRandomRange = theRandom.nextInt(consumeRange*2) - consumeRange;
		
		//Add it to the current electric consumption rate and turn it into a string
		String theConsumption = Integer.toString(electConsumption + currentRandomRange);
		return theConsumption;
	}
}
