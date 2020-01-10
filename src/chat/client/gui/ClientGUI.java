/**
 * @author Acroitoritei Calin
 * @author Finaga Petrica
 * @version 1.0
 * @since 05-12-2019
 **/

package chat.client.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;

import chat.client.ChatClient;
import chat.client.ClientAgent;
import common.Message;
import common.Message.MessageType;

@SuppressWarnings("serial")
public class ClientGUI extends JFrame {

	private JPanel contentPane;
	private ClientAgent myAgent;
	private JTextPane currentMessage;
	private JButton sendButton;
	private JLabel talkingNowLabel;
	private JLabel myUserNameLabel;
	private JLabel updatesLabel;
	private JTable usersTable;
	private JScrollPane messagesScrollPane;
	private JScrollPane usersScrollPane;
	private JScrollPane currentMessageScrollPane;
	private JTextPane messagesTextPane;
	private Style receivedMessageStyle;
	private Style myMessageStyle;
	private Color myMessageColor = Color.black;
	private Color receivedMessageColor = Color.red;
	private Color windowColor;
	private String talkingNowClientName = null;
	private String talkingNowClientAlias = null;
	private String myAlias = "defaultUsername";
	private DefaultTableModel usersTableModel;
	private Map<String, DefaultStyledDocument> usersMessages;
	private JScrollBar messagesScrollBar;
	private JMenuBar menuBar;
	private JMenu colorsMenu;
	private JMenuItem receivedMessageColorMenuItem, sentMessageColorMenuItem, windowColorMenuItem;

	/**
	 * Create the frame.
	 */

	public ClientGUI(ClientAgent agent, String alias) {

		this.myAgent = agent;
		if (alias != null) {
			myAlias = alias;
		} else {
			myAlias = JOptionPane.showInputDialog(contentPane, "Enter your username");
			while (myAlias == null || GUIHelper.IsStringEmpty(myAlias.trim())) {
				myAlias = JOptionPane.showInputDialog(contentPane, "Enter your username");
			}
			myAgent.UpdateAlias(myAlias);
		}

		usersMessages = new HashMap<String, DefaultStyledDocument>();

		// Window styling
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);

		this.setResizable(false);
		this.setTitle(myAlias);
		this.setBounds(100, 100, 851, 509);
		this.setContentPane(contentPane);

		// Capture GUI close event
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				myAgent.doDelete();
			}
		});

		windowColor = contentPane.getBackground();
		// Add menubar to the interface
		menuBar = new JMenuBar();
		this.setJMenuBar(menuBar);
		// Insert the menu and the according submenus into the menubar
		colorsMenu = new JMenu("Colors");
		receivedMessageColorMenuItem = new JMenuItem("Received message color");
		sentMessageColorMenuItem = new JMenuItem("Sent message color");
		windowColorMenuItem = new JMenuItem("Window color");
		colorsMenu.add(receivedMessageColorMenuItem);
		colorsMenu.add(sentMessageColorMenuItem);
		colorsMenu.add(windowColorMenuItem);
		menuBar.add(colorsMenu);

		// Add event listeners on menu items
		receivedMessageColorMenuItem.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				receivedMessageColor = JColorChooser.showDialog(null, "Pick a color for received messages",
						receivedMessageColor);
				if (receivedMessageColor != null)
					StyleConstants.setForeground(receivedMessageStyle, receivedMessageColor);
			}
		});
		sentMessageColorMenuItem.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				myMessageColor = JColorChooser.showDialog(null, "Pick a color for received messages", myMessageColor);
				if (myMessageColor != null)
					StyleConstants.setForeground(myMessageStyle, myMessageColor);
			}
		});
		windowColorMenuItem.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				windowColor = JColorChooser.showDialog(null, "Pick a color for window background", windowColor);
				if (windowColor != null)
					contentPane.setBackground(windowColor);
			}
		});

		// Text input to insert the message
		currentMessage = new JTextPane();
		currentMessage.setBounds(10, 397, 474, 39);
		currentMessage.getDocument().putProperty("filterNewlines", Boolean.TRUE);
		currentMessage.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
			}

			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER)
					sendButton.doClick();
			}

			@Override
			public void keyPressed(KeyEvent e) {
			}
		});

		currentMessageScrollPane = new JScrollPane(currentMessage);
		currentMessageScrollPane.setBounds(10, 397, 474, 39);
		contentPane.add(currentMessageScrollPane);

		myUserNameLabel = new JLabel(myAlias);
		myUserNameLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		myUserNameLabel.setBounds(10, 11, 236, 25);
		contentPane.add(myUserNameLabel);

		// Label to display who am I talking with
		talkingNowLabel = new JLabel();
		talkingNowLabel.setHorizontalAlignment(SwingConstants.CENTER);
		talkingNowLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		talkingNowLabel.setBounds(256, 11, 326, 25);
		contentPane.add(talkingNowLabel);

		String tableHeader[] = new String[] { "ID", "Status", "agentName" }; // agent Name is hidden in the table
		// Table to show users and their status
		usersTableModel = new DefaultTableModel(0, 0);
		usersTableModel.setColumnIdentifiers(tableHeader);

		usersTable = new JTable(usersTableModel) {
			// Disables the cell editing feature in the users table
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		usersTable.setLocation(592, 11);
		usersTable.setSize(new Dimension(230, 403));
		usersTable.setTableHeader(null);
		usersTable.setDefaultEditor(Object.class, null);
		usersTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		usersTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				talkingNowClientName = usersTableModel.getValueAt(usersTable.getSelectedRow(), 2).toString();
				talkingNowClientAlias = usersTableModel.getValueAt(usersTable.getSelectedRow(), 0).toString();
				talkingNowLabel.setText("Chatting with " + talkingNowClientAlias);
				messagesTextPane.setStyledDocument(usersMessages.get(talkingNowClientAlias));
			}
		});
		usersTable.getColumnModel().getColumn(2).setMinWidth(0);
		usersTable.getColumnModel().getColumn(2).setMaxWidth(0);
		usersTable.getColumnModel().getColumn(2).setWidth(0);

		usersScrollPane = new JScrollPane(usersTable);
		usersScrollPane.setBounds(592, 11, 230, 403);
		contentPane.add(usersScrollPane);

		// Label to display latest updates
		updatesLabel = new JLabel("No new updates");
		updatesLabel.setBounds(592, 422, 230, 14);
		contentPane.add(updatesLabel);

		// Text Pane to show all messages in current conversation
		messagesTextPane = new JTextPane();
		messagesTextPane.setBounds(10, 47, 572, 339);
		messagesTextPane.setEditable(false);

		// Styles to show different messages
		receivedMessageStyle = messagesTextPane.addStyle("", null);
		myMessageStyle = messagesTextPane.addStyle("", null);
		
		StyleConstants.setForeground(receivedMessageStyle, receivedMessageColor);
		StyleConstants.setAlignment(receivedMessageStyle, StyleConstants.ALIGN_LEFT);
		StyleConstants.setFontFamily(receivedMessageStyle, "Rubik");
		
		StyleConstants.setForeground(myMessageStyle, myMessageColor);
		StyleConstants.setAlignment(myMessageStyle, StyleConstants.ALIGN_RIGHT);
		StyleConstants.setFontFamily(myMessageStyle, "Rubik");

		messagesScrollPane = new JScrollPane(messagesTextPane);
		messagesScrollBar = messagesScrollPane.getVerticalScrollBar();
		messagesScrollPane.setBounds(10, 47, 572, 339);
		contentPane.add(messagesScrollPane);

		// Button to send the message
		sendButton = new JButton("Send");
		sendButton.setFont(new Font("Rubik", Font.PLAIN, 20));
		sendButton.setBounds(494, 397, 88, 39);
		sendButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {

				if (talkingNowClientName == null) {
					JOptionPane.showMessageDialog(null, "Please select a user from the list!");
				} else {
					String messageContent = currentMessage.getText();
					if (GUIHelper.IsStringEmpty(messageContent.trim())) {
						currentMessage.setText("");
						return;
					}

					Message msg = new Message(MessageType.TextMessage, messageContent);
					myAgent.SendMessage(talkingNowClientName, msg);

					
					AddMessageToMessageList(talkingNowClientAlias, messageContent, myMessageStyle);
					currentMessage.setText("");
				}
			}
		});
		contentPane.add(sendButton);
	}

	// Add all users into the list
	public void GUIOnAllClients(ChatClient[] clients) {
		this.AddClientsToTableModel(clients);
	}

	// Alter the status of an existing user or add a new user to the list
	public void GUIOnClientUpdate(ChatClient client) {

		updatesLabel.setText(client.getAlias() + " went " + client.getStatus().toString());
		for (int i = 0; i < usersTableModel.getRowCount(); i++) {
			if (usersTableModel.getValueAt(i, 0).equals(client.getAlias())) {
				usersTableModel.setValueAt(client.getStatus().toString(), i, 1);
				return;
			}
		}

		ChatClient[] clients = { client };
		this.AddClientsToTableModel(clients);
	}

	// Display the received message
	public void GUIOnTextMessage(String clientName, String message) {
		String clientAlias = "";
		for (int i = 0; i < usersTableModel.getRowCount(); i++) {
			if (usersTableModel.getValueAt(i, 2).equals(clientName)) {
				clientAlias = usersTableModel.getValueAt(i, 0).toString();
				usersTable.getSelectionModel().setSelectionInterval(i, i);
				break;
			}
		}

		AddMessageToMessageList(clientAlias, message, receivedMessageStyle);
	}

	private void AddClientsToTableModel(ChatClient[] clients) {

		for (ChatClient chatClient : clients) {
			final Vector<String> data = new Vector<String>();

			data.add(chatClient.getAlias());
			data.add(chatClient.getStatus().toString());
			data.add(chatClient.getName());
			usersTableModel.addRow(data);

			// Add messages into the list
			usersMessages.put(chatClient.getAlias(), new DefaultStyledDocument());
		}
	}

	private void AddMessageToMessageList(String alias, String message, Style messageStyle) {

		try {
			DefaultStyledDocument messages = usersMessages.get(alias);

			if (!GUIHelper.IsStringEndingWithNewLine(message)) {
				message += "\n";
			}
			messages.setLogicalStyle(messages.getLength(), messageStyle);
			messages.insertString(messages.getLength(), message, messageStyle);

		} catch (BadLocationException e) {
			e.printStackTrace();
		} finally {
			messagesScrollBar.setValue(messagesScrollBar.getMaximum());
		}
	}
}
