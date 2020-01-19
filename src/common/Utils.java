/**
 * @author Finaga Petrica
 *
 * @version 1.0
 * @since 05-12-2019
 **/

package common;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.google.gson.Gson;

import chat.client.ClientConfig;
import jade.core.AID;
import jade.core.Agent;
import jade.lang.acl.ACLMessage;

public class Utils {
	private static final Gson gson = new Gson();

	private static AID loggerAID = null;

	public static <T> String ToJson(T container) {
		return gson.toJson(container);
	}

	public static <T> T ToObject(String json, Class<T> className) {

		try {
			T object = gson.fromJson(json, className);
			return object;
		} catch (Exception e) {
			return null;
		}
	}

	public static void InitializeLoggerAgent() {

		loggerAID = new AID();
		loggerAID.setLocalName("LoggerAgent");
	}

	public static void LogInformMessage(Agent agent, String content) {

		if (agent == null || loggerAID == null)
			return;

		ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
		msg.setContent(content);
		msg.addReceiver(loggerAID);

		agent.send(msg);
	}

	public static void LogErrorMessage(Agent agent, String content) {

		if (agent == null || loggerAID == null)
			return;

		ACLMessage msg = new ACLMessage(ACLMessage.FAILURE);
		msg.setContent(content);
		msg.addReceiver(loggerAID);

		agent.send(msg);
	}

	public static boolean WriteClientConfigToFile(final ClientConfig config, final String fileName) {

		if (config == null)
			return false;

		final File file = new File(fileName);

		// Create the file
		try (final FileWriter fileWriter = new FileWriter(file)) {
			fileWriter.write(ToJson(config));
		} catch (IOException e) {
			return false;
		}

		return true;
	}

	public static ClientConfig ReadClientConfigFromFile(final String fileName) {
		final File file = new File(fileName);

		if (!file.exists())
			return null;

		String json = "";
		try {
			final BufferedReader reader = new BufferedReader(new FileReader(file));
			String line = "";

			while ((line = reader.readLine()) != null) {
				json += line;
			}

			reader.close();
		} catch (Throwable t) {
			t.printStackTrace();
		}

		if (!json.equals("")) {
			final ClientConfig config = ToObject(json, ClientConfig.class);
			return config;
		}

		return null;
	}

	public static String GetLocalIPAddress() {

		String localAddress = "";
		try {
			localAddress = InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}

		return localAddress;
	}

	public static String GetCurrentDate() {

		Date date = new Date(); // this object contains the current date value
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy/HH:mm:ss");

		return formatter.format(date);
	}
}
