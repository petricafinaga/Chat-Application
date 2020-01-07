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
	}

	/**
	 * Override methods from inherited class
	 */
	@Override
	protected void setup() {

		clientConfig = Utils.ReadClientConfigFromFile();

		if (clientConfig == null) {
			clientConfig = ClientConfig.GetDefaultClientConfig();
			Utils.WriteClientConfigToFile(clientConfig);
		}


		serverAid.setName(fServerName + "@" + clientConfig.GetServerAddress() + ":1099" + "/JADE");

		String alias = clientConfig.GetAlias();

		clientGui = new ClientGUI(this, alias);
		clientGui.setVisible(true);

		// Subscribe to Local Server
		if (alias != null)
			SubscribeToServer();

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

		clientGui.GUIAddUsers(clients);
	}

	public void OnClientUpdate(ChatClient client) {
		// TO DO

		clientGui.GUIAddOrModifyUserStatus(client);
	}

	public void OnTextMessage(String clientName, String messageText) {
		// TO DO

		clientGui.GUIDisplayReceivedMessage(clientName, messageText);
	}

	public void UpdateAlias(String alias) {
		clientConfig.SetAlias(alias);
		Utils.WriteClientConfigToFile(clientConfig);

		SubscribeToServer();
>>>>>>> 6a81e4e847d87b4943b056b33df288b3218251f7
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

		message.setContent(new Message(MessageType.Subscribe, clientConfig.GetAlias()).toString());

		this.send(message);
	}

	private void UnsubscribeFromServer() {

		final ACLMessage message = new ACLMessage(ACLMessage.INFORM);

		message.addReceiver(serverAid);
		message.setContent(new Message(MessageType.Unsubscribe, null).toString());

		this.send(message);
	}
}
