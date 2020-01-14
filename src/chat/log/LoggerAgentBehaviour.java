package chat.log;

import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;

@SuppressWarnings("serial")
public class LoggerAgentBehaviour extends Behaviour {

	private LoggerAgent myAgent;

	public LoggerAgentBehaviour(LoggerAgent agent) {
		this.myAgent = agent;
	}

	@Override
	public void action() {
		final ACLMessage message = myAgent.receive();

		if (null != message) {

			switch (message.getPerformative()) {

			// Log INFO message type from server
			case ACLMessage.INFORM:
				System.out.println("Log INFO: " + message.getContent());
				break;

			// Log ERROR message type from server
			case ACLMessage.FAILURE:
				System.out.println("Log ERROR: " + message.getContent());
				break;

			default:
				break;
			}
		} else {
			block();
		}

	}

	@Override
	public boolean done() {
		// TODO Auto-generated method stub
		return false;
	}

}
