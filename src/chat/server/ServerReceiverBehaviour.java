package chat.server;

import chat.client.ChatClient;
import chat.client.ChatClient.ClientStatus;
import common.Message;
import common.Utils;
import jade.core.AID;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

@SuppressWarnings("serial")
public final class ServerReceiverBehaviour extends CyclicBehaviour {

	private ServerAgent myAgent;

	public ServerReceiverBehaviour(ServerAgent agent) {
		super(agent);
		this.myAgent = agent;
	}

	@Override
	public void action() {
		final ACLMessage message = myAgent.receive();

		if (null != message) {
			final Message content = Utils.ToObject(message.getContent(), Message.class);

			switch (content.getMessageType()) {

			// Handle SUBSCRIBE message from chat client agent
			case Subscribe:
				HandleAgentSubscription(message.getSender(), content);
				break;

			// Handle UNSUBSCRIBE message from chat client agent
			case Unsubscribe:
				HandleAgentUnsubscription(message.getSender());
				break;

			default:
				break;
			}
		} else {
			block();
		}
	}

	private void HandleAgentSubscription(AID aid, Message message) {
		final String alias = message.getMessage();
		if (alias.equals(""))
			return;

		this.myAgent.OnClientSubscribe(aid, new ChatClient(aid.getName(), alias, ClientStatus.Online));
	}

	private void HandleAgentUnsubscription(AID aid) {
		this.myAgent.OnClientUnsubscribe(aid.getName());
	}
}
