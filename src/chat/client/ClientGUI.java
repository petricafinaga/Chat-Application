package chat.client;

import javax.swing.AbstractListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;

public class ClientGUI extends JFrame {

	private JPanel contentPane;

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

		JList chatList = new JList();
		chatList.setValueIsAdjusting(true);
		chatList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		chatList.setModel(new AbstractListModel() {
			String[] values = new String[] { "asdasd", "asd", "asd", "as", "d", "asdasd" };

			@Override
			public int getSize() {
				return values.length;
			}

			@Override
			public Object getElementAt(int index) {
				return values[index];
			}
		});
		chatList.setBounds(704, 11, 171, 420);
		contentPane.add(chatList);
	}
}
