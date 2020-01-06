package chat.client;

public class ClientConfig {

	private String alias;
	private String serverAddress;

	public ClientConfig(String alias, String serverAddress) {
		this.alias = alias;
		this.serverAddress = serverAddress;
	}

	public void SetAlias(String alias) {
		this.alias = alias;
	}

	public void SetServerAddress(String serverAddress) {
		this.serverAddress = serverAddress;
	}

	public String GetAlias() {
		return this.alias;
	}

	public String GetServerAddress() {
		return this.serverAddress;
	}
}
