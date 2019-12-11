package chat.server;

import java.util.HashMap;
import java.util.Map;

import chat.client.ChatClient;
import chat.client.ChatClient.ClientStatus;
import jade.core.AID;
import jade.core.Agent;
import jade.lang.acl.ACLMessage;

@SuppressWarnings("serial")
public class ServerAgent extends Agent {

	private Map<AID, ChatClient> chatClientsMap;

	public ServerAgent() {
		this.chatClientsMap = new HashMap<>();
	}

	@Override
	protected void setup() {

		ServerReceiverBehaviour receiverBehaviour = new ServerReceiverBehaviour(this);
		addBehaviour(receiverBehaviour);
	}

	protected void OnClientSubscribe(AID aid, ChatClient client) {

		this.chatClientsMap.putIfAbsent(aid, client);
		NotifyAllOnlineAgents();
	}

	protected void OnClientUnsubscribe(AID aid) {

		this.chatClientsMap.remove(aid);
		NotifyAllOnlineAgents();
	}

	private void NotifyAllOnlineAgents() {

		// Create an array of JSON objects which represents all the serialized agents
		StringBuilder sbBuilder = new StringBuilder();
		sbBuilder.append('[');

		for (Map.Entry<AID, ChatClient> entry : chatClientsMap.entrySet()) {

			ChatClient client = entry.getValue();
			if (client.getStatus() == ClientStatus.Online) {
				sbBuilder.append(entry.getValue().toString());
				sbBuilder.append(',');
			}
		}

		sbBuilder.deleteCharAt(sbBuilder.lastIndexOf(","));
		sbBuilder.append(']');

		String agentsJson = sbBuilder.toString();
		for (Map.Entry<AID, ChatClient> entry : chatClientsMap.entrySet()) {
			ACLMessage message = new ACLMessage(ACLMessage.INFORM);

			message.addReceiver(entry.getKey());
			message.setContent(agentsJson);
			this.send(message);
		}
	}
}
