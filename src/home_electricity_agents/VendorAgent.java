package home_electricity_agents;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;

public class VendorAgent extends Agent {
	//Knows how much energy it can sell
	//And how much electricity it gets per time frame
	private int electricity = 0;
	private int electIncrement = 0;
	private String sellElectricity;
	private String buyElectricity;
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
		buyElectricity = args[2].toString(); //Cost they buy electricity at
		sellElectricity = args[3].toString(); //Cost they sell electricity at
		register( sd );

		addBehaviour(new CyclicBehaviour(this) {
			@Override
			public void action() {
				//Receive the other agents message
				ACLMessage msg=receive();
				if (msg != null)
				{
					System.out.println(getLocalName()+ ": Received message " + msg.getContent() + " from " + msg.getSender().getLocalName());
					if (msg.getContent().contains("B")) //Home needs to buy electricity
					{
						ACLMessage reply = msg.createReply();
						reply.setPerformative(ACLMessage.INFORM);
						//reply.setContent("B" +sellElectricity);
						reply.setContent(sellElectricity);
						System.out.println("\t" + getLocalName() + ": Sending response " + reply.getContent() + " to " + msg.getAllReceiver().next());
						send(reply);
					}
					else if (msg.getContent().contains("S")) //Home needs to sell electricity
					{
						ACLMessage reply = msg.createReply();
						reply.setPerformative(ACLMessage.INFORM);
						//reply.setContent("S"+buyElectricity);
						reply.setContent(buyElectricity);
						System.out.println("\t" + getLocalName() + ": Sending response " + reply.getContent() + " to " + msg.getAllReceiver().next());
						send(reply);
					}
				}
				else
					block();
			}
		});
	}
}
