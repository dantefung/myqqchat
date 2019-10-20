package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;
import java.util.Map.Entry;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.ListCellRenderer;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import model.biz.AddFriendBiz;
import model.biz.BtnMouseMotionHandleBiz;
import model.biz.WavePlayerBiz;
import model.biz.dragHandleBiz;
import model.entity.userimformation;

import comondata.comondata;
import controller.MessageTypeEnum;
import controller.receivequestion;

public class MainView extends JFrame {
	private static final long serialVersionUID = -7791117576890422358L;

	private ChatList friendChatList;
	private ChatList groupChatList;
	private JLabel namelab;
	private JTabbedPane tabpan;
	private JPanel namepnl;
	private JButton closebtn;
	private JButton minibtn;
	private JTextArea addfriendtext;
	private JLabel headLab;
	private JLabel titleLab;

	public MainView() {
		try {
			UIManager.setLookAndFeel(UIManager
					.getCrossPlatformLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		this.setSize(300, 690);

		ImageIcon ninjaIcon = new ImageIcon("picture/ninja.png");
		titleLab = new JLabel(ninjaIcon);
		titleLab.setLocation(5, 0);
		titleLab.setSize(ninjaIcon.getIconWidth(), ninjaIcon.getIconHeight());

		namelab = new JLabel(comondata.getName());
		namelab.setFont(comondata.getTitleFont());

		tabpan = new JTabbedPane();
		namepnl = new JPanel(null);

		friendChatList = new ChatList();
		groupChatList = new ChatList();

		groupChatList.addItem("Ⱥ��");
		groupChatList.addMouseListener(new listMouseListener());

		ImageIcon headIcon = new ImageIcon("picture/ai.png");
		headLab = new JLabel(headIcon);
		headLab.setLocation(10, 35);
		headLab.setSize(headIcon.getIconWidth(), headIcon.getIconHeight());

		headLab.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseExited(MouseEvent arg0) {
				setCursor(Cursor.getDefaultCursor());
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON1) {
					WavePlayerBiz wavePlayer = new WavePlayerBiz("music/ai.wav");
					wavePlayer.play(new ByteArrayInputStream(wavePlayer.getSamples()));
					JOptionPane.showMessageDialog(null, "霤˻󤤤������Ӱ�衢�ˤ���Ĥ��H��ơ������줷�I�λꡢһ��...�����Ҋ�룿");
					wavePlayer.stop();
				}
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}
		});

		namelab.setLocation(headLab.getLocation().x + headLab.getSize().width
				+ 10, headLab.getLocation().y);
		namelab.setSize(getWidth() / 2, 15);

		ImageIcon closeIcon = new ImageIcon("picture/qqclose.png");
		closebtn = new JButton(closeIcon);
		closebtn.setLocation(getWidth() - closeIcon.getIconWidth(), 0);
		closebtn.setSize(closeIcon.getIconWidth(), closeIcon.getIconHeight());
		closebtn.setContentAreaFilled(false);
		closebtn.setBackground(Color.red);
		closebtn.setBorderPainted(false);
		closebtn.setFocusPainted(false);

		closebtn.addMouseListener(new BtnMouseMotionHandleBiz(this, closebtn,
				"close"));

		ImageIcon miniIcon = new ImageIcon("picture/qqmini.png");
		minibtn = new JButton(miniIcon);
		minibtn.setLocation(closebtn.getLocation().x - miniIcon.getIconWidth(),
				closebtn.getLocation().y);
		minibtn.setSize(miniIcon.getIconWidth(), miniIcon.getIconHeight());
		minibtn.setBorderPainted(false);
		minibtn.setFocusPainted(false);
		minibtn.setBackground(comondata.getFocusColor());
		minibtn.setContentAreaFilled(false);

		minibtn.addMouseListener(new BtnMouseMotionHandleBiz(this, minibtn,
				"minimization"));

		addfriendtext = new JTextArea("�������");
		addfriendtext.setFont(comondata.getInputFont());
		addfriendtext.setSize(getWidth(), 30);
		addfriendtext.setLocation(0, 105);
		addfriendtext.setBackground(comondata.getMainFocusColor());
		addfriendtext.setForeground(Color.white);
		addfriendtext.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
				if (!(e.getKeyChar() > KeyEvent.VK_0 && e.getKeyChar() <= KeyEvent.VK_9)) {
					e.consume();
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {

			}

			@Override
			public void keyPressed(KeyEvent e) {

			}
		});
		addfriendtext.addFocusListener(new FocusListener() {

			@Override
			public void focusLost(FocusEvent e) {
				addfriendtext.setBackground(comondata.getMainFocusColor());
				addfriendtext.setText("�������");
				addfriendtext.setForeground(Color.white);
			}

			@Override
			public void focusGained(FocusEvent e) {
				addfriendtext.setBackground(Color.white);
				addfriendtext.setForeground(Color.black);
				addfriendtext.setText("");
			}
		});
		addfriendtext.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
			}

			@Override
			public void keyReleased(KeyEvent e) {

			}

			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					System.out.println("typed");
					if (!addfriendtext.getText().equals("")) {
						AddFriendBiz.getAddFriendBiz()
								.addFriendRequest(
										Integer.valueOf(comondata.getId()
												.trim()),
										Integer.valueOf(addfriendtext.getText()
												.trim()));
					}
					addfriendtext.setText("");
				}
			}
		});
		namepnl.add(titleLab);
		namepnl.add(headLab);
		namepnl.add(namelab);
		namepnl.add(closebtn);
		namepnl.add(minibtn);
		namepnl.add(addfriendtext);
		namepnl.setBackground(comondata.getMainColor());

		JScrollPane friendpan = new JScrollPane();
		JScrollPane gruppan = new JScrollPane();

		friendChatList.addMouseListener(new listMouseListener(friendChatList));
		friendpan.setViewportView(friendChatList);

		gruppan.setViewportView(groupChatList);

		tabpan.setFocusable(false);
		tabpan.addTab("����", friendpan);
		tabpan.addTab("Ⱥ", gruppan);
		tabpan.setFont(comondata.getTabFont());
		tabpan.setBackground(Color.white);

		this.getContentPane().add(namepnl, BorderLayout.CENTER);
		this.getContentPane().add(tabpan, BorderLayout.SOUTH);

		this.getContentPane().setBackground(comondata.getMainColor());
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setTitle("���칤��");
		this.setUndecorated(true);
		dragHandleBiz dragAdp = new dragHandleBiz(this);
		this.addMouseListener(dragAdp);
		this.addMouseMotionListener(dragAdp);
	}

	/**
	 * �� CommonData �б���������û������ͬ�� �ṩ���ⲿ�����
	 */
	public void initUserList(HashMap<String, userimformation> OnlineUserMap) {
		Iterator<Entry<String, userimformation>> iter = OnlineUserMap
				.entrySet().iterator();

		while (iter.hasNext()) {
			Entry<String, userimformation> entry = (Entry<String, userimformation>) iter
					.next();
			userimformation onlineUserDetail = (userimformation) entry
					.getValue();

			// �Լ������
			if (!onlineUserDetail.getId().equals(comondata.getId())) {
				addOnlineUserItem(onlineUserDetail.getId() + "\n"
						+ onlineUserDetail.getName());
			}
		}
	}

	/**
	 * �� CommonData �б���������û������ͬ�� �ṩ���ⲿ�����
	 */
	public void initOffUserList(HashMap<String, userimformation> OnlineUserMap) {
		Iterator<Entry<String, userimformation>> iter = OnlineUserMap
				.entrySet().iterator();

		while (iter.hasNext()) {
			Entry<String, userimformation> entry = (Entry<String, userimformation>) iter
					.next();
			userimformation onlineUserDetail = (userimformation) entry
					.getValue();

			// �Լ������
			if (!onlineUserDetail.getId().equals(comondata.getId())) {
				addOfflineUserItem(onlineUserDetail.getId() + "\n"
						+ onlineUserDetail.getName());
			}
		}
	}

	/**
	 * ��list����������û�
	 * 
	 * @param nameStr_
	 */
	public void addOnlineUserItem(String nameStr_) {
		friendChatList.addItem(nameStr_);
		friendChatList.setUserOlineStatus(nameStr_);
	}

	/**
	 * ��list����������û�
	 * 
	 * @param nameStr_
	 */
	public void addOfflineUserItem(String nameStr_) {
		friendChatList.addItem(nameStr_);
		friendChatList.setUserOfflineStatus(nameStr_);
	}

	/**
	 * ��list���Ƴ��û�
	 * 
	 * @param nameStr_
	 */
	public void removeUserItem(String nameStr_) {
		friendChatList.removeItem(nameStr_);
	}

	/**
	 * �����û��б�(��дjlist�ؼ�)
	 * 
	 * @author KumaHime
	 */

	class ChatList extends JList<Object> {
		private static final long serialVersionUID = 4281799662638099298L;

		private UserListCellRenderer userlistuserlist = new UserListCellRenderer();

		private Vector<String> onlineUserVector = new Vector<>(); // list
																	// ����

		public ChatList() {
			// list�߶�
			this.setFixedCellHeight(65);

			this.setBackground(Color.white);

			this.setFont(comondata.getTitleWithSegoeFont());

			// ������Ⱦ��Ϊ�Զ������Ⱦ��
			this.setCellRenderer(userlistuserlist);

			// ����list����
			this.setListData(onlineUserVector);

		}

		public void setUserOlineStatus(String status) {
			userlistuserlist.setOnline(status);
		}

		public void setUserOfflineStatus(String status) {
			userlistuserlist.setOffline(status);
		}

		public void setUserB3Selected(String selected) {
			userlistuserlist.setB3Select(selected);
		}

		public boolean isUserOnline(String status) {
			return userlistuserlist.isOnline(status);
		}

		/**
		 * ��list�ṩ���item�Ľӿ�
		 * 
		 * @param nameStr_
		 */
		public void addItem(String nameStr_) {
			if (!onlineUserVector.contains(nameStr_)) {
				onlineUserVector.add(nameStr_);
			}
			this.setListData(onlineUserVector);
		}

		/**
		 * ��list�ṩɾ��item�Ľӿ�
		 * 
		 * @param nameStr_
		 */
		public void removeItem(String nameStr_) {
			onlineUserVector.remove(nameStr_);
			this.setListData(onlineUserVector);
		}
	}

	/**
	 * list ��Ⱦ��
	 * 
	 * @author KumaHime
	 * 
	 */
	class UserListCellRenderer extends JPanel implements
			ListCellRenderer<Object> {
		private static final long serialVersionUID = 3278668944817504772L;

		private String text; // list�ϵ�text
		private Color background; // bei jing se
		private Color foreground;
		private boolean online = false;
		private String B3SStr = "";
		private Vector<String> onlineList = new Vector<String>(); // ��¼�����û�

		public UserListCellRenderer() {
			this.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 30));

		}

		@Override
		public Component getListCellRendererComponent(JList<?> list,
				Object value, int index, boolean isSelected,
				boolean cellHasFocus) {
			text = (String) value;
			this.setFont(comondata.getInputFont());
			// background=isSelected?list.getSelectionBackground():list.getBackground();
			if (onlineList.contains(text) || text.equals("Ⱥ��")) {
				online = true;
			} else {
				online = false;
			}
			background = isSelected ? comondata.getItemFocusColor()
					: Color.white;
			foreground = isSelected ? list.getSelectionForeground() : list
					.getForeground();

			return this;
		}

		public void setSelectForeGround(Color fore) {
			foreground = fore;
		}

		public void setB3Select(String selectedStr) {
			B3SStr = selectedStr;
		}

		public void setOnline(String status) {
			if (!onlineList.contains(status)) {
				onlineList.add(status);
			}
		}

		public void setOffline(String status) {
			if (onlineList.contains(status)) {
				onlineList.remove(status);
			}
		}

		public boolean isOnline(String status) {
			return onlineList.contains(status);
		}

		/**
		 * �ػ游�����
		 */
		@Override
		public void paintComponent(Graphics g) {
			g.setColor(background);
			g.fillRect(0, 0, getWidth(), getHeight());
			g.setColor(foreground);
			try {
				if (online) {
					Image ryuko = ImageIO.read(new File("picture/ryuko1.png"));
					g.drawImage(ryuko, 0, getHeight() / 2 - 23, null);
				} else {
					Image ryuko = ImageIO.read(new File(
							"picture/ryuko1_gray.png"));
					g.drawImage(ryuko, 0, getHeight() / 2 - 23, null);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (text.equals("Ⱥ��")) {
				g.drawString(text, (getWidth() - 50) / 2,
						(getHeight() - 10) / 2);
			} else {
				g.drawString("�˺ţ�" + text.split("\n")[0],
						(getWidth() - 50) / 2, (getHeight() - 10) / 2);
				g.drawString("�û����� " + text.split("\n")[1],
						(getWidth() - 50) / 2, (getHeight() - 10) / 2
								+ this.getFont().getSize());
			}
		}

		@Override
		public Dimension getPreferredSize() {
			return new Dimension(130, 120); // Cell�ĳߴ�
		}

	}

	/**
	 * list ������
	 * 
	 * @author KumaHime
	 * 
	 */
	class listMouseListener extends MouseAdapter {
		private JPopupMenu jpop = new JPopupMenu();
		private ChatList clist = null;
		private boolean friendChat;
		private String id_now;
		private String name_now;

		public listMouseListener() {
			friendChat = false;
		}

		public listMouseListener(ChatList chatlist) {
			friendChat = true;
			clist = chatlist;
			JMenuItem friendItem = new JMenuItem("ɾ������");
			friendItem.addMouseListener(new MouseAdapter() {

				@Override
				public void mouseReleased(MouseEvent e) {
					if (id_now.equals("0")) {
						JOptionPane.showMessageDialog(null, "ϵͳ���Ѳ���ɾ����");
						return;
					}
					if (e.getButton() == MouseEvent.BUTTON1) {
						receivequestion
								.getMainSocket()
								.getServerThread()
								.sendData(
										MessageTypeEnum.RemoveFriend.toString()
												+ ":::"
												+ Integer.valueOf(comondata
														.getId()) + ":"
												+ id_now);
					}
				}

			});
			jpop.add(friendItem);
		}

		@Override
		public void mouseClicked(MouseEvent e) {

			JList<?> userList = (JList<?>) e.getSource();

			String value = userList.getModel()
					.getElementAt(userList.locationToIndex(e.getPoint()))
					.toString().trim();
			if (value != "Ⱥ��") {
				id_now = value.split("\n")[0];
				name_now = value.split("\n")[1];
			}
			if (e.getButton() == MouseEvent.BUTTON3 && friendChat == true) {
				clist.setSelectedValue(value, true);
				jpop.show(clist, e.getX(), e.getY());
			}
			if (e.getClickCount() == 2) {
				int index = userList.locationToIndex(e.getPoint());
				if (index >= 0) {
					// Ⱥ��
					if (e.getSource() == groupChatList) {
						System.out.println("Ⱥ��");
						comondata.getGroupChatFrame().setVisible(true);
					}

					// һ��һ����
					else {
						String userName = userList.getModel()
								.getElementAt(index).toString().trim();
						if (clist.isUserOnline(userName)) {

							if (comondata.getChatFrameMap().get(userName) == null) {
								// ��ӵ������û���
								comondata.getChatFrameMap().put(userName,
										new ChatView(userName));
							}

							comondata.getChatFrameMap().get(userName)
									.setVisible(true);
						} else {
							JOptionPane.showMessageDialog(null, "�û����ߣ��޷����ͣ�");
						}
					}
				}
			}
		}
	}

	// ������
	public static void main(String[] args) {
		MainView m = new MainView();
		m.setVisible(true);
		m.addOnlineUserItem("1\na");
		m.addOnlineUserItem("2\nb");
	}
}
