package home_electricity_agents;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;

import java.util.Iterator;
import java.util.Random;


public class VendorAgent extends Agent {
	//Knows how much energy it can sell
	//And how much electricity it gets per time frame
	private int electricity = 0;
	private int electIncrement = 0;
	private String sellElectricity;
	private String buyElectricity;

	private String buyMin;
	private String buyMax;
	private	String sellMin;
	private String sellMax;
	//Knows the range that it will offer
	//This will be price per energy
	private int acceptableBuyMax = 0;
	private int acceptableBuyMin = 0;
	
	private int acceptableSellMax = 0;
	private int acceptableSellMin = 0;


	void register( ServiceDescription sd) // EXPERIMENTAL:: Maybe works, maybe doesn't. Was trying to use this to find the agent type when sending messages
	{
		DFAgentDescription dfd = new DFAgentDescription();
		dfd.setName(getAID());
		dfd.addServices(sd); // An agent can register one or more services

		// Register the agent and its services
		try {
			DFService.register(this, dfd );
		}
		catch (FIPAException fe) { fe.printStackTrace(); }
	}

	@Override
	protected  void setup() //Start the agent
	{
		Object[] args = getArguments();
		ServiceDescription sd  = new ServiceDescription();
		sd.setType( args[0].toString());
		sd.setName( args[1].toString());
		sellMin = args[2].toString();
		sellMax = args[3].toString();
		buyMin = args[4].toString();
		buyMax = args[5].toString();
		//buyElectricity = args[2].toString(); //Cost they buy electricity at

		//buyElectricity = GetOffer(Integer.parseInt(buyMin), Integer.parseInt(buyMax));
		register( sd );

		addBehaviour(new CyclicBehaviour(this) {
			@Override
			public void action() {
				//Receive the other agents message
				ACLMessage msg=receive();
				if (msg != null)
				{
					System.out.println(getLocalName()+ ": Received message " + msg.getContent() + " from " + msg.getSender().getLocalName());
					//Integer messageData  = Integer.parseInt(msg.getContent().substring(1));
					//Iterator receivers = msg.getAllIntendedReceiver();
					switch (msg.getContent().charAt(0))
					{
						case 'B': //From an appliance
							ACLMessage reply = msg.createReply();
							reply.setPerformative(ACLMessage.INFORM);
							//reply.setContent("B" +sellElectricity);
							reply.setContent("I" + getSellElectricity());



							System.out.println("\t" + getLocalName() + ": Sending response " + reply.getContent() + " to " +  msg.getSender().getLocalName());

							send(reply);
							break;
						case 'S': //From a vendor
							//ACLMessage reply = msg.createReply();
							///reply.setPerformative(ACLMessage.INFORM);
							//reply.setContent("S"+buyElectricity);
							//reply.setContent(buyElectricity);
							//System.out.println("\t" + getLocalName() + ": Sending response " + reply.getContent() + " to " + msg.getAllReceiver().next());
							//send(reply);
							break;
						case 'L': //From a home asking for a lower price
							Random rand = new Random();
							int chosenNumber = rand.nextInt(100);
							System.out.println("\t" + getLocalName() + "RANDOM CHOSEN: " + chosenNumber);
							if (chosenNumber > 50)
							{
								chosenNumber = (chosenNumber - 50)/2;
								System.out.println("\t" + getLocalName() + "FIXED CHOSEN: " + chosenNumber);
								sellElectricity = Integer.toString(Integer.parseInt(sellElectricity) * (100 - chosenNumber)/100);
								System.out.println("\t" + getLocalName() + "Lower Price is now: " + sellElectricity);
							}
							ACLMessage replyBarter = msg.createReply();
							replyBarter.setPerformative(ACLMessage.INFORM);
							replyBarter.setContent("R" + sellElectricity);
							System.out.println("\t" + getLocalName() + ": Sending response " + replyBarter.getContent() + " to " + msg.getSender().getLocalName());

							send(replyBarter);
							break;
						case 'Y':

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
	//Increments the electricity
	private void IncrementElectricity()
	{
		electricity += electIncrement;
	}

	private String getSellElectricity()
	{
		sellElectricity = GetOffer(Integer.parseInt(sellMin), Integer.parseInt(sellMax));
		return sellElectricity;
	}

	private String getBuyElectricity()
	{
		buyElectricity = GetOffer(Integer.parseInt(buyMin), Integer.parseInt(buyMax));
		return buyElectricity;
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
