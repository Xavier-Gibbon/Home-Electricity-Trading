package home_electricity_agents;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.TickerBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;

import java.util.Iterator;

import gui_application.MiddleMan;
import gui_application.MainMenu;

public class HomeAgent extends Agent {
	//Knows how much electricity it has
	private int electricity = 0;
	//Knows the range of which the agent wants to be in
	private int maxElect = 0;
	private int minElect = 0;

	private int vendorReplyCount = 0;
	private int initalVendorReplyCount = 0;
	private int lowestVendorCost = 99999;
	private int highestVendorCost = 0;
	private AID vendorName;
	private int money = 50000;
	private int applianceMessageCount = 0;
	
	//Knows the acceptable price range for buying and selling electricity
	//This will be price per energy
	private int acceptableBuyMax = 0;
	private int acceptableBuyMin = 0;
	
	private int acceptableSellMax = 0;
	private int acceptableSellMin = 50;

	private TickerBehaviour counter;

	public void activateCounter() //Activate counter, on each tick of the counter the home will send a message to all of the appliances
	{
		MiddleMan.SendMessageToMenu(getLocalName() + ": I have been asked to start counting");
		counter = new TickerBehaviour(this, 500) {
			public void onStart()
			{
				super.onStart();
				MiddleMan.SendMessageToMenu(getLocalName()+ ": Start counting");
			}
			@Override
			protected void onTick()
			{
				MiddleMan.SendMessageToMenu("\n" + getLocalName() + ": Counter : " + getTickCount());
				sendMessagesCost();

				if (getTickCount() >= 5)
					deactivateCounter();
			}
			public int onEnd() {
				MiddleMan.SendMessageToMenu(getLocalName() + ": Stop counting");
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
		for (DFAgentDescription serviceAgent : serviceAgents) { //For every agent that is type "Appliance"
			msg.addReceiver(serviceAgent.getName());
		}
		Iterator receivers = msg.getAllIntendedReceiver();
		MiddleMan.SendMessageToMenu(getLocalName() + ": Sending messages");
		while(receivers.hasNext()) //Send message to all appliance agents
		{
			applianceMessageCount++;
			MiddleMan.SendMessageToMenu("\t"+getLocalName() + ": Sending message to " +((AID)receivers.next()).getLocalName());
		}
		send(msg);
	}
	
	private void sendMessagesOnOff() //Send the ping to the appliances, toggling "on" state
	{
		ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
		msg.setContent("toggle");		
		DFAgentDescription[] serviceAgents = getTarget("Appliance");
		for (DFAgentDescription serviceAgent : serviceAgents) {
			msg.addReceiver(serviceAgent.getName());
		}
		Iterator receivers = msg.getAllIntendedReceiver();
		System.out.println(getLocalName() + ": Sending messages");
		while(receivers.hasNext())
		{
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

	private void sendVendorMessage(String message) //Send the message to the vendors
	{
		ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
		DFAgentDescription[] serviceAgents = getTarget("Vendor");
		for (DFAgentDescription serviceAgent : serviceAgents) {
			msg.addReceiver(serviceAgent.getName());
		}
		if (message.equals("L") || message.equals("N")) //If we need to ask the vendors all for a lower price
		{
			msg.setContent(message);
		}
		else //Else we need to ask them how much we want to buy
		{
			msg.setContent(message + Integer.toString(electricity));
		}
		Iterator receivers = msg.getAllIntendedReceiver();
		MiddleMan.SendMessageToMenu(getLocalName() + ": Sending messages to Vendors : " + msg.getContent());
		while(receivers.hasNext())
		{
			//messageCount++;
			if (message.charAt(0) == 'L' || message.charAt(0) == 'N') //If we are asking for a lower price, this counts how many vendors there are and how many final offers we need to wait for
			{
				vendorReplyCount++;
			}
			else //If we are sending an inital asking price, we need to wait for however many offers before we can do anything else
			{
				initalVendorReplyCount++;
			}
			MiddleMan.SendMessageToMenu("\t"+getLocalName() + ": Sending message to " +((AID)receivers.next()).getLocalName());
		}
		send(msg);
	}

	public void deactivateCounter()// Stop the counter
	{
		MiddleMan.SendMessageToMenu(getLocalName() + ": I have been asked to stop counting");
		counter.stop(); // stopping the ticker behaviour
	}

	public void getReply() //Wait for replies from the vendors/appliances
	{
		addBehaviour(new CyclicBehaviour(this) {
			@Override
			public void action() {
				//Receive the other agents message
				ACLMessage msg=receive();
				if (msg != null) //If the message is not null
				{
					MiddleMan.SendMessageToMenu(getLocalName()+ ": Received message " + msg.getContent() + " from " + msg.getSender().getLocalName());
					Integer messageData  = Integer.parseInt(msg.getContent().substring(1)); //Get the data from the message this is to get the string from the first character onwards
					switch (msg.getContent().charAt(0)) //Depending on what the first character is in the message it has a different meaning
                    {
                        case 'A': //From an appliance telling us its cost of electricity
							applianceMessageCount--; //Tick down until we have had all appliances reply to us
                            electricity += messageData;
							if (applianceMessageCount <= 0) //If all appliances have replied to us then we can start buying electricity
							{
								if (electricity > 0)
								{
									MiddleMan.SendMessageToMenu("=======================SELLING ELECTRICITY====================");
									MiddleMan.SendMessageToMenu(getLocalName()+ ": I have to sell " + Integer.toString(electricity) + " electricity");
								}
								else
								{
									MiddleMan.SendMessageToMenu("=======================BUYING ELECTRICITY====================");
									MiddleMan.SendMessageToMenu(getLocalName()+ ": I have to buy " + Integer.toString(electricity * -1) + " electricity");
								}
								buySellElectricity();
							}
                            break;
						case 'I': //From a vendor telling us its initial offer price of electricity
							initalVendorReplyCount--;//A vendor has replied
							if (messageData < lowestVendorCost) //Use this to find the lowest cost vendor of the lot
							{
								lowestVendorCost = messageData; //Store their price
								vendorName = msg.getSender(); //Store who they are
							}
							if (initalVendorReplyCount <= 0 ) //If all vendors have replied
							{
								if (lowestVendorCost > acceptableBuyMax) //If the lowest offer from the vendors is not smaller than our maximum buy price then we ask for a lower price from all vendors
								{
									sendVendorMessage("L"); //Get lower price from everyone
								}
								else
								{
									chooseVendor();//If there is an acceptable price we will take that offer
								}
							}
							break;
                        case 'R': //From a vendor bartering with us
							ACLMessage replyBuy = msg.createReply();
							replyBuy.setPerformative(ACLMessage.INFORM);
							replyBuy.setContent("L");
							MiddleMan.SendMessageToMenu("\t" + getLocalName() + ": Sending response " + replyBuy.getContent() + " to " + msg.getSender().getLocalName());
							send(replyBuy); //We send back a message instantly again until the vendor will not barter anymore, we are waiting for its final offer
                            break;
                        case 'M':
                        	ACLMessage replySell = msg.createReply();
                        	replySell.setPerformative(ACLMessage.INFORM);
                        	replySell.setContent("N");
							MiddleMan.SendMessageToMenu("\t" + getLocalName() + ": Sending response " + replySell.getContent() + " to " + msg.getSender().getLocalName());
							send(replySell); //We send back a message instantly again until the vendor will not barter anymore, we are waiting for its final offer
                        	break;                
                        case 'F': //Final offer from a vendor
							vendorReplyCount--; //A vendor has sent us its final offer, we need to get all of them before we can choose a vendor to buy from
							if (messageData < lowestVendorCost) //Store the lowest cost and vendor
							{
								lowestVendorCost = messageData;
								vendorName = msg.getSender();
							}
							if (vendorReplyCount <= 0 ) //If all vendors have replied to us we can then choose the vendor we need to buy from
							{
								chooseVendor(); //Choose the vendor
								vendorReplyCount = 0; //Reset variable
								initalVendorReplyCount = 0; //Reset variable
								lowestVendorCost = 999; //Reset variable
							}
                            break;
                        case 'P':
                        	vendorReplyCount--; //A vendor has sent us its final offer, we need to get all of them before we can choose a vendor to buy from
                        	if (messageData > highestVendorCost) //Use this to find the lowest cost vendor of the lot
							{
								highestVendorCost = messageData; //Store their price
								vendorName = msg.getSender(); //Store who they are
							}
							if (vendorReplyCount <= 0 ) //If all vendors have replied to us we can then choose the vendor we need to buy from
							{
								chooseVendor(); //Choose the vendor
								vendorReplyCount = 0; //Reset variable
								initalVendorReplyCount = 0; //Reset variable
								lowestVendorCost = 999; //Reset variable
							}
                        	break;
						case 'C': //Cost of the electricity from the vendor, think of this as the bill
							if (messageData < 0)
							{
								MiddleMan.SendMessageToMenu("We made $" + messageData * -1 + " this week from excess electricity");
							}
							else
							{
								MiddleMan.SendMessageToMenu("This weeks cost of electricity is $" + messageData);
							}
							
							money -= messageData; //The cost of the electricity is taken out of our money
							electricity = 0; //Our electricity has been reset back to 0 because we have sold/bought enough to make it 0
							break;
						case 'G':
							initalVendorReplyCount--;//A vendor has replied
							if (messageData > highestVendorCost) //Use this to find the lowest cost vendor of the lot
							{
								highestVendorCost = messageData; //Store their price
								vendorName = msg.getSender(); //Store who they are
							}
							if (initalVendorReplyCount <= 0 ) //If all vendors have replied
							{
								
								if (highestVendorCost < acceptableSellMin) //If the lowest offer from the vendors is not smaller than our maximum buy price then we ask for a lower price from all vendors
								{
									sendVendorMessage("N"); //Get lower price from everyone
								}
								else
								{
									chooseVendor();//If there is an acceptable price we will take that offer
								}
							}
							break;
                        default:
                            break;
                    }
				}
				else
					block();
			}
		});
	}

	private void chooseVendor() //If we have an acceptable price we will choose the vendor we need to buy from
	{
		MiddleMan.SendMessageToMenu("-------------------Choosing Vendor------------------");
		ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
		msg.addReceiver(vendorName);
		msg.setContent("Y"+Integer.toString(electricity)); //Sending to the chosen vendor the amount of electricity we need to buy
		Iterator receivers = msg.getAllIntendedReceiver();
		while(receivers.hasNext())
		{
			MiddleMan.SendMessageToMenu("\t"+getLocalName() + ": Sending message to " +((AID)receivers.next()).getLocalName());
		}
		send(msg);
	}

	private void buySellElectricity() //Buy or sell electricity based on the amount of electricity we require
	{
		if (electricity > 0) //We have excess electricity and we need to sell some
		{
			sendVendorMessage("S");
		}
		else if (electricity < 0) //We are in need of electricity and we need to buy some
		{
			sendVendorMessage("B");
		}
	}

	@Override
	protected void setup() //Start the agent
	{
		MainMenu.main(null);
		MiddleMan.SendMessageToMenu(getLocalName() + ": I have been created");
		activateCounter();
		getReply();
	}
}