package home_electricity_agents;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

import javax.swing.*;

public class ApplianceAgent extends Agent {
	//How much electricity the appliance consumes
	//And how varied the consumption can be
	private int electConsumption = 0;
	private int consumeRange = 0;
	
	//TODO: add home ID
	@Override
	protected void setup()
	{
		addBehaviour(new CyclicBehaviour(this) {
			@Override
			public void action() {
				//Receive the other agents message
				ACLMessage msg=receive();
				if (msg != null)
				{
					//JOptionPane.showMessageDialog(null,"Message received " + msg.getContent());
					System.out.println(getLocalName()+ ": Received message " +
					msg.getContent() + " from " + msg.getSender().getLocalName());

					ACLMessage reply = msg.createReply();
					reply.setPerformative(ACLMessage.INFORM);
					reply.setContent("Pong");

					System.out.println(getLocalName() + ": Sending response " +
					reply.getContent() + " to " + msg.getAllReceiver().next());

					send(reply);
				}
				else
					block();
			}
		});
	}
}
