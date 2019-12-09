package chat.client;

import java.awt.EventQueue;

import chat.server.ServerAgent;
import jade.core.AID;
import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;

@SuppressWarnings("serial")
public class ClientAgent extends Agent {

	private ClientGUI clientGui;

	@Override
	protected void setup() {

		DFAgentDescription dfd = new DFAgentDescription();
		ServiceDescription sd = new ServiceDescription();
		sd.setType("chat-client");
		sd.setName(getName());
		sd.setOwnership("SI");
		dfd.setName(getAID());
		dfd.addServices(sd);

		try {
			// Register chat-client to DF
			DFService.register(this, dfd);
			ServerAgent.register(this, dfd);

			EventQueue.invokeLater(new Runnable() {
				@Override
				public void run() {
					try {
						clientGui = new ClientGUI();
						clientGui.setVisible(true);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});

			ClientReceiverBehaviour receiverBehaviour = new ClientReceiverBehaviour(this);
			this.addBehaviour(receiverBehaviour);

		} catch (FIPAException e) {
			doDelete();
		}
	}

	@Override
	public void takeDown() {
		try {
			DFService.deregister(this);
			clientGui.dispose();
		} catch (FIPAException e) {
			e.printStackTrace();
		}
	}

	public void OnMessage(String message, AID sender) {
		System.out.println(message + " " + sender.getName());
	}
}
