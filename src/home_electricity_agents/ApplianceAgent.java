package home_electricity_agents;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
import java.util.Random;

public class ApplianceAgent extends Agent {
	//How much electricity the appliance consumes
	//And how varied the consumption can be
	private int electConsumption;
	private int consumeRange;

	@Override
	protected void setup() //Start the agent
	{
		Object[] args = getArguments();

		ServiceDescription sd  = new ServiceDescription();
		sd.setType( args[0].toString());
		sd.setName( args[1].toString());
		electConsumption = Integer.parseInt(args[2].toString());
		consumeRange = Integer.parseInt(args[3].toString());
		register( sd );

		addBehaviour(new CyclicBehaviour(this) 
		{
			//Computes the current consumption and returns it as a string
			public String GetConsumption()
			{
				Random theRandom = new Random();
				//Get a number between -consumeRange and consumeRange
				int currentRandomRange = theRandom.nextInt(consumeRange*2) - consumeRange;
				
				//Add it to the current electric consumption rate and turn it into a string
				String theConsumption = Integer.toString(electConsumption + currentRandomRange);
				return theConsumption;
			}
			
			@Override
			public void action() 
			{
				//Receive the other agents message
				ACLMessage msg=receive();
				if (msg != null)
				{
					System.out.println(getLocalName()+ ": Received message " + msg.getContent() + " from " + msg.getSender().getLocalName());
					if (msg.getContent().equals("cost"))
					{
						ACLMessage reply = msg.createReply();
						reply.setPerformative(ACLMessage.INFORM);
						reply.setContent(this.GetConsumption());
						System.out.println("\t" + getLocalName() + ": Sending response " + reply.getContent() + " to " + msg.getAllReceiver().next());
						send(reply);
					}
				}
				else
					block();
			}
		}
		);
	}

	// Method to register the service
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
	
	//TODO: add home ID
}
