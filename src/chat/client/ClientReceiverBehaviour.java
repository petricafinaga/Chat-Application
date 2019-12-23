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
				myAgent.OnAllClients();
				break;

			// Handle ClientUpdate message from server
			case ClientUpdate:
				myAgent.OnClientUpdate();
				break;

			default:
				break;
			}
		} else {
			block();
		}
	}

}
