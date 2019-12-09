package chat.client;

import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

@SuppressWarnings("serial")
public class ClientReceiverBehaviour extends CyclicBehaviour {

	private ClientAgent myAgent;

	public ClientReceiverBehaviour(ClientAgent clientAgent) {
		super(clientAgent);
		myAgent = clientAgent;
	}

	@Override
	public void action() {
		ACLMessage message = myAgent.receive();

		if (null != message) {
			myAgent.OnMessage(message.getContent(), message.getSender());
		} else {
			block();
		}
	}

}
