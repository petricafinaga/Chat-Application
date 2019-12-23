package chat.server;

import java.util.HashMap;
import java.util.Map;

import chat.client.ChatClient;
import chat.client.ChatClient.ClientStatus;
import common.Message;
import common.Message.MessageType;
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

		final String name = aid.getName();
		chatClientsMap.put(name, client);
	}

	protected void OnClientUnsubscribe(String name) {

		final ChatClient client = chatClientsMap.get(name);
		client.SetStatus(ClientStatus.Offline);

		NotifyAllOnlineAgents(client);
	}

	private void SendAllAgentsToClient(AID aid) {

		ACLMessage message = new ACLMessage(ACLMessage.INFORM);

		message.addReceiver(aid);
		message.setContent(new Message(MessageType.AllClients, Utils.ToJson(chatClientsMap.values())).toString());

		this.send(message);
	}

	private void NotifyAllOnlineAgents(ChatClient client) {

		final String content = new Message(MessageType.ClientUpdate, Utils.ToJson(client)).toString();
		for (final Map.Entry<String, ChatClient> key : chatClientsMap.entrySet()) {
			if (key.getValue().getStatus() != ClientStatus.Online)
				continue;

			final AID aid = new AID();
			aid.setName(key.getKey());

			final ACLMessage message = new ACLMessage(ACLMessage.INFORM);
			message.addReceiver(aid);
			message.setContent(content);
			this.send(message);
		}
	}
}
