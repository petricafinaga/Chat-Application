package chat.server;

import javax.swing.text.AbstractDocument.Content;

import chat.client.ChatClient;
import chat.client.ChatClient.ClientStatus;
import jade.core.AID;
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
		final ACLMessage message = myAgent.receive();

		if (null != message) {
			final String content = message.getContent();
			switch (message.) {

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
		final String alias = message.getContent();
		if (alias.equals(""))
			return;

		final AID aid = message.getSender();
		this.myAgent.OnClientSubscribe(aid, new ChatClient(aid.getName(), alias, ClientStatus.Online));
	}

	private void HandleAgentUnsubscription(ACLMessage message) {
		this.myAgent.OnClientUnsubscribe(message.getSender().getName());
	}
}
