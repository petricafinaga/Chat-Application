/**
 * @author Finaga Petrica
 *
 * @version 1.0
 * @since 05-12-2019
 **/

package chat.client;

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
}
