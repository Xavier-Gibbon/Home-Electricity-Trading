package home_electricity_agents;

import jade.core.Agent;

public class HomeAgent extends Agent {
	//Knows how much electricity it has
	private int electricity = 0;
	
	//Knows the range of which the agent wants to be in
	private int maxElect = 0;
	private int minElect = 0;
	
	//Knows the acceptable price range for buying and selling electricity
	//This will be price per energy
	private int acceptableBuyMax = 0;
	private int acceptableBuyMin = 0;
	
	private int acceptableSellMax = 0;
	private int acceptableSellMin = 0;
}