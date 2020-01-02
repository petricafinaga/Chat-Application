package chat.client;

import common.Message;
import common.Utils;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

@SuppressWarnings("serial")
public final class ClientReceiverBehaviour extends CyclicBehaviour {

	private ClientAgent myAgent;

	public ClientReceiverBehaviour(ClientAgent clientAgent) {
		super(clientAgent);
		myAgent = clientAgent;
	}

	@Override
	public void action() {
		final ACLMessage message = myAgent.receive();

		if (null != message) {

			final Message content = Utils.ToObject(message.getContent(), Message.class);
			switch (content.getMessageType()) {

			// Handle AllClients message from server
			case AllClients:
				final ChatClient[] clients = Utils.ToObject(content.getMessage(), ChatClient[].class);
				myAgent.OnAllClients(clients);
				break;

			// Handle ClientUpdate message from server
			case ClientUpdate:
				final ChatClient client = Utils.ToObject(content.getMessage(), ChatClient.class);
				myAgent.OnClientUpdate(client);
				break;

			// Handle TextMessage from chat-client
			case TextMessage:
				myAgent.OnTextMessage(message.getSender().getName(), content.getMessage());
				break;

			default:
				break;
			}
		} else {
			block();
		}
	}

}
