package chat.client.gui;

import java.awt.Color;

public class WindowConfiguration {

	private Color receivedMessageColor;
	private Color myMessageColor;
	private Color windowColor;
	private int fontSize;
	private String fontName;

	public WindowConfiguration() {

		this.receivedMessageColor = Color.red;
		this.myMessageColor = Color.black;
		this.fontSize = 14;
	}
	
	
	// My message color
	public void setMyMessageColor(Color _myMsgColor) {
		this.myMessageColor = _myMsgColor;
	}
	
	public Color getMyessageColor() {
		return this.myMessageColor;
	}
	
	// Received message color
	public void setReceivedMessageColor(Color _recvMsgColor) {
		this.receivedMessageColor = _recvMsgColor;
	}
	
	public Color getReceivedMessageColor() {
		return this.receivedMessageColor;
	}
	
	// Window color
	public void setWindowColor(Color _windowColor) {
		this.windowColor = _windowColor;
	}
	
	public Color getWindowColor() {
		return this.windowColor;
	}
	
	// Font size
	public void setFontSize(int _fontSize) {
		this.fontSize = _fontSize;
	}
	
	public int getFontSize()
	{
		return this.fontSize;
	}
	
	// Font name
	public void setFontName(String _fontName) {
		this.fontName = _fontName;
	}
	
	public String getFontName() {
		return this.fontName;
	}

}
