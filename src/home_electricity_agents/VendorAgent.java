package home_electricity_agents;

import jade.core.Agent;

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
}
