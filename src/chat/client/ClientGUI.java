package chat.client;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.JTextPane;
import javax.swing.JButton;

import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Vector;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import common.Message;
import common.Message.MessageType;

import java.awt.Color;
import java.awt.Dimension;

@SuppressWarnings("serial")
public class ClientGUI extends JFrame {

	private JPanel contentPane;
	ChatClient chatClient;
	ClientAgent clientAgent;

	JTextPane currentMessage;
	JButton sendButton;
	JLabel talkingNowLabel;
	JTable usersTable;
	JScrollPane messagesScrollPane;
	JScrollPane usersScrollPane;
	JScrollPane currentMessageScrollPane;
	JLabel updatesLabel;
	JTextPane messagesTextPane;
	StyledDocument messagesDoc;
	Style receivedMessageStyle;
	Style myMessageStyle;
	Style userNameStyle;
	
	Color myMessageColor = Color.black;
	Color receivedMessageColor = Color.orange;
	

	String talkingNowClient = null;

	DefaultTableModel usersTableModel;

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					ClientGUI frame = new ClientGUI();
//					frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	/**
	 * Create the frame.
	 */
	public ClientGUI(ClientAgent a) {

		super(a.getLocalName());
		clientAgent = a;

		setBounds(100, 100, 851, 486);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

//		Capture GUI close event
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
//				TODE delete agent
				clientAgent.doDelete();
			}
		});

//		Text input to insert the message
		currentMessage = new JTextPane();
		currentMessage.setBounds(10, 397, 474, 39);
		currentMessageScrollPane = new JScrollPane(currentMessage);
		currentMessageScrollPane.setBounds(10, 397, 474, 39);
		contentPane.add(currentMessageScrollPane);

//		Button to send the message
		sendButton = new JButton("Send");
		sendButton.setIcon(null);
		sendButton.setFont(new Font("Rubik", Font.PLAIN, 20));
		sendButton.setBounds(494, 397, 88, 39);
		contentPane.add(sendButton);

//		Label to display who am I talking with
		talkingNowLabel = new JLabel("User ID");
		talkingNowLabel.setHorizontalAlignment(SwingConstants.CENTER);
		talkingNowLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		talkingNowLabel.setBounds(10, 11, 572, 25);
		contentPane.add(talkingNowLabel);

//		Table to show users and their status
		usersTableModel = new DefaultTableModel(0, 0);
		String header[] = new String[] { "ID", "Status", "agentName" }; // agent Name is hidden in the table
		usersTableModel.setColumnIdentifiers(header);
		usersTable = new JTable(usersTableModel) {
//			Disables the cell editing feature in the users table
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		usersTable.setLocation(592, 11);
		usersTable.setSize(new Dimension(230, 403));
		usersTable.setTableHeader(null);
		usersTable.setDefaultEditor(Object.class, null);
		usersTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				// TODO Auto-generated method stub
				talkingNowClient = usersTableModel.getValueAt(usersTable.getSelectedRow(), 2).toString();
				talkingNowLabel.setText(usersTableModel.getValueAt(usersTable.getSelectedRow(), 0).toString());

			}
		});

		usersTable.getColumnModel().getColumn(2).setMinWidth(0);
		usersTable.getColumnModel().getColumn(2).setMaxWidth(0);
		usersTable.getColumnModel().getColumn(2).setWidth(0);
		usersScrollPane = new JScrollPane(usersTable);
		usersScrollPane.setBounds(592, 11, 230, 403);
		contentPane.add(usersScrollPane);

//		Label to display latest updates
		updatesLabel = new JLabel("No new updates");
		updatesLabel.setBounds(592, 422, 230, 14);
		contentPane.add(updatesLabel);

//		Text Pane to show all messages in current conversation
		messagesTextPane = new JTextPane();
		messagesTextPane.setBounds(10, 47, 572, 339);
		messagesTextPane.setEditable(false);
		
		messagesDoc = messagesTextPane.getStyledDocument();
		
		receivedMessageStyle = messagesTextPane.addStyle("", null);
		myMessageStyle = messagesTextPane.addStyle("", null);
		userNameStyle = messagesTextPane.addStyle("", null);
		StyleConstants.setForeground(receivedMessageStyle, receivedMessageColor);
		StyleConstants.setForeground(myMessageStyle, myMessageColor);
		StyleConstants.setBold(userNameStyle, true);
		
		messagesScrollPane = new JScrollPane(messagesTextPane);
		messagesScrollPane.setBounds(10, 47, 572, 339);
		contentPane.add(messagesScrollPane);

//		Send the message to the highlighted user
		sendButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent arg0) {
				Message msg = new Message(MessageType.TextMessage, currentMessage.getText());
				if (talkingNowClient == null)
					JOptionPane.showMessageDialog(null, "Please select a user from the list!");
				else {
					clientAgent.SendMessage(talkingNowClient, msg);
					StyleConstants.setForeground(userNameStyle, myMessageColor);
					try {
						
						messagesDoc.insertString(messagesDoc.getLength(), "Me: ",userNameStyle);
						messagesDoc.insertString(messagesDoc.getLength(), currentMessage.getText() + "\n",
								myMessageStyle);
					} catch (BadLocationException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				currentMessage.setText("");
			}
		});

	}

	public void GUIAddUsers(ChatClient[] clients) {
		for (ChatClient chatClient : clients) {
			Vector<String> data = new Vector<String>();
			data.add(chatClient.getAlias());
			data.add(chatClient.getStatus().toString());
			data.add(chatClient.getName());
			usersTableModel.addRow(data);
		}
	}

	public void GUIAddOrModifyUserStatus(ChatClient client) {
		boolean found = false;
		for (int i = 0; i < usersTableModel.getRowCount(); i++) {
			if (usersTableModel.getValueAt(i, 0).equals(client.getAlias())) {
				usersTableModel.setValueAt(client.getStatus().toString(), i, 1);
				found = true;
			}
		}
		if (!found) {
			Vector<String> data = new Vector<String>();
			data.add(client.getAlias());
			data.add(client.getStatus().toString());
			data.add(client.getName());
			usersTableModel.addRow(data);
		}
		updatesLabel.setText(client.getAlias() + " went " + client.getStatus().toString());
	}

	public void GUIDisplayReceivedMessage(String clientName, String message) {
		String clientAlias = "";
		for (int i = 0; i < usersTableModel.getRowCount(); i++) {
			if (usersTableModel.getValueAt(i, 2).equals(clientName)) {
				clientAlias = usersTableModel.getValueAt(i, 0).toString();
			}
		}
		
		StyleConstants.setForeground(userNameStyle, receivedMessageColor);
		try {
			messagesDoc.insertString(messagesDoc.getLength(), clientAlias + ": ", userNameStyle);
			messagesDoc.insertString(messagesDoc.getLength(), message + "\n", receivedMessageStyle);
		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
