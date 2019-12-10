package chat.server;

import jade.core.Agent;

@SuppressWarnings("serial")
public class ServerAgent extends Agent {

	@Override
	protected void setup() {

		ServerReceiverBehaviour receiverBehaviour = new ServerReceiverBehaviour(this);
		addBehaviour(receiverBehaviour);
	}

}
