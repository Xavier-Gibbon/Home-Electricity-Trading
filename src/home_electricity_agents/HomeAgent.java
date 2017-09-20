package home_electricity_agents;

import com.sun.org.apache.xpath.internal.operations.Bool;
import jade.Boot;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.TickerBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;

import java.util.Iterator;

public class HomeAgent extends Agent {
	//Knows how much electricity it has
	private int electricity = 0;
	private int messageCount = 0;
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

	public void activateCounter() //Activate counter, on each tick of the counter the home will send a message to all of the appliances
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

	private void sendMessagesCost() //Send the ping to the appliances, asking them for their cost
	{
		ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
		msg.setContent("cost");		
		DFAgentDescription[] serviceAgents = getTarget("Appliance");
		for (DFAgentDescription serviceAgent : serviceAgents) {
			msg.addReceiver(serviceAgent.getName());
		}
		Iterator receivers = msg.getAllIntendedReceiver();
		System.out.println(getLocalName() + ": Sending messages");
		while(receivers.hasNext())
		{
			messageCount++;
			System.out.println("\t"+getLocalName() + ": Sending message to " +((AID)receivers.next()).getLocalName());
		}
		send(msg);
	}
	
	private DFAgentDescription[] getTarget(String service)
	{
		DFAgentDescription dfd = new DFAgentDescription();
		ServiceDescription sd = new ServiceDescription();
		sd.setType(service);
		dfd.addServices(sd);
		try {
			DFAgentDescription[] result = DFService.search(this, dfd);
			return result;
		} catch (Exception fe) {
		}
		return null;
	}

	private void sendVendorMessage(Boolean BuySell) //Send the message to the vendors
	{
		ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
		DFAgentDescription[] serviceAgents = getTarget("Vendor");
		for (DFAgentDescription serviceAgent : serviceAgents) {
			msg.addReceiver(serviceAgent.getName());
		}
		if (BuySell) //Buy electricity
		{
			msg.setContent("B" + Integer.toString(electricity));
		}
		else //Sell electricity
		{
			msg.setContent("S" + Integer.toString(electricity));
		}
		Iterator receivers = msg.getAllIntendedReceiver();
		System.out.println(getLocalName() + ": Sending messages to Vendors");
		while(receivers.hasNext())
		{
			messageCount++;
			System.out.println("\t"+getLocalName() + ": Sending message to " +((AID)receivers.next()).getLocalName());
		}
		send(msg);
	}

	public void deactivateCounter()// Stop the counter
	{
		System.out.println(getLocalName() + ": I have been asked to stop counting");
		counter.stop(); // stopping the ticker behaviour
	}

	public void getReply() //Wait for replies from the vendors/appliances
	{
		addBehaviour(new CyclicBehaviour(this) {
			@Override
			public void action() {
				//Receive the other agents message
				ACLMessage msg=receive();
				if (msg != null)
				{
					System.out.println(getLocalName()+ ": Received message " + msg.getContent() + " from " + msg.getSender().getLocalName());
					//msg.getSender().get
					//{

					//}
					/*=======================================================================
						TODO: Find out way to find out where a message came from without parsing the text as I've done
						TODO: This may require finding the type of the sender or something along those lines
					 ========================================================================*/
					/*if(msg.getContent().contains("S")) //Vendor Selling Reply
					{

					}
					else if(msg.getContent().contains("C")) //Appliance Reply
					{*/
					electricity -= Integer.parseInt(msg.getContent());
					System.out.println(getLocalName()+ ": I have to buy " + Integer.toString(electricity) + " electricity");
					if (messageCount == 0)
						buySellElectricity();
					//}
					messageCount--;
					//May have to include try statement here
				}
				else
					block();
				//if (messageCount > 0) //Wait for all messages, not sure if this is necessary
				//	getReply();
			}
		});
	}

	private void buySellElectricity() //Buy or sell electricity based on the amount of electricity we require
	{
		if (electricity > 0) //Buy electricity
		{
			sendVendorMessage(Boolean.TRUE);
		}
		else if (electricity < 0) //Sell electricity
		{
			sendVendorMessage(Boolean.FALSE);
		}

	}

	@Override
	protected void setup() //Start the agent
	{
		System.out.println(getLocalName() + ": I have been created");
		activateCounter();
		getReply();
	}
}