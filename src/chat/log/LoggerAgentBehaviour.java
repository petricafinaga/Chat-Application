/**
 * @author Finaga Petrica
 *
 * @version 1.0
 * @since 05-12-2019
 **/

package chat.log;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import common.Utils;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;

@SuppressWarnings("serial")
public class LoggerAgentBehaviour extends Behaviour {
	private final String fLogFileName = "server.log";

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
				LogToFile("Log INFO: " + message.getContent());
				break;

			// Log ERROR message type from server
			case ACLMessage.FAILURE:
				LogToFile("Log ERROR: " + message.getContent());
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

	private void LogToFile(String content) {

		FileWriter fileWriter;
		try {

			fileWriter = new FileWriter(fLogFileName, true);
			BufferedWriter writer = new BufferedWriter(fileWriter);

			writer.write(Utils.GetCurrentDate() + " | " + content);
			writer.newLine(); // Add new line
			writer.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
