package chat.log;

import jade.core.Agent;
import jade.core.behaviours.Behaviour;

public class LoggerAgent extends Agent {

	@Override
	protected void setup() {

		final Behaviour loggerBehaviour = new LoggerAgentBehaviour(this);
		this.addBehaviour(loggerBehaviour);
	}
}
