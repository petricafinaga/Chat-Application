package common;

import com.google.gson.Gson;

public class Utils {
	static final Gson gson = new Gson();

	public static <T> String ToJson(T container) {
		return gson.toJson(container);
	}

	public static <T> T ToObject(String json) {
		return gson.fromJson(json, T.class);
	}
}
