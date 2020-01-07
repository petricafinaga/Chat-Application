package chat.client;

import common.Message;
import common.Message.MessageType;
import common.Utils;
import jade.core.AID;
import jade.core.Agent;
import jade.lang.acl.ACLMessage;

@SuppressWarnings("serial")
public final class ClientAgent extends Agent {
	private final String fServerName = "ChatServer";

	private AID serverAid;
	private ClientGUI clientGui;
	private ClientConfig clientConfig;

	public ClientAgent() {
		serverAid = new AID();
		serverAid.setLocalName(fServerName);

		clientConfig = Utils.ReadClientConfigFromFile();
	}

	/**
	 * Override methods from inherited class
	 */
	@Override
	protected void setup() {

		// Subscribe to Local Server
		SubscribeToServer();

		String alias = "";
		if (clientConfig != null) {
			alias = clientConfig.GetAlias();
		} else {
			Utils.WriteClientConfigToFile(ClientConfig.GetDefaultClientConfig());
		}

		clientGui = new ClientGUI(this, alias);
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
//		Utils.WriteClientConfigToFile(config)
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
