package chat.server;

import java.util.HashMap;
import java.util.Map;

import chat.client.ChatClient;
import common.Utils;
import jade.core.AID;
import jade.core.Agent;
import jade.lang.acl.ACLMessage;

@SuppressWarnings("serial")
public class ServerAgent extends Agent {

	// Map that contains <Name, ChatClient> pair for each chatClient that has
	// subscribed to server
	private Map<String, ChatClient> chatClientsMap;

	public ServerAgent() {
		this.chatClientsMap = new HashMap<>();
	}

	@Override
	protected void setup() {

		final ServerReceiverBehaviour receiverBehaviour = new ServerReceiverBehaviour(this);
		addBehaviour(receiverBehaviour);
	}

	protected void OnClientSubscribe(AID aid, ChatClient client) {

		SendAllAgentsToClient(aid);
		NotifyAllOnlineAgents(client);
		this.chatClientsMap.putIfAbsent(aid.getName(), client); // TO DO: Verify if already exists in map, and just
																// change the status
	}

	protected void OnClientUnsubscribe(String name) {

//		this.chatClientsMap.remove(name); // TO DO: Do not remove client, just change the status to offline
		NotifyAllOnlineAgents(null);
	}

	private void SendAllAgentsToClient(AID aid) {

		ACLMessage message = new ACLMessage(ACLMessage.INFORM);

		message.addReceiver(aid);
		message.setContent(Utils.ToJson(chatClientsMap.values()));

		this.send(message);
	}

	private void NotifyAllOnlineAgents(ChatClient client) {

		final String clientJson = Utils.ToJson(client);
		System.out.println("cucuc" + clientJson);

		for (String name : chatClientsMap.keySet()) {
			final AID aid = new AID();
			aid.setName(name);

			final ACLMessage message = new ACLMessage(ACLMessage.INFORM);
			message.addReceiver(aid);
			message.setContent(clientJson);
			this.send(message);
		}
	}
}
