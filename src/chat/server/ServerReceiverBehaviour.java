package chat.server;

import chat.client.ChatClient;
import chat.client.ChatClient.ClientStatus;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

@SuppressWarnings("serial")
public class ServerReceiverBehaviour extends CyclicBehaviour {

	private ServerAgent myAgent;

	public ServerReceiverBehaviour(ServerAgent agent) {
		super(agent);
		this.myAgent = agent;
	}

	@Override
	public void action() {
		ACLMessage message = myAgent.receive();

		if (null != message) {
			switch (message.getPerformative()) {

			// Handle SUBSCRIBE message from chat client agent
			case ACLMessage.SUBSCRIBE:
				HandleAgentSubscription(message);
				break;

			// Handle CANCEL subscription message from chat client agent
			case ACLMessage.CANCEL:
				HandleAgentUnsubscription(message);
				break;
			}
		} else {
			block();
		}
	}

	private void HandleAgentSubscription(ACLMessage message) {
		String alias = message.getContent();
		if (alias.equals(""))
			return;

		this.myAgent.OnClientSubscribe(message.getSender(),
				new ChatClient(message.getSender().getName(), alias, ClientStatus.Online));
	}

	private void HandleAgentUnsubscription(ACLMessage message) {
		this.myAgent.OnClientUnsubscribe(message.getSender());
	}
}
