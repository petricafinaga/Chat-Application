/**
 * @author Finaga Petrica
 *
 * @version 1.0
 * @since 05-12-2019
 **/

package chat.client;

import chat.client.gui.WindowConfiguration;

public class ClientConfig {
	private static final String fDefaultAlias = null;
	private static final String fDefaultServerName = "ChatServer";
	private static final WindowConfiguration fDefaultWindowConfig = null;

	private String alias;
	private String serverName;
	private WindowConfiguration windowConfig;

	public ClientConfig(String alias, String serverName, WindowConfiguration windowConfig) {
		this.alias = alias;
		this.serverName = serverName;
		this.windowConfig = windowConfig;
	}

	public void SetAlias(String alias) {
		this.alias = alias;
	}

	public void SetServerName(String serverName) {
		this.serverName = serverName;
	}

	public void SetWindowConfiguration(WindowConfiguration windowConfig) {
		this.windowConfig = windowConfig;
	}

	public String GetAlias() {
		return this.alias;
	}

	public String GetServerName() {
		return this.serverName;
	}

	public WindowConfiguration GetWindowConfiguration() {
		return this.windowConfig;
	}

	public static ClientConfig GetDefaultClientConfig() {
		return new ClientConfig(fDefaultAlias, fDefaultServerName, fDefaultWindowConfig);
	}
}
