package chat.client;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;

import javax.swing.JTextPane;
import javax.swing.JButton;

import java.awt.EventQueue;
import java.awt.Font;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

@SuppressWarnings("serial")
public class ClientGUI extends JFrame {

	private JPanel contentPane;

	JTextPane currentMessage;
	JList<String> chatList;
	JList<String> ongoingMessages;
	JButton sendButton;
	JLabel talkingNow;

	DefaultListModel<String> users_model;

//	String[] all_users = new String[] { "user1", "user2", "user3" };

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
	public ClientGUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 903, 486);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		users_model = new DefaultListModel<String>();

		chatList = new JList<>();
		chatList.setValueIsAdjusting(true);
		chatList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		chatList.setModel(users_model);
//		chatList.setModel(new AbstractListModel<String>() {
//			String[] values = new String[] { "asdasd", "asd", "asd", "as", "d", "asdasd" };
//
//			@Override
//			public int getSize() {
//				return values.length;
//			}
//
//			@Override
//			public String getElementAt(int index) {
//				return values[index];
//			}
//		});
		chatList.setBounds(10, 16, 171, 420);
		contentPane.add(chatList);

		currentMessage = new JTextPane();
		currentMessage.setBounds(191, 397, 572, 39);
		contentPane.add(currentMessage);

		sendButton = new JButton("");

		sendButton.setIcon(new ImageIcon(
				"D:\\eclipse-java-2019-09-R-win32-x86_64\\_workspace\\Chat-Application/assets\\send-icon.png"));
		sendButton.setFont(new Font("Rubik", Font.PLAIN, 20));
		sendButton.setBounds(773, 397, 104, 39);
		contentPane.add(sendButton);

		ongoingMessages = new JList<String>();
		ongoingMessages.setBounds(191, 43, 572, 343);
		ongoingMessages.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		contentPane.add(ongoingMessages);

		talkingNow = new JLabel("User ID");
		talkingNow.setHorizontalAlignment(SwingConstants.CENTER);
		talkingNow.setFont(new Font("Tahoma", Font.PLAIN, 20));
		talkingNow.setBounds(191, 8, 572, 25);
		contentPane.add(talkingNow);

		sendButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent arg0) {
				setUsers(new String[] { "Petrica", "JOro", "Iulica" });
			}
		});

	}

	/*
	 * Add user IDs to the Users List
	 */
	public void setUsers(String[] users) {
		users_model.removeAllElements();
		for (String user : users) {
			users_model.addElement(user);
		}
	}
}
