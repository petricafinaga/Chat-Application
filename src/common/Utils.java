package common;

import com.google.gson.Gson;

import chat.client.ClientConfig;

public class Utils {
	static final Gson gson = new Gson();

	public static <T> String ToJson(T container) {
		return gson.toJson(container);
	}

	public static <T> T ToObject(String json, Class<T> className) {
		return gson.fromJson(json, className);
	}

	public static boolean WriteClientConfigToFile(ClientConfig config) {
		// TO DO

		return true;
	}

	public static ClientConfig ReadClientConfigFromFile() {
		// TO DO

		return null;
	}

}
