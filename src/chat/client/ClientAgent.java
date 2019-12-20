package chat.client;

import java.awt.EventQueue;

import jade.core.AID;
import jade.core.Agent;
import jade.lang.acl.ACLMessage;

@SuppressWarnings("serial")
public class ClientAgent extends Agent {

	private ClientGUI clientGui;

	@Override
	protected void setup() {

		// Subscribe to Local Server
		SubscribeToServer();
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

		final ClientReceiverBehaviour receiverBehaviour = new ClientReceiverBehaviour(this);
		this.addBehaviour(receiverBehaviour);
	}

	@Override
	public void takeDown() {
		UnsubscribeFromServer();
		clientGui.dispose();
	}

	public void OnMessage(String message, AID sender) {
		System.out.println("Received on: " + this.getLocalName() + " : " + message);
	}

	private void SubscribeToServer() {
		final AID receiverAid = new AID();
		receiverAid.setLocalName("ChatServer");

		final ACLMessage message = new ACLMessage(ACLMessage.SUBSCRIBE);
		message.setContent("Alias");
		message.addReceiver(receiverAid);

		this.send(message);
	}

	private void UnsubscribeFromServer() {
		final AID receiverAid = new AID();
		receiverAid.setLocalName("ChatServer");

		final ACLMessage message = new ACLMessage(ACLMessage.CANCEL);
		message.addReceiver(receiverAid);
	}
}
