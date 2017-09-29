package home_electricity_agents;
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

	private int vendorReplyCount = 3;
	private int lowestVendorCost = 99999;
	private AID vendorName;

	private int applianceMessageCount = 0;
	
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
			//messageCount++;
			applianceMessageCount++;
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
	//	if (BuySell) //Buy electricity
	//	{
		msg.setContent("B" + Integer.toString(electricity));
	//	}
	//	else //Sell electricity
	//	{
	//		msg.setContent("S" + Integer.toString(electricity));
		//}
		Iterator receivers = msg.getAllIntendedReceiver();
		System.out.println(getLocalName() + ": Sending messages to Vendors : " + msg.getContent());
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

					Integer messageData  = Integer.parseInt(msg.getContent().substring(1));
					switch (msg.getContent().charAt(0))
                    {
                        case 'A': //From an appliance

							applianceMessageCount--;
                            electricity += messageData;
							if (applianceMessageCount <= 0)
							{
								System.out.println("=======================BUYING ELECTRICITY====================");
								System.out.println(getLocalName()+ ": I have to buy " + Integer.toString(electricity) + " electricity");
								buySellElectricity();

							}
                            break;
						case 'I': //From a vendor
							ACLMessage replyBarter = msg.createReply();
							replyBarter.setPerformative(ACLMessage.INFORM);
							replyBarter.setContent("L");
							System.out.println("\t" + getLocalName() + ": Sending response " + replyBarter.getContent() + " to " + msg.getSender().getLocalName());

							send(replyBarter);

							System.out.println("VENDOR REPLY COUNT: " + vendorReplyCount);
							//vendorReplyCount--;

							if (messageData < lowestVendorCost)
							{
								lowestVendorCost = messageData;
								vendorName = msg.getSender();
							}
							if (vendorReplyCount <= 0 )
							{
								//chooseVendor();
								vendorReplyCount = 3;
								lowestVendorCost = 999;
							}
							break;
                        case 'R': //From a vendor
							vendorReplyCount--;
							if (messageData < lowestVendorCost)
							{
								lowestVendorCost = messageData;
								vendorName = msg.getSender();
							}
							if (vendorReplyCount <= 0 )
							{
								chooseVendor();
								vendorReplyCount = 3;
								lowestVendorCost = 999;
							}
                            break;
                        case 'B': //From a vendor

                            break;
                        default:

                            break;
                    }

					//System.out.println(getLocalName()+ ": I have to buy " + Integer.toString(electricity) + " electricity");
					//messageCount--;
					//if (messageCount == 0)
					//{
						//System.out.println("0 MESSAGES");
						//buySellElectricity();
						//chooseVendor();
					//}
					//System.out.println("Message count: " + messageCount);

					//}

					//May have to include try statement here
				}
				else
					block();
				//if (messageCount > 0) //Wait for all messages, not sure if this is necessary
				//	getReply();
			}
		});
	}


	private void chooseVendor()
	{
		System.out.println("-------------------Choosing Vendor------------------");
		ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
		msg.addReceiver(vendorName);
		msg.setContent("Y"+Integer.toString(electricity));
		Iterator receivers = msg.getAllIntendedReceiver();
		while(receivers.hasNext())
		{
			messageCount++;
			System.out.println("\t"+getLocalName() + ": Sending message to " +((AID)receivers.next()).getLocalName());
		}
		send(msg);
		//messageCount++;
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