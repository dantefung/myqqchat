package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.ScrollPane;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.Border;
import javax.swing.plaf.ScrollBarUI;
import javax.swing.plaf.basic.BasicScrollBarUI;
import javax.swing.text.BadLocationException;
import javax.swing.text.StyleConstants;

import com.sun.corba.se.impl.orb.ParserTable.TestAcceptor1;
import com.sun.org.apache.bcel.internal.generic.JSR;

import sun.awt.image.ImagingLib;

import comondata.comondata;
import controller.MessageTypeEnum;
import controller.receivequestion;

public class ChatView extends JFrame implements ActionListener {
	private JButton sendbtn;
	private JButton cancelbtn;
	private JButton filebtn;
	private JButton fontbtn;
	private JLabel nameLabel;
	private JPanel titlePanel;
	private JPanel itempan;
	private ChatPanel chatpnl;
	private JPanel centerPanel;
	private JPanel btnPanel;
	private JScrollPane receivepan;
	private JScrollPane sendpan;
	private String userId;
	private String chatRecordsStr;
	private ChatView parentFrame;
	private boolean isSingleChat;
	private JTextPane inputTA;
	private Font inputFont;
	private JLabel titlePctLabel;
	private JPopupMenu popmenu = new JPopupMenu();

	private int screenWidth = java.awt.Toolkit.getDefaultToolkit()
			.getScreenSize().width;
	private int screenHeight = java.awt.Toolkit.getDefaultToolkit()
			.getScreenSize().height;
	private Color qqColor = new Color(232, 236, 247);
	private Color borderColor = new Color(215, 217, 227);
	private Color PaleTurquoise1 = new Color(6, 157, 213);
	private JFrame mainFrame = this;
	private JLabel aokilabel;

	public ChatView() {

		isSingleChat = false;

		// 设置标题
		nameLabel = new JLabel("群聊");

		// 初始化界面
		InterFace();

		// 初始化基本参数
		initBasicData();
	}

	public ChatView(String usrn) {

		isSingleChat = true;
		nameLabel = new JLabel("交谈方：" + usrn.split("\n")[1]);

		userId = usrn.split("\n")[0];
		InterFace();
	}

	public void InterFace() {
		try {
			UIManager.setLookAndFeel(UIManager
					.getCrossPlatformLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}

		JMenuItem paste = new JMenuItem("粘贴");
		JMenuItem copy = new JMenuItem("复制");
		copy.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON1) {
					String temp = inputTA.getSelectedText();
					StringSelection text = new StringSelection(temp);
					getToolkit().getSystemClipboard().setContents(text, null); // 第二个参数为剪贴板的拥有者，对于文本的复制或剪切可不考
				}
			}
		});
		paste.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON1) {
					Transferable contents = getToolkit().getSystemClipboard()
							.getContents(null);
					if (contents.isDataFlavorSupported(DataFlavor.stringFlavor)) {
						try {
							inputTA.replaceSelection((String) contents
									.getTransferData(DataFlavor.stringFlavor));
						} catch (UnsupportedFlavorException | IOException e1) {
							e1.printStackTrace();
						}
					}

				}
			}
		});
		popmenu.add(copy);
		popmenu.add(paste);

		nameLabel.setFont(comondata.getTitleFont());
		nameLabel.setBackground(qqColor);

		ImageIcon tImIcon = new ImageIcon("picture/moe.jpg");
		titlePctLabel = new JLabel(tImIcon);

		ImageIcon fontIcon = new ImageIcon("picture/font.png");
		fontbtn = new JButton(fontIcon);

		ImageIcon fileIcon = new ImageIcon("picture/file.png");
		filebtn = new JButton(fileIcon);

		inputFont = new Font(null, Font.PLAIN, 12);

		inputTA = new JTextPane();
		// inputTA.setLineWrap(true);

		sendbtn = new JButton("发送");
		cancelbtn = new JButton("关闭");

		sendbtn.setActionCommand("发送");
		sendbtn.setFont(comondata.getBtnFont());
		sendbtn.setBackground(PaleTurquoise1);
		sendbtn.setBorderPainted(false);
		sendbtn.setFocusable(false);
		sendbtn.addActionListener(this);

		cancelbtn.setActionCommand("关闭");
		cancelbtn.setFont(comondata.getBtnFont());
		cancelbtn.setBackground(PaleTurquoise1);
		cancelbtn.setBorderPainted(false);
		cancelbtn.setFocusable(false);
		cancelbtn.addActionListener(this);

		fontbtn.setActionCommand("字体设置");
		fontbtn.setBackground(qqColor);
		fontbtn.setBorderPainted(false);
		fontbtn.setFocusable(false);
		fontbtn.addActionListener(this);

		filebtn.setActionCommand("文件发送");
		filebtn.setBackground(qqColor);
		filebtn.setBorderPainted(false);
		filebtn.setFocusable(false);
		filebtn.addActionListener(this);

		FlowLayout fl = new FlowLayout(6);
		fl.setHgap(10);
		btnPanel = new JPanel(fl);
		btnPanel.add(sendbtn);
		btnPanel.add(cancelbtn);
		btnPanel.setBackground(qqColor);

		titlePanel = new JPanel(fl);
		titlePanel.add(titlePctLabel);
		titlePanel.add(nameLabel);
		titlePanel.setBackground(qqColor);

		chatpnl = new ChatPanel();
		receivepan = new JScrollPane(chatpnl);
		receivepan.getHorizontalScrollBar().setUI(new BasicScrollBarUI());
		receivepan.getVerticalScrollBar().setUI(new BasicScrollBarUI());
		receivepan
				.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		receivepan
				.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		receivepan.setBorder(BorderFactory.createLineBorder(borderColor));
		receivepan.setSize(380, 300);

		itempan = new JPanel(null);
		fontbtn.setSize(20, 20);
		fontbtn.setLocation(0, 0);
		filebtn.setSize(fontbtn.getSize());
		filebtn.setLocation(fontbtn.getLocation().x + fontbtn.getSize().width,
				fontbtn.getLocation().y);
		itempan.add(filebtn);
		itempan.add(fontbtn);
		itempan.setSize(380, 30);
		itempan.setLocation(0, 300);
		itempan.setBackground(qqColor);

		inputTA.setBackground(qqColor);
		inputTA.setFont(new Font(comondata.getHtmlFontName(), comondata
				.getHtmlFontStyle(), comondata.getHtmlFontSize()));
		inputTA.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON3) {
					popmenu.show(inputTA, e.getX(), e.getY());
				}
			}
		});
		inputTA.addKeyListener(new KeyListener() {
			private String kao = "";
			private boolean sep = false;

			private void showExpression(int x, int y) {
				JPopupMenu ExPopMenu = new JPopupMenu();

				JMenuItem menuItem1 = new JMenuItem("/ball", doStretch(
						new ImageIcon("Expression/ball.gif"), 3,
						Image.SCALE_DEFAULT));
				menuItem1.addMouseListener(new ExSelect(new ImageIcon(
						"Expression/ball.gif")));
				
				JMenuItem menuItem2 = new JMenuItem("/beat", doStretch(
						new ImageIcon("Expression/beat.gif"), 3,
						Image.SCALE_DEFAULT));
				menuItem2.addMouseListener(new ExSelect(new ImageIcon(
						"Expression/beat.gif")));
				
				JMenuItem menuItem3 = new JMenuItem("/ballbeat", doStretch(
						new ImageIcon("Expression/ballbeat.gif"), 3,
						Image.SCALE_DEFAULT));
				menuItem3.addMouseListener(new ExSelect(new ImageIcon(
						"Expression/ballbeat.gif")));
				
				JMenuItem menuItem4 = new JMenuItem("/handbeat", doStretch(
						new ImageIcon("Expression/handbeat.gif"), 3,
						Image.SCALE_DEFAULT));
				menuItem4.addMouseListener(new ExSelect(new ImageIcon(
						"Expression/handbeat.gif")));
				
				JMenuItem menuItem5 = new JMenuItem("/2beathand", doStretch(
						new ImageIcon("Expression/2beathand.gif"), 3,
						Image.SCALE_DEFAULT));
				menuItem5.addMouseListener(new ExSelect(new ImageIcon(
						"Expression/2beathand.gif")));
				
				JMenuItem menuItem6 = new JMenuItem("/birdpenis", doStretch(
						new ImageIcon("Expression/birdpenis.gif"), 3,
						Image.SCALE_DEFAULT));
				menuItem6.addMouseListener(new ExSelect(new ImageIcon(
						"Expression/birdpenis.gif")));
				
				JMenuItem menuItem7 = new JMenuItem("/cool", doStretch(
						new ImageIcon("Expression/cool.gif"), 3,
						Image.SCALE_DEFAULT));
				menuItem7.addMouseListener(new ExSelect(new ImageIcon(
						"Expression/cool.gif")));
				
				JMenuItem menuItem8 = new JMenuItem("/cry", doStretch(
						new ImageIcon("Expression/cry.gif"), 3,
						Image.SCALE_DEFAULT));
				menuItem8.addMouseListener(new ExSelect(new ImageIcon(
						"Expression/cry.gif")));
				
				JMenuItem menuItem9 = new JMenuItem("/dance", doStretch(
						new ImageIcon("Expression/dance.gif"), 3,
						Image.SCALE_DEFAULT));
				menuItem9.addMouseListener(new ExSelect(new ImageIcon(
						"Expression/dance.gif")));
				
				JMenuItem menuItem10 = new JMenuItem("/doraemon", doStretch(
						new ImageIcon("Expression/doraemon.gif"), 3,
						Image.SCALE_DEFAULT));
				menuItem10.addMouseListener(new ExSelect(new ImageIcon(
						"Expression/doraemon.gif")));
				
				JMenuItem menuItem11 = new JMenuItem("/drivebike", doStretch(
						new ImageIcon("Expression/drivebike.gif"), 3,
						Image.SCALE_DEFAULT));
				menuItem11.addMouseListener(new ExSelect(new ImageIcon(
						"Expression/drivebike.gif")));
				
				JMenuItem menuItem12 = new JMenuItem("/filepinpon", doStretch(
						new ImageIcon("Expression/filepinpon.gif"), 3,
						Image.SCALE_DEFAULT));
				menuItem12.addMouseListener(new ExSelect(new ImageIcon(
						"Expression/filepinpon.gif")));
				
				JMenuItem menuItem13 = new JMenuItem("/fire", doStretch(
						new ImageIcon("Expression/fire.gif"), 3,
						Image.SCALE_DEFAULT));
				menuItem13.addMouseListener(new ExSelect(new ImageIcon(
						"Expression/fire.gif")));
				
				JMenuItem menuItem14 = new JMenuItem("/gymnastics", doStretch(
						new ImageIcon("Expression/gymnastics.gif"), 3,
						Image.SCALE_DEFAULT));
				menuItem14.addMouseListener(new ExSelect(new ImageIcon(
						"Expression/gymnastics.gif")));
				
				JMenuItem menuItem15 = new JMenuItem("/hula", doStretch(
						new ImageIcon("Expression/hula.gif"), 3,
						Image.SCALE_DEFAULT));
				menuItem15.addMouseListener(new ExSelect(new ImageIcon(
						"Expression/hula.gif")));
				
				ExPopMenu.add(menuItem1);
				ExPopMenu.add(menuItem2);
				ExPopMenu.add(menuItem3);
				ExPopMenu.add(menuItem4);
				ExPopMenu.add(menuItem5);
				ExPopMenu.add(menuItem6);
				ExPopMenu.add(menuItem7);
				ExPopMenu.add(menuItem8);
				ExPopMenu.add(menuItem9);
				ExPopMenu.add(menuItem10);
				ExPopMenu.add(menuItem11);
				ExPopMenu.add(menuItem12);
				ExPopMenu.add(menuItem13);
				ExPopMenu.add(menuItem14);
				ExPopMenu.add(menuItem15);
				// JScrollPane jScrollPane = new JScrollPane(ExPopMenu);;
				ExPopMenu.show(inputTA, x, y);
			}

			@Override
			public void keyTyped(KeyEvent e) {
				if (e.getKeyChar() == '/') {
					showExpression(e.getKeyLocation(), 0);
					kao = "";
					sep = true;
				}
				if (sep) {
					if (e.getKeyChar() == '\b') {
						if (kao.length() > 0) {
							kao = kao.substring(0, kao.length() - 1);
						} else {
							kao = "";
						}
					} else {
						kao += e.getKeyChar();
					}

					switch (kao) {
					case "/ball":
						showKao("/ball");
						e.setKeyChar('\0');
						kao = "";
						sep = false;
						break;
					case "/beat":
						showKao("/beat");
						e.setKeyChar('\0');
						kao = "";
						sep = false;
						break;
					case "/ballbeat":
						showKao("/ballbeat");
						e.setKeyChar('\0');
						kao = "";
						sep = false;
						break;
					case "/handbeat":
						showKao("/handbeat");
						e.setKeyChar('\0');
						kao = "";
						sep = false;
						break;
					case "/2beathand":
						showKao("/2beathand");
						e.setKeyChar('\0');
						kao = "";
						sep = false;
						break;
					case "/birdpenis":
						showKao("/birdpenis");
						e.setKeyChar('\0');
						kao = "";
						sep = false;
						break;
					case "/cool":
						showKao("/cool");
						e.setKeyChar('\0');
						kao = "";
						sep = false;
						break;
					case "/cry":
						showKao("/cry");
						e.setKeyChar('\0');
						kao = "";
						sep = false;
						break;
					case "/dance":
						showKao("/dance");
						e.setKeyChar('\0');
						kao = "";
						sep = false;
						break;
					case "/doraemon":
						showKao("/doraemon");
						e.setKeyChar('\0');
						kao = "";
						sep = false;
						break;
					case "/drivebike":
						showKao("/drivebike");
						e.setKeyChar('\0');
						kao = "";
						sep = false;
						break;
					case "/filepinpon":
						showKao("/filepinpon");
						e.setKeyChar('\0');
						kao = "";
						sep = false;
						break;
					case "/fire":
						showKao("/fire");
						e.setKeyChar('\0');
						kao = "";
						sep = false;
						break;
					case "/gymnastics":
						showKao("/gymnastics");
						e.setKeyChar('\0');
						kao = "";
						sep = false;
						break;
					case "/hula":
						showKao("/hula");
						e.setKeyChar('\0');
						kao = "";
						sep = false;
						break;
					default:
						break;
					}
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {

			}

			@Override
			public void keyPressed(KeyEvent e) {

			}
		});
		sendpan = new JScrollPane(inputTA);
		sendpan.setBorder(BorderFactory.createEmptyBorder());
		sendpan.setBackground(qqColor);
		sendpan.setLocation(0, 330);
		sendpan.setSize(380, 70);

		ImageIcon aokiIcon = new ImageIcon("picture/aoki.png");
		aokilabel = new JLabel(aokiIcon);
		aokilabel.setLocation(receivepan.getWidth()
				+ receivepan.getLocation().x, receivepan.getLocation().y);
		aokilabel.setSize(aokiIcon.getIconWidth(), aokiIcon.getIconHeight());

		centerPanel = new JPanel(null);
		centerPanel.add(receivepan);
		centerPanel.add(sendpan);
		centerPanel.add(itempan);
		centerPanel.add(aokilabel);
		centerPanel.setBackground(qqColor);

		this.getContentPane().setLayout(new BorderLayout());
		this.getContentPane().setBackground(qqColor);
		this.getContentPane().add(titlePanel, BorderLayout.NORTH);
		this.getContentPane().add(centerPanel, BorderLayout.CENTER);
		this.getContentPane().add(btnPanel, BorderLayout.SOUTH);

		this.setUndecorated(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLocation(screenWidth / 2 - 550 / 2, screenHeight / 2 - 500 / 2);
		this.setSize(580, 510);
		dragAdapter dragAdp = new dragAdapter();
		this.addMouseListener(dragAdp);
		this.addMouseMotionListener(dragAdp);
	}

	/**
	 * 
	 * @author KumaHime
	 * 
	 */
	private class dragAdapter extends MouseAdapter {

		private Point pressPoint = new Point();

		@Override
		public void mousePressed(MouseEvent e) {
			pressPoint = e.getPoint();
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			pressPoint = new Point();
		}

		@Override
		public void mouseDragged(MouseEvent e) {
			setLocation(mainFrame.getLocation().x + (e.getX() - pressPoint.x),
					mainFrame.getLocation().y + (e.getY() - pressPoint.y));
		}

	}

	/**
	 * 初始化基本参数
	 */
	private void initBasicData() {
		// 窗体标题
		this.setTitle("I am " + comondata.getName());

		// 给聊天记录赋初值
		this.chatRecordsStr = "";

		// 初始化父类窗体
		this.parentFrame = this;

		// 播放有新消息声音
		// Sounds.getSounds().startMessageAudio(); // 不在这播放，改成在接收消息时播放

	}

	/**
	 * 退出当前聊天
	 */
	private void eixtCurrentChat() {
		// 若是不是群聊则从聊天用户表中移除对应项
		// if(!isSingleChat)
		// {
		// comondata.getUserInformationMap().remove(usernameString);
		// }

		this.dispose();
	}

	/**
	 * 监听器
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		String actionStr = e.getActionCommand();
		switch (actionStr) {
		case "发送": {
			String inputText = getInputTAValue();

			if (!inputText.equals("")) {
				String fontStyle = "font-family:"
						+ comondata.getHtmlFontName()
						+ ";"
						+ "font-size:"
						+ comondata.getHtmlFontSize()
						+ ";"
						+ "font-style:"
						+ fontStyleToString(comondata.getHtmlFontStyle())
						+ ";"
						+ "color:"
						+ "#"
						+ towZeroHead(Integer.toHexString(comondata
								.getHtmlFontColor().getRed()))
						+ towZeroHead(Integer.toHexString(comondata
								.getHtmlFontColor().getGreen()))
						+ towZeroHead(Integer.toHexString(comondata
								.getHtmlFontColor().getBlue())) + ";";

				String chatContent = "<span style='" + fontStyle + "'>"
						+ inputText + "</span>";

				String headContent = "<table word-wrap='break-word'  width"

						+ "='1px' z-index='40' height='200' border='0' cellpadding='1' cellspacing='0' bgcolor='#E5DFEF'><tr><td valign='top' style='word-break : break-all; '>";

				String trailContent = "</td></tr></table>";

				chatContent = headContent + chatContent + trailContent;

				String formatContent = ""; // 格式化的聊天内容

				// 发送至服务器的消息
				String sendMsg = "";

				// 设置日期格式
				SimpleDateFormat df = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");

				// 获取当前系统时间
				String timeStr = df.format(new Date());

				// 格式化聊天内容
				formatContent = "<font color = green>" + comondata.getName()
						+ "  " + timeStr + " :</font><br>" + chatContent;

				// 一对一聊天
				if (isSingleChat) {
					// 消息格式：msgType:::fromUser:::toUser:::formatContent
					sendMsg = MessageTypeEnum.SingleChat.toString() + ":::"
							+ comondata.getId() + ":::" + userId + ":::"
							+ formatContent;
					System.out.println("send message one by one:" + sendMsg);
				}
				// 群聊
				else {
					// 消息格式：msgType:::fromUser:::formatContent
					sendMsg = MessageTypeEnum.GroupChat.toString() + ":::"
							+ comondata.getId() + ":::" + formatContent;
					System.out.println("gourp chat:" + sendMsg);
				}

				// 添加聊天内容到本地聊天窗口
				addChatContent("<font color = blue>" + comondata.getName()
						+ " " + timeStr + " :</font><br>" + chatContent);

				// 发送消息至服务器
				receivequestion.getMainSocket().getServerThread()
						.sendData(sendMsg);
				// 清空聊天窗口
				inputTA.setText("");

			} else {
				JOptionPane.showMessageDialog(this,
						"Can not send an empty message", "infor",
						JOptionPane.INFORMATION_MESSAGE);
				return;
			}

			break;
		}

		case "关闭": {
			eixtCurrentChat();
			break;
		}

		case "文件发送": {
			try {
				UIManager.setLookAndFeel(UIManager
						.getSystemLookAndFeelClassName());
			} catch (ClassNotFoundException | InstantiationException
					| IllegalAccessException | UnsupportedLookAndFeelException e1) {
				e1.printStackTrace();
			}
			JFileChooser fc = new JFileChooser();
			int select = fc.showOpenDialog(this);
			if (select == JFileChooser.APPROVE_OPTION) {
				comondata.setFileName(fc.getSelectedFile().getPath());
				String filename = fc.getSelectedFile().getName();
				receivequestion.getMainSocket().sendQuest(
						MessageTypeEnum.FileSend.toString(), userId,
						comondata.getMyIp(), filename);
				System.out.println("文件发送");
			}
			break;
		}

		case "字体设置": {
			Font font = new Font(comondata.getHtmlFontName(),
					comondata.getHtmlFontStyle(), comondata.getHtmlFontSize());
			FontView fv = new FontView(font, comondata.getHtmlFontColor());
			fv.showDialog(this, getLocation().x + getWidth() / 2,
					getLocation().y + getHeight() / 2);
			font = fv.getSelectedfont();
			comondata.setHtmlFontName(font.getName());
			comondata.setHtmlFontSize(font.getSize());
			comondata.setHtmlFontStyle(font.getStyle());
			Color fvColor = fv.getSelectedcolor();
			comondata.setHtmlFontColor(fvColor);

			inputTA.setFont(font);
			inputTA.setForeground(comondata.getHtmlFontColor());
			fontStyleToString(font.getStyle());
			break;
		}

		default:
			break;
		}
	}

	private String fontStyleToString(int fontstyle) {
		String value = "";
		System.out.println("font-style = " + fontstyle);
		System.out.println("BOLD = " + Font.BOLD);
		switch (fontstyle) {
		case Font.BOLD:
			System.out.println("bold");
			value = "bold";
			break;
		case Font.ITALIC:
			value = "italic";
			break;
		case Font.PLAIN:
			value = "plain";
			break;
		default:
			break;
		}
		return value;
	}

	/**
	 * 两位数补全，如果为一个数字就在首位加0
	 * 
	 * @param str
	 */
	private String towZeroHead(String str) {
		switch (str.length()) {
		case 0:
			str = "00";
			break;

		case 1:
			str = "0" + str;
			break;

		default:
			break;
		}
		return str;
	}

	/**
	 * 添加聊天内容
	 * 
	 * @param chatContent_
	 */
	public void addChatContent(String chatContent_) {
		chatpnl.addContent(chatContent_);

		// 添加到聊天记录字符串里
		chatRecordsStr += (chatContent_ + "<br>");
	}

	/**
	 * 重写的聊天panel
	 * 
	 * @author KumaHime
	 * 
	 */
	class ChatPanel extends JPanel {
		private static final long serialVersionUID = 8253395484207504951L;
		// 先创一个label
		JTextPane jp = new JTextPane();
		Vector<String> contentList = new Vector<String>();

		public ChatPanel() {
			initInterface();
		}

		private void initInterface() {
			// 背景
			this.setBackground(qqColor);

			jp.setBackground(qqColor);

			jp.setEditable(false);

			jp.setContentType("text/html");

			jp.setSize(20, 20);

			this.add(jp);

			setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		}

		/**
		 * 供外部使用的添加内容接口
		 * 
		 * @param content_
		 */
		public void addContent(String content_) {
			contentList.add(content_);

			Iterator iterator = contentList.iterator();
			String messages = "";

			while (iterator.hasNext()) {
				messages += iterator.next() + "<br>";
			}

			jp.setText("<html>" + messages + "<html>");

			// 左对齐
			jp.setAlignmentX(Component.LEFT_ALIGNMENT);

			// 刷新界面
			this.updateUI();
		}
	}

	private String getInputTAValue() {
		int k = 0;
		String msg = "";
		Vector<ImageIcon> list = new Vector<ImageIcon>();
		// 获得JTextPane中的图片
		for (int i = 0; i < this.inputTA.getStyledDocument().getRootElements()[0]
				.getElement(0).getElementCount(); i++) {

			ImageIcon icon = (ImageIcon) StyleConstants.getIcon(this.inputTA
					.getStyledDocument().getRootElements()[0].getElement(0)
					.getElement(i).getAttributes());
			if (icon != null) {

				list.add(icon);
			}
		}
		for (int i = 0; i < this.inputTA.getText().length(); i++) {

			if (this.inputTA.getStyledDocument().getCharacterElement(i)
					.getName().equals("icon")) {
				try {
					msg += "<img src='"
							+ new File(list.get(k).toString()).toURI().toURL()
									.toString() + "' />";
				} catch (MalformedURLException e) {
					e.printStackTrace();
				}

				k++;
			} else {

				try {
					msg += this.inputTA.getStyledDocument().getText(i, 1);
				} catch (BadLocationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return msg;
	}

	private void showKao(String kao) {
		String pathBegin = "Expression";
		String pathEnd = ".gif";
		try {
			inputTA.getDocument().remove(
					inputTA.getText().length() - kao.length() + 1,
					kao.length() - 1);
		} catch (BadLocationException e1) {
			e1.printStackTrace();
		}
		ImageIcon testIcon;
		testIcon = new ImageIcon(pathBegin + kao + pathEnd);

		inputTA.insertIcon(testIcon);
		System.out.println(getInputTAValue());
	}

	private static ImageIcon doStretch(ImageIcon srcImageIcon, float scale,
			int p_ScaleMode) {
		Float imgWidth = srcImageIcon.getIconWidth() / scale; // 计算缩放后的图形宽度。
		Float imgHeight = srcImageIcon.getIconHeight() / scale; // 计算缩放后的图形高度。
		// 进行缩放操作。
		return new ImageIcon(srcImageIcon.getImage().getScaledInstance(
				imgWidth.intValue(), imgHeight.intValue(), p_ScaleMode));

	}

	class ExSelect extends MouseAdapter {
		ImageIcon icon;

		public ExSelect(ImageIcon icon) {
			this.icon = icon;
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			System.out.println("press");
			if (e.getButton() == MouseEvent.BUTTON1) {
				try {
					inputTA.getDocument().remove(inputTA.getText().length() - 1, 1);
				} catch (BadLocationException e1) {
					e1.printStackTrace();
				}
				inputTA.insertIcon(icon);
			}
		}
	}

	// 测试用
	public static void main(String[] args) {
		new ChatView().setVisible(true);
	}
}
