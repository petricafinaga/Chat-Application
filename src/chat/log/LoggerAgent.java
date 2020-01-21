/**
 * @author Finaga Petrica
 *
 * @version 1.0
 * @since 05-12-2019
 **/

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
