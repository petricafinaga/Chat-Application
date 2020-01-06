package chat.client;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

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

		File file = new File("config.txt");

		// Create the file
		try {
			if (file.createNewFile()) {
				System.out.println("File is created!");
			} else {
				System.out.println("File already exists.");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Write Content
		FileWriter writer;
		try {
			writer = new FileWriter(file);
			writer.write("Test data");
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Override methods from inherited class
	 */
	@Override
	protected void setup() {

		// Subscribe to Local Server
		SubscribeToServer();

		clientGui = new ClientGUI(this);
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
		clientGui.GUIAddUsers(clients);
	}

	public void OnClientUpdate(ChatClient client) {
		clientGui.GUIAddOrModifyUserStatus(client);
	}

	public void OnTextMessage(String clientName, String messageText) {
		clientGui.GUIDisplayReceivedMessage(clientName, messageText);
	}

	public void UpdateAlias(String alias) {

	}

	public void SendMessage(String clientName, Message msg) {

		if (msg == null)
			return;

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
		message.setContent(new Message(MessageType.Subscribe, "user-" + Math.random()).toString());

		this.send(message);
	}

	private void UnsubscribeFromServer() {

		final ACLMessage message = new ACLMessage(ACLMessage.INFORM);

		message.addReceiver(serverAid);
		message.setContent(new Message(MessageType.Unsubscribe, null).toString());

		this.send(message);
	}
}
