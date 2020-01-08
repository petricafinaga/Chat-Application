/**
 * @author Finaga Petrica
 *
 * @version 1.0
 * @since 05-12-2019
 **/

package common;

import com.google.gson.Gson;

public class Message {

	public static enum MessageType {
		Subscribe, Unsubscribe, AllClients, ClientUpdate, TextMessage,
	}

	private MessageType messageType;
	private String message;

	public Message(MessageType type, String message) {
		this.messageType = type;
		this.message = message;
	}

	public MessageType getMessageType() {
		return this.messageType;
	}

	public String getMessage() {
		return this.message;
	}

	@Override
	public String toString() {
		final Gson gson = new Gson();
		return gson.toJson(this);
	}
}
