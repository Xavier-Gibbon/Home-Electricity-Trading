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
	private int electricity = 100; //TEMP TEST
	
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
				System.out.println("\n" + getLocalName() + ": Counter : " + getTickCount());
				sendMessagesCost();
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

	private void sendMessagesCost()
	{
		ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
		msg.setContent("cost");
		msg.addReceiver(new AID("APP1",AID.ISLOCALNAME));
		msg.addReceiver(new AID("APP2",AID.ISLOCALNAME));
		Iterator receivers = msg.getAllIntendedReceiver();
		System.out.println(getLocalName() + ": Sending messages");
		while(receivers.hasNext())
		{
			System.out.println("\t"+getLocalName() + ": Sending message to " +((AID)receivers.next()).getLocalName());
		}
		send(msg);
	}

	public void deactivateCounter()
	{
		System.out.println(getLocalName() + ": I have been asked to stop counting");
		counter.stop(); // stopping the ticker behaviour
	}

	public void getReply()
	{
		addBehaviour(new CyclicBehaviour(this) {
			@Override
			public void action() {
				//Receive the other agents message
				ACLMessage msg=receive();
				if (msg != null)
				{
					System.out.println(getLocalName()+ ": Received message " + msg.getContent() + " from " + msg.getSender().getLocalName());
					//May have to include try statement here
					electricity -= Integer.parseInt(msg.getContent());
					System.out.println(getLocalName()+ ": I have " + Integer.toString(electricity) + " left");
				}
				else
					block();
			}
		});
	}

	@Override
	protected void setup()
	{
		System.out.println(getLocalName() + ": I have been created");
		activateCounter();
		getReply();


	}
}