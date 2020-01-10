package chat.client.gui;

public class GUIHelper {

	public static boolean IsStringEndingWithNewLine(String str) {
		return (str.length() > 0 ? str.charAt(str.length() - 1) == '\n' : true);
	}

	public static boolean IsStringEmpty(String str) {
		return str.equals("");
	}
}
