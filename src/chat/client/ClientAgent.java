package chat.client;

import java.awt.EventQueue;

import common.Message;
import common.Message.MessageType;
import jade.core.AID;
import jade.core.Agent;
import jade.lang.acl.ACLMessage;

@SuppressWarnings("serial")
public final class ClientAgent extends Agent {
	private final String fServerName = "ChatServer";

	private AID serverAid;
	private ClientGUI clientGui;

	public ClientAgent() {
		serverAid = new AID();
		serverAid.setLocalName(fServerName);
	}

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

	public void OnAllClients(ChatClient[] clients) {
		// TO DO
	}

	public void OnClientUpdate(ChatClient client) {
		// TO DO
	}

	private void SubscribeToServer() {

		final ACLMessage message = new ACLMessage(ACLMessage.INFORM);

		message.addReceiver(serverAid);
		message.setContent(new Message(MessageType.Subscribe, "Petrica").toString());

		this.send(message);
	}

	private void UnsubscribeFromServer() {

		final ACLMessage message = new ACLMessage(ACLMessage.INFORM);

		message.addReceiver(serverAid);
		message.setContent(new Message(MessageType.Unsubscribe, null).toString());

		this.send(message);
	}
}
