/**
 * @author Finaga Petrica
 *
 * @version 1.0
 * @since 05-12-2019
 **/

package chat.client;

import common.Message;
import common.Message.MessageType;
import common.Utils;
import jade.core.AID;
import jade.core.Agent;
import jade.lang.acl.ACLMessage;

@SuppressWarnings("serial")
public final class ClientAgent extends Agent {
	private static final String configExtension = ".config";

	private AID serverAid;
	private ClientGUI clientGui;
	private ClientConfig clientConfig;

	private String configFileName;

	public ClientAgent() {
		serverAid = new AID();
	}

	/**
	 * Override methods from inherited class
	 */
	@Override
	protected void setup() {

		configFileName = this.getLocalName() + configExtension;
		clientConfig = Utils.ReadClientConfigFromFile(configFileName);

		if (clientConfig == null) {
			clientConfig = ClientConfig.GetDefaultClientConfig();
			Utils.WriteClientConfigToFile(clientConfig, configFileName);
		}

		serverAid.setLocalName(clientConfig.GetServerName());
		String alias = clientConfig.GetAlias();

		clientGui = new ClientGUI(this, alias);
		clientGui.setVisible(true);

		// Subscribe to Chat Server
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
		clientGui.GUIAddUsers(clients);
	}

	public void OnClientUpdate(ChatClient client) {
		clientGui.GUIAddOrModifyUserStatus(client);
	}

	public void OnTextMessage(String clientName, String messageText) {
		clientGui.GUIDisplayReceivedMessage(clientName, messageText);
	}

	public void UpdateAlias(String alias) {
		clientConfig.SetAlias(alias);
		Utils.WriteClientConfigToFile(clientConfig, configFileName);
		SubscribeToServer();
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
