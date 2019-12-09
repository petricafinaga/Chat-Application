package chat.server;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import jade.core.AID;
import jade.core.Agent;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.util.leap.Iterator;

@SuppressWarnings("serial")
public class ServerAgent extends Agent {

	private static Map<AID, Vector<Agent>> onlineAgentsMap = new HashMap<>();

	public static Vector<Agent> register(Agent a, DFAgentDescription dfd) {

		Iterator iterator = dfd.getAllServices();
		while (iterator.hasNext()) {
			Object serviceObject = iterator.next();
			if (serviceObject.getClass().toString().equals(new ServiceDescription().getClass().toString())) {
				ServiceDescription serviceDescription = (ServiceDescription) serviceObject;

				System.out.println("type: " + serviceDescription.getType());
			}
		}

		return null;
	}
}
