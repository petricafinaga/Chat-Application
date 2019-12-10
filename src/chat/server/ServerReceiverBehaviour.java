package chat.server;

import java.util.HashMap;
import java.util.Map;

import chat.client.ChatClient;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

@SuppressWarnings("serial")
public class ServerReceiverBehaviour extends CyclicBehaviour {

	private Agent myAgent;
	private Map<AID, ChatClient> onlineAgentsMap;

	public ServerReceiverBehaviour(Agent agent) {
		super(agent);

		this.myAgent = agent;
		this.onlineAgentsMap = new HashMap<>();
	}

	@Override
	public void action() {
		ACLMessage message = myAgent.receive();

		if (null != message) {
			switch (message.getPerformative()) {
			case ACLMessage.SUBSCRIBE:
				String alias = message.getContent();
				if (alias.equals(""))
					return;

				this.onlineAgentsMap.putIfAbsent(message.getSender(),
						new ChatClient(message.getSender(), alias, ChatClient.ClientStatus.Online));
				NotifyAllOnlineAgents(onlineAgentsMap);
			}

		} else {
			block();
		}
	}

	private void NotifyAllOnlineAgents(Map<AID, ChatClient> onlineAgents) {

		for (Map.Entry<AID, ChatClient> entry : onlineAgents.entrySet()) {
			ACLMessage message = new ACLMessage(ACLMessage.INFORM);

			message.addReceiver(entry.getKey());
			StringBuilder sbBuilder = new StringBuilder();
			sbBuilder.append('[');

			for (Map.Entry<AID, ChatClient> agent : onlineAgents.entrySet()) {
				sbBuilder.append(agent.getValue());
				System.out.println(agent.getValue());

//				Gson gson = new Gson();
//				gson.fromJson(agent.getValue(), ChatClient.class);
				sbBuilder.append(',');
			}

			sbBuilder.append(']');

			message.setContent(sbBuilder.toString());
			myAgent.send(message);
		}
	}
}
