package chat.client;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextPane;
import javax.swing.JButton;

import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Vector;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
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
	JTextPane messagesTextArea;

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
		String header[] = new String[] { "ID", "Status" };
		usersTableModel.setColumnIdentifiers(header);
		usersTable = new JTable(usersTableModel);
		usersTable.setLocation(592, 11);
		usersTable.setSize(new Dimension(230, 403));
		usersTable.setTableHeader(null);
		usersScrollPane = new JScrollPane(usersTable);
		usersScrollPane.setBounds(592, 11, 230, 403);
		contentPane.add(usersScrollPane);

//		Label to display latest updates
		updatesLabel = new JLabel("No new updates");
		updatesLabel.setBounds(592, 422, 230, 14);
		contentPane.add(updatesLabel);

//		Text Pane to show all messages in current conversation
		messagesTextArea = new JTextPane();
		messagesTextArea.setBounds(10, 47, 572, 339);
		messagesScrollPane = new JScrollPane(messagesTextArea);
		messagesScrollPane.setBounds(10, 47, 572, 339);
		contentPane.add(messagesScrollPane);

		sendButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent arg0) {

			}
		});

	}

	public void GUIAddUsers(ChatClient[] clients) {
		for (ChatClient chatClient : clients) {
			Vector<String> data = new Vector<String>();
			data.add(chatClient.getAlias());
			data.add(chatClient.getStatus().toString());
			usersTableModel.addRow(data);
		}
	}

	public void GUIAddOrModifyUserStatus(ChatClient client) {
		boolean found = false;
		for (int i = 0; i < usersTableModel.getRowCount(); i++) {
			if (usersTableModel.getValueAt(i, 0).equals(client.getAlias())) {
				usersTableModel.setValueAt(client.getStatus().toString(), i, 1);
				updatesLabel.setText(client.getAlias() + " went " + client.getStatus().toString());
				found = true;
			}
		}
		if (!found) {
			Vector<String> data = new Vector<String>();
			data.add(client.getAlias());
			data.add(client.getStatus().toString());
			usersTableModel.addRow(data);
			updatesLabel.setText(client.getAlias() + " went " + client.getStatus().toString());
		}
	}
}
