package home_electricity_agents;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
import java.util.Random;
import gui_application.MiddleMan;

public class VendorAgent extends Agent {
	//Knows how much energy it can sell
	//And how much electricity it gets per time frame
	private int electricity = 0;
	private int electIncrement = 0;
	private String sellElectricity;
	private String buyElectricity;

	private Integer money = 0;
	private Integer messageData;
	private Integer currentBarterTick;
	private String buyMin;
	private String buyMax;
	private	String sellMin;
	private String sellMax;
	private Integer barterTicks;
	private String vendorType;
	private Integer tempBarterPrice;

	//Knows the range that it will offer
	//This will be price per energy
	private int acceptableBuyMax = 0;
	private int acceptableBuyMin = 0;
	
	private int acceptableSellMax = 0;
	private int acceptableSellMin = 0;


	void register( ServiceDescription sd)
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
		vendorType = args[6].toString(); //Set the vendor type, this is to choose the way that the vendor barters with the home agent
		register( sd );
		addBehaviour(new CyclicBehaviour(this) {
			@Override
			public void action() {
				ACLMessage msg=receive();//Receive the other agents message
				if (msg != null)
				{
					MiddleMan.SendMessageToMenu(getLocalName()+ ": Received message " + msg.getContent() + " from " + msg.getSender().getLocalName());
					try
					{
						messageData  = Integer.parseInt(msg.getContent().substring(1)); //Get the data from the message past the first char
					}
					catch(java.lang.NumberFormatException ex) {}
					ACLMessage reply = msg.createReply();
					reply.setPerformative(ACLMessage.INFORM);
					switch (msg.getContent().charAt(0)) //Depending on the first character the message has a different meanning
					{
						case 'B': //Initial message from a home agent asking how much the electricity costs
							reply.setContent("I" + getSellElectricity());//Reply with the cost of the electricity
							Random barterRand = new Random();
							barterTicks = barterRand.nextInt(5) + 1; //Used to decide how many ticks (1-5) this vendor will barter with the home for
							currentBarterTick = 0;
							MiddleMan.SendMessageToMenu("\t" + getLocalName() + ": Sending response " + reply.getContent() + " to " +  msg.getSender().getLocalName());
							send(reply);
							break;
						case 'S': //Home needing to sell electricity

							break;
						case 'L': //From a home asking for a lower price
							if (currentBarterTick < barterTicks) //If the vendor agent is willing to barter any more
							{
								currentBarterTick++; //Tick up, this is to choose how many times the vendor will barter with the home agent (1-5) times
								switch (vendorType.charAt(0))
								{
									case 'A':
										tempBarterPrice = getLinearPrice(); //The price of the electricity will fall in a linear pattern
										break;
									case 'B':
										tempBarterPrice = getParabolicPrice(); //The price of the electricity will fall in a upside down parabola pattern(Small early reduction in price, but later on the price decreases faster
										break;
									case 'C':
										tempBarterPrice = getNegativeParabolicPrice();//The price of the electricity will fall in a sideways parabola pattern(Larger early price reduction, but later the price will not reduce much more
										break;
									default:
										break;
								}
							}
							if (currentBarterTick.equals(barterTicks)) //If the vendor is on its final offer
							{
								reply.setContent("F" + Integer.toString(tempBarterPrice)); //Final Offer
								MiddleMan.SendMessageToMenu(":::::::::::::::::FINAL OFFER FROM " + getLocalName() + ":::::::::::::::::");
							}
							else
							{
								reply.setContent("R" + Integer.toString(tempBarterPrice)); //Bartering can continue
							}
							MiddleMan.SendMessageToMenu("\t" + getLocalName() + ": Sending response " + reply.getContent() + " to " + msg.getSender().getLocalName());
							send(reply);
							break;
						case 'Y': //The home agent has accepted the price of this electricity
							messageData *= -1; //Make the electricity a positive number
							electricity -= messageData;
							money += messageData * tempBarterPrice; //Add the amount of money
							reply.setContent("C" + Integer.toString(messageData * tempBarterPrice));
							MiddleMan.SendMessageToMenu("\t" + getLocalName() + ": Sending response " + reply.getContent() + " to " + msg.getSender().getLocalName());
							send(reply); //Reply to the home agent with the total cost of the electricity
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

	private Integer getLinearPrice() //Linear path eg: y = -x + 100
	{
		return -((Integer.parseInt(sellElectricity) - Integer.parseInt(sellMin))/5) * currentBarterTick + Integer.parseInt(sellElectricity);
	}

	private Integer getParabolicPrice() //Slow start, late discounts eg: y = -x^2 + 100
	{
		return (int)-(((Integer.parseInt(sellElectricity) - Integer.parseInt(sellMin))/Math.pow(5,2)) * Math.pow(currentBarterTick,2)) + Integer.parseInt(sellElectricity);
	}
	private Integer getNegativeParabolicPrice() //Early discounts slow later eg: y = (100/(x+4))+75
	{
		return (int)-(Math.sqrt((Math.pow(Integer.parseInt(sellElectricity) - Integer.parseInt(sellMin),2)* currentBarterTick)/5)) + Integer.parseInt(sellElectricity);
	}

	private String getSellElectricity() //Get the initial price of electricity for this tick
	{
		sellElectricity = GetOffer(Integer.parseInt(sellMin), Integer.parseInt(sellMax));
		return sellElectricity;
	}

	private String getBuyElectricity()
	{
		buyElectricity = GetOffer(Integer.parseInt(buyMin), Integer.parseInt(buyMax));
		return buyElectricity;
	}

	public String GetOffer(int min, int max)//Creates an offer, based on the given minimum and maximum values
	{
		int difference = max - min;
		Random theRandom = new Random();
		String offer = Integer.toString(min + theRandom.nextInt(difference));
		return offer;
	}
}
