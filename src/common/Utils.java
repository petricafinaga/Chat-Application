package common;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.google.gson.Gson;

import chat.client.ClientConfig;

public class Utils {
	private static final String configFileName = "config.json";

	static final Gson gson = new Gson();

	public static <T> String ToJson(T container) {
		return gson.toJson(container);
	}

	public static <T> T ToObject(String json, Class<T> className) {
		return gson.fromJson(json, className);
	}

	public static boolean WriteClientConfigToFile(ClientConfig config) {

		if (config == null)
			return false;

		File file = new File(configFileName);

		// Create the file
		try (FileWriter fileWriter = new FileWriter(file)) {
			fileWriter.write(ToJson(config));
		} catch (IOException e) {
			return false;
		}

		return true;
	}

	public static ClientConfig ReadClientConfigFromFile() {
		File file = new File(configFileName);

		if (!file.exists())
			return null;

		String json = "";
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line = "";

			while ((line = reader.readLine()) != null) {
				json += line;
			}

			reader.close();
		} catch (Throwable t) {
			t.printStackTrace();
		}

		if (!json.equals("")) {
			ClientConfig config = ToObject(json, ClientConfig.class);
			return config;
		}

		return null;
	}
}
