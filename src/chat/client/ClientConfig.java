package chat.client;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class ClientConfig {
	private static final String defaultAlias = null;

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

	public static ClientConfig GetDefaultClientConfig() {

		String localAddress = "";
		try {
			localAddress = InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}

		return new ClientConfig(defaultAlias, localAddress);
	}
}
