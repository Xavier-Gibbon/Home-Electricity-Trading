package home_electricity_agents;

import jade.core.Agent;
import java.util.Random;

public class VendorAgent extends Agent {
	//Knows how much energy it can sell
	//And how much electricity it gets per time frame
	private int electricity = 0;
	private int electIncrement = 0;
	//Knows the range that it will offer
	//This will be price per energy
	private int acceptableBuyMax = 0;
	private int acceptableBuyMin = 0;
	
	private int acceptableSellMax = 0;
	private int acceptableSellMin = 0;
	
	//Increments the electricity
	private void IncrementElectricity()
	{
		electricity += electIncrement;
	}
	
	//Creates an offer, based on the given minimum and maximum values
	public String GetOffer(int min, int max)
	{
		int difference = max - min;
		Random theRandom = new Random();
		
		String offer = Integer.toString(min + theRandom.nextInt(difference));
		
		return offer;
	}
}
