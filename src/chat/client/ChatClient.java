package chat.client;

import com.google.gson.Gson;

import jade.core.AID;

public class ChatClient {

	public static enum ClientStatus {
		Online, Idle, Offline
	}

	private AID aid;
	private String alias;
	private ClientStatus status;

	public ChatClient(AID aid, String alias, ClientStatus status) {

		this.aid = aid;
		this.alias = alias;
		this.status = status;
	}

	public AID getAID() {
		return this.aid;
	}

	public String getAlias() {
		return this.alias;
	}

	public ClientStatus getStatus() {
		return this.status;
	}

	public void SetAID(AID aid) {
		this.aid = aid;
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
		gson.toJson(this);

		System.out.println(gson.toString());

		return gson.toString();
	}
}
