package home_electricity_agents;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;

import java.util.Iterator;

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
	
	@Override
	protected void setup()
	{
		addBehaviour(new CyclicBehaviour()
		{
			@Override
			public void action()
			{
				//Send a message to the other agent

			}
		});

		ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
		msg.setContent("Send");
		msg.addReceiver(new AID("APP",AID.ISLOCALNAME));
		Iterator receivers = msg.getAllIntendedReceiver();
		System.out.println("Sending message");
		while(receivers.hasNext())
		{
			System.out.println("Sending to : " +((AID)receivers.next()).getLocalName());
		}
		send(msg);
	}
}