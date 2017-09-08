package home_electricity_agents;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.core.behaviours.TickerBehaviour;
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

	private TickerBehaviour counter;

	public void activateCounter()
	{
		System.out.println(getLocalName() + ": I have been asked to start counting");
		counter = new TickerBehaviour(this, 500) {
			public void onStart()
			{
				super.onStart();
				System.out.println(getLocalName()+ ": Start counting");
			}
			@Override
			protected void onTick()
			{
				System.out.println(getLocalName() + ": Counter : " + getTickCount());
				ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
				msg.setContent("Send");
				msg.addReceiver(new AID("APP",AID.ISLOCALNAME));
				Iterator receivers = msg.getAllIntendedReceiver();
				System.out.println(getLocalName() + ": Sending messages");
				while(receivers.hasNext())
				{
					System.out.println(getLocalName() + ": Sending message to " +((AID)receivers.next()).getLocalName());
				}
				send(msg);
				if (getTickCount() >= 5)
					deactivateCounter();
			}
			public int onEnd() {
				System.out.println(getLocalName() + ": Stop counting");
				return super.onEnd();
			}
		};
		addBehaviour(counter);
	}

	public void deactivateCounter()
	{
		System.out.println(getLocalName() + ": I have been asked to stop counting");
		counter.stop(); // stopping the ticker behaviour
	}

	@Override
	protected void setup()
	{
		System.out.println(getLocalName() + ": I have been created");
		activateCounter();
		


	}
}