/**
 * @author Finaga Petrica
 *
 * @version 1.0
 * @since 05-12-2019
 **/

package chat.client;

public class ClientConfig {
	private static final String fDefaultAlias = null;
	private static final String fDefaultServerName = "ChatServer";

	private String alias;
	private String serverName;

	public ClientConfig(String alias, String serverName) {
		this.alias = alias;
		this.serverName = serverName;
	}

	public void SetAlias(String alias) {
		this.alias = alias;
	}

	public void SetServerName(String serverName) {
		this.serverName = serverName;
	}

	public String GetAlias() {
		return this.alias;
	}

	public String GetServerName() {
		return this.serverName;
	}

	public static ClientConfig GetDefaultClientConfig() {
		return new ClientConfig(fDefaultAlias, fDefaultServerName);
	}
}
