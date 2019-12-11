package chat.client;

import com.google.gson.Gson;

public class ChatClient {

	public static enum ClientStatus {
		Online, Idle, Offline
	}

	private String name;
	private String alias;
	private ClientStatus status;

	public ChatClient(String name, String alias, ClientStatus status) {

		this.name = name;
		this.alias = alias;
		this.status = status;
	}

	public String getName() {
		return this.name;
	}

	public String getAlias() {
		return this.alias;
	}

	public ClientStatus getStatus() {
		return this.status;
	}

	public void SetName(String name) {
		this.name = name;
	}

	public void SetAlias(String alias) {
		this.alias = alias;
	}

	public void SetStatus(ClientStatus status) {
		this.status = status;
	}

	@Override
	public String toString() {
		Gson gson = new Gson();
		return gson.toJson(this);
	}
}
