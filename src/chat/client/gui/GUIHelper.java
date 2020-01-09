package chat.client.gui;

public class GUIHelper {

	public static boolean IsStringOnNewLine(String str) {
		return str.charAt(str.length() - 1) == '\n';
	}
}
