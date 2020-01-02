package chat.client;

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

	/**
	 * Override methods from inherited class
	 */
	@Override
	protected void setup() {

		// Subscribe to Local Server
		SubscribeToServer();

		clientGui = new ClientGUI();
		clientGui.setVisible(true);

		final ClientReceiverBehaviour receiverBehaviour = new ClientReceiverBehaviour(this);
		this.addBehaviour(receiverBehaviour);
	}

	@Override
	protected void takeDown() {
		UnsubscribeFromServer();
		clientGui.dispose();
	}
	/* End override methods from inherited class */

	public void OnAllClients(ChatClient[] clients) {
		// TO DO
	}

	public void OnClientUpdate(ChatClient client) {
		// TO DO
	}

	public void OnTextMessage(String clientName, String messageText) {
		// TO DO
	}

	public void SendMessage(String clientName, Message msg) {

		final AID aid = new AID();
		aid.setName(clientName);

		final ACLMessage message = new ACLMessage(ACLMessage.INFORM);
		message.addReceiver(aid);
		message.setContent(msg.toString());

		this.send(message);
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
