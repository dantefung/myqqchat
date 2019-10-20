package com.myf.qq.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Vector;

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

import QQ.WEB_INF.classes.com.myf.qq.domain.User;

import com.myf.qq.control.ChatClientProxy;
import com.myf.qq.service.UserService;
import com.myf.qq.service.WavePlayerService;
import com.myf.qq.service.impl.DragHandleServiceImpl;
import com.myf.qq.service.impl.UserServiceImpl;
import com.myf.qq.service.impl.WavePlayerServiceImpl;
import com.myf.qq.util.CommonData;
import com.myf.qq.util.MessageTypeEnum;

/**
 * QQ客户端页面
 * 
 * @author myf
 * 
 */
public class MainUI extends JFrame
{
	private static final long serialVersionUID = -7791117576890422358L;
	
	private ChatList friendChatList;
	private ChatList groupChatList;
	private JLabel namelab;
	private JTabbedPane tabpan;
	private JPanel namepnl;
	private JButton closebtn;
	private JButton minibtn;
	private JTextArea addfriendtext;
	private JLabel qqmusicJL;
	private JLabel titleLab;
	private JLabel addfriendJL;
	private JLabel footJL;
	
	private MainUI mainUI = this;
	
	private ImageIcon footIcon;
	private ImageIcon closeIcon;
	private ImageIcon closeIconHover;
	private ImageIcon closeIconPress;
	private ImageIcon miniIcon;
	private ImageIcon qqmusic;
	private ImageIcon qqmusicHover;
	private ImageIcon qqmusicPress;
	private ImageIcon qqmusicminiIcon;
	
	private WavePlayerService wavePlayer;
	private JFrame musicFrame;
	private JLabel qqmusicExit;
	private JLabel qqmusicMini;
	
	private UserService userService = new UserServiceImpl();
	
	public MainUI()
	{
		mainUI = this;
		try
		{
			UIManager.setLookAndFeel(UIManager
			        .getCrossPlatformLookAndFeelClassName());
		}
		catch (ClassNotFoundException | InstantiationException
		        | IllegalAccessException | UnsupportedLookAndFeelException e)
		{
			e.printStackTrace();
		}
		this.setSize(294, 720);
		
		ImageIcon ninjaIcon = new ImageIcon("pic/mainUI/bg.gif");
		titleLab = new JLabel(ninjaIcon);
		titleLab.setLocation(0, 0);
		titleLab.setSize(ninjaIcon.getIconWidth(), ninjaIcon.getIconHeight());
		
//		namelab = new JLabel(CommonData.getName());
		namelab = new JLabel("张三");
		namelab.setFont(CommonData.getTitleFont());
		
		tabpan = new JTabbedPane();
		namepnl = new JPanel(null);
		
		friendChatList = new ChatList();
		groupChatList = new ChatList();
		
		groupChatList.addItem("群聊");
		groupChatList.addMouseListener(new listMouseListener());
		
		qqmusic = new ImageIcon("pic/mainUI/qqmusic.png");
		qqmusicHover = new ImageIcon("pic/mainUI/qqmusic_hover.png");
		qqmusicPress = new ImageIcon("pic/mainUI/qqmusic_press.png");
		qqmusicJL = new JLabel(qqmusic);
		qqmusicJL.setLocation(61, 9);
		qqmusicJL.setSize(qqmusic.getIconWidth(), qqmusic.getIconHeight());
		
		qqmusicJL.addMouseListener(new MouseListener()
		{
			
			@Override
			public void mouseReleased(MouseEvent e)
			{
				
			}
			
			@Override
			public void mousePressed(MouseEvent e)
			{
				qqmusicJL.setIcon(qqmusicPress);
			}
			
			@Override
			public void mouseExited(MouseEvent e)
			{
				qqmusicJL.setIcon(qqmusic);
				setCursor(Cursor.getDefaultCursor());
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e)
			{
				qqmusicJL.setIcon(qqmusicHover);
				setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e)
			{
				if (e.getButton() == MouseEvent.BUTTON1)
				{
					wavePlayer = new WavePlayerServiceImpl(
					        "music/ai.wav");
					wavePlayer.play(new ByteArrayInputStream(wavePlayer
					        .getSamples()));
					musicFrame = new JFrame();
					musicFrame.setSize(856, 491);
					
					JLabel qqmusicPlayArea = new JLabel(new ImageIcon("pic/mainUI/qqmusic.gif"));
					qqmusicPlayArea.setSize(getWidth(), getHeight());
					qqmusicPlayArea.setLocation(0,0);
					
					ImageIcon qqmusicExitIcon = new ImageIcon("pic/mainUI/qqclose.png");
					qqmusicExit = new JLabel(qqmusicExitIcon);
					qqmusicExit.setSize(qqmusicExitIcon.getIconWidth(), qqmusicExitIcon.getIconHeight());
					qqmusicExit.setLocation(musicFrame.getWidth()-qqmusicExitIcon.getIconWidth(), 0);
					qqmusicExit.addMouseListener(new MouseListener()
					{
						
						@Override
						public void mouseReleased(MouseEvent e)
						{
							// TODO Auto-generated method stub
							
						}
						
						@Override
						public void mousePressed(MouseEvent e)
						{
							qqmusicExit.setIcon(closeIconPress);
							
						}
						
						@Override
						public void mouseExited(MouseEvent e)
						{
							qqmusicExit.setIcon(closeIcon);
							
						}
						
						@Override
						public void mouseEntered(MouseEvent e)
						{
							qqmusicExit.setIcon(closeIconHover);
							
						}
						
						@Override
						public void mouseClicked(MouseEvent e)
						{
							musicFrame.setVisible(false);
							wavePlayer.stop();						}
					});
					
				    qqmusicminiIcon = new ImageIcon("pic/mainUI/qqmusicmini.png");
					qqmusicMini = new JLabel(qqmusicminiIcon);
					qqmusicMini.setSize(qqmusicminiIcon.getIconWidth(), qqmusicminiIcon.getIconHeight());
					qqmusicMini.setLocation(musicFrame.getWidth()-qqmusicExitIcon.getIconWidth()-qqmusicminiIcon.getIconWidth(), 0);
					qqmusicMini.addMouseListener(new MouseListener()
					{
						
						@Override
						public void mouseReleased(MouseEvent e)
						{
							// TODO Auto-generated method stub
							
						}
						
						@Override
						public void mousePressed(MouseEvent e)
						{
							// TODO Auto-generated method stub
							
						}
						
						@Override
						public void mouseExited(MouseEvent e)
						{
							qqmusicMini.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
							qqmusicMini.setIcon(qqmusicminiIcon);
							
						}
						
						@Override
						public void mouseEntered(MouseEvent e)
						{
							qqmusicMini.setCursor(new Cursor(Cursor.HAND_CURSOR));
							
						}
						
						@Override
						public void mouseClicked(MouseEvent e)
						{
							musicFrame.setExtendedState(JFrame.ICONIFIED);
							
						}
					});
					
					musicFrame.add(qqmusicPlayArea);
					qqmusicPlayArea.add(qqmusicExit);
					qqmusicPlayArea.add(qqmusicMini);
					// 默认点关闭按钮时退出程序
					musicFrame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
					// 屏幕居中
					Dimension screenDim = Toolkit.getDefaultToolkit().getScreenSize();// 获得屏幕的大小（维度）
					musicFrame.setLocation((screenDim.width - 856) / 2,
					        (screenDim.height - 491) / 2);
					// 去掉默认的边框修饰，可注释掉，运行后对比注释前后的效果
					musicFrame.setUndecorated(true);
					// 鼠标拖动
					DragHandleServiceImpl dragAdp = new DragHandleServiceImpl(musicFrame);
					musicFrame.addMouseListener(dragAdp);
					musicFrame.addMouseMotionListener(dragAdp);
					musicFrame.setVisible(true);
					
					/*JOptionPane.showMessageDialog(null,
					       "现在为您播放的是： 陈奕迅-陪你度过漫长岁月");*/
				}
			}
		});
				
				
				
		
		namelab.setLocation(0, 80);
		namelab.setSize(getWidth() / 2, 15);
		
		closeIcon = new ImageIcon("pic/mainUI/qqclose.png");
		closeIconHover = new ImageIcon("pic/mainUI/qqclose_hover.png");
		closeIconPress = new ImageIcon("pic/mainUI/qqclose_press.png");
		closebtn = new JButton(closeIcon);
		closebtn.setLocation(getWidth() - closeIcon.getIconWidth(), 1);
		closebtn.setSize(closeIcon.getIconWidth(), closeIcon.getIconHeight());
		closebtn.setContentAreaFilled(false);
		closebtn.setBackground(Color.red);
		closebtn.setBorderPainted(false);
		closebtn.setFocusPainted(false);
		
	/*	closebtn.addMouseListener(new BtnMouseMotionHandleBiz(this, closebtn,
		        "close"));*/
		closebtn.addMouseListener(new MouseListener()
		{
			
			@Override
			public void mouseReleased(MouseEvent e)
			{
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e)
			{
				closebtn.setIcon(closeIconPress);
			}
			
			@Override
			public void mouseExited(MouseEvent e)
			{
				closebtn.setIcon(closeIcon);
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e)
			{
				closebtn.setIcon(closeIconHover);
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e)
			{
				System.exit(0);
			}
		});
		
		miniIcon = new ImageIcon("pic/mainUI/qqmini.png");
		minibtn = new JButton();
		minibtn.setLocation(closebtn.getLocation().x - miniIcon.getIconWidth(),
		        closebtn.getLocation().y);
		minibtn.setSize(miniIcon.getIconWidth(), miniIcon.getIconHeight());
		minibtn.setBorderPainted(false);
		minibtn.setFocusPainted(false);
		minibtn.setBackground(CommonData.getFocusColor());
		minibtn.setContentAreaFilled(false);
		
		/*minibtn.addMouseListener(new BtnMouseMotionHandleBiz(this, minibtn,
		        "minimization"));*/
		minibtn.addMouseListener(new MouseListener()
		{
			
			@Override
			public void mouseReleased(MouseEvent e)
			{
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e)
			{
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent e)
			{
				minibtn.setIcon(null);
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e)
			{
				minibtn.setIcon(miniIcon);
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e)
			{
				mainUI.setExtendedState(JFrame.ICONIFIED);
				
			}
		});
		
		ImageIcon addfriendIcon = new ImageIcon("pic/mainUI/search.gif");
		addfriendJL = new JLabel(addfriendIcon);
		addfriendJL.setLocation(0,137);
		addfriendJL.setSize(addfriendIcon.getIconWidth(), addfriendIcon.getIconHeight());
		addfriendJL.addMouseListener(new MouseListener()
		{
			
			@Override
			public void mouseReleased(MouseEvent e)
			{
				
				
			}
			
			@Override
			public void mousePressed(MouseEvent e)
			{
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent e)
			{
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e)
			{
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e)
			{
				addfriendJL.setVisible(false);
				addfriendtext.setFocusable(true);
			}
		});
		
		addfriendtext = new JTextArea("好友添加");
		addfriendtext.setFont(CommonData.getInputFont());
		addfriendtext.setSize(getWidth(), addfriendIcon.getIconHeight());
		addfriendtext.setLocation(0, 137);
		addfriendtext.setForeground(Color.white);
		addfriendtext.addKeyListener(new KeyListener()
		{
			
			@Override
			public void keyTyped(KeyEvent e)
			{
				if (!(e.getKeyChar() > KeyEvent.VK_0 && e.getKeyChar() <= KeyEvent.VK_9))
				{
					e.consume();
				}
			}
			
			@Override
			public void keyReleased(KeyEvent e)
			{
				
			}
			
			@Override
			public void keyPressed(KeyEvent e)
			{
				
			}
		});
		addfriendtext.addFocusListener(new FocusListener()
		{
			
			@Override
			public void focusLost(FocusEvent e)
			{
				/*addfriendtext.setBackground(CommonData.getMainFocusColor());
				addfriendtext.setText("好友添加");
				addfriendtext.setForeground(Color.white);*/
				addfriendJL.setVisible(true);
			}
			
			@Override
			public void focusGained(FocusEvent e)
			{
				addfriendtext.setBackground(Color.white);
				addfriendtext.setForeground(Color.black);
				addfriendtext.setText("");
			}
		});
		addfriendtext.addKeyListener(new KeyListener()
		{
			
			@Override
			public void keyTyped(KeyEvent e)
			{
			}
			
			@Override
			public void keyReleased(KeyEvent e)
			{
				
			}
			
			@Override
			public void keyPressed(KeyEvent e)
			{
				if (e.getKeyCode() == KeyEvent.VK_ENTER)
				{
					System.out.println("typed");
					if (!addfriendtext.getText().equals(""))
					{
						userService.addFriend(
						        Integer.valueOf(CommonData.getId().trim()),
						        Integer.valueOf(addfriendtext.getText().trim()));
					}
					addfriendtext.setText("");
				}
			}
		});
		footIcon = new ImageIcon("pic/mainUI/foot.png");
		footJL = new JLabel(footIcon);
		footJL.setSize(footIcon.getIconWidth(), footIcon.getIconHeight());
		footJL.setLocation(0,getHeight()-61);
		footJL.addMouseListener(new MouseListener()
		{
			
			@Override
			public void mouseReleased(MouseEvent e)
			{
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e)
			{
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent e)
			{
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e)
			{
				footJL.setIcon(footIcon);
			}
			
			@Override
			public void mouseClicked(MouseEvent e)
			{
				// TODO Auto-generated method stub
				
			}
		});
		
		titleLab.add(closebtn);
		titleLab.add(minibtn);
		namepnl.add(titleLab);
		namepnl.add(addfriendJL);
		namepnl.add(addfriendtext);
		namepnl.setBackground(CommonData.getMainColor());
		
		JScrollPane friendpan = new JScrollPane();
		JScrollPane gruppan = new JScrollPane();
		
		friendChatList.addMouseListener(new listMouseListener(friendChatList));
		friendpan.setViewportView(friendChatList);
		
		gruppan.setViewportView(groupChatList);
		
		tabpan.setFocusable(false);
		tabpan.addTab("朋友", friendpan);
		tabpan.addTab("群", gruppan);
		tabpan.setFont(CommonData.getTabFont());
		tabpan.setBackground(Color.white);
		
		footJL.add(qqmusicJL);
		this.getContentPane().add(footJL);
		this.getContentPane().add(namepnl, BorderLayout.CENTER);
		this.getContentPane().add(tabpan, BorderLayout.SOUTH);
		
		this.getContentPane().setBackground(CommonData.getMainColor());
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setTitle("聊天工具");
		this.setUndecorated(true);
		DragHandleServiceImpl dragAdp = new DragHandleServiceImpl(this);
		this.addMouseListener(dragAdp);
		this.addMouseMotionListener(dragAdp);
	}
	
	/**
	 * 与 CommonData 中保存的在线用户表进行同步 提供给外部类调用
	 */
	public void initUserList(HashMap<String, User> OnlineUserMap)
	{
		Iterator<Entry<String, User>> iter = OnlineUserMap.entrySet()
		        .iterator();
		
		while (iter.hasNext())
		{
			Entry<String, User> entry = (Entry<String, User>) iter.next();
			User onlineUserDetail = (User) entry.getValue();
			
			// 自己不添加
			if (!onlineUserDetail.getId().equals(CommonData.getId()))
			{
				addOnlineUserItem(onlineUserDetail.getId() + "\n"
				        + onlineUserDetail.getName());
			}
		}
	}
	
	/**
	 * 与 CommonData 中保存的离线用户表进行同步 提供给外部类调用
	 */
	public void initOffUserList(HashMap<String, User> OnlineUserMap)
	{
		Iterator<Entry<String, User>> iter = OnlineUserMap.entrySet()
		        .iterator();
		
		while (iter.hasNext())
		{
			Entry<String, User> entry = (Entry<String, User>) iter.next();
			User onlineUserDetail = (User) entry.getValue();
			
			// 自己不添加
			if (!onlineUserDetail.getId().equals(CommonData.getId()))
			{
				addOfflineUserItem(onlineUserDetail.getId() + "\n"
				        + onlineUserDetail.getName());
			}
		}
	}
	
	/**
	 * 在list内添加在线用户
	 * 
	 * @param nameStr_
	 */
	public void addOnlineUserItem(String nameStr_)
	{
		friendChatList.addItem(nameStr_);
		friendChatList.setUserOlineStatus(nameStr_);
	}
	
	/**
	 * 在list内添加离线用户
	 * 
	 * @param nameStr_
	 */
	public void addOfflineUserItem(String nameStr_)
	{
		friendChatList.addItem(nameStr_);
		friendChatList.setUserOfflineStatus(nameStr_);
	}
	
	/**
	 * 在list内移除用户
	 * 
	 * @param nameStr_
	 */
	public void removeUserItem(String nameStr_)
	{
		friendChatList.removeItem(nameStr_);
	}
	
	/**
	 * 在线用户列表(重写jlist控件)
	 * 
	 * @author Myf
	 */
	
	class ChatList extends JList<Object>
	{
		private static final long serialVersionUID = 4281799662638099298L;
		
		private UserListCellRenderer userlistuserlist = new UserListCellRenderer();
		
		private Vector<String> onlineUserVector = new Vector<>(); // list
		                                                          // 内容
		
		public ChatList()
		{
			
			// list高度
			this.setFixedCellHeight(65);
			
			this.setBackground(Color.white);
			
			this.setFont(CommonData.getTitleWithSegoeFont());
			
			// 设置渲染器为自定义的渲染器
			this.setCellRenderer(userlistuserlist);
			
			// 设置list内容
			this.setListData(onlineUserVector);
			
		}
		
		public void setUserOlineStatus(String status)
		{
			userlistuserlist.setOnline(status);
		}
		
		public void setUserOfflineStatus(String status)
		{
			userlistuserlist.setOffline(status);
		}
		
		public void setUserB3Selected(String selected)
		{
			userlistuserlist.setB3Select(selected);
		}
		
		public boolean isUserOnline(String status)
		{
			return userlistuserlist.isOnline(status);
		}
		
		/**
		 * 给list提供添加item的接口
		 * 
		 * @param nameStr_
		 */
		public void addItem(String nameStr_)
		{
			if (!onlineUserVector.contains(nameStr_))
			{
				onlineUserVector.add(nameStr_);
			}
			this.setListData(onlineUserVector);
		}
		
		/**
		 * 给list提供删除item的接口
		 * 
		 * @param nameStr_
		 */
		public void removeItem(String nameStr_)
		{
			onlineUserVector.remove(nameStr_);
			this.setListData(onlineUserVector);
		}
	}
	
	/**
	 * list 渲染器
	 * 
	 * @author myf
	 * 
	 */
	class UserListCellRenderer extends JPanel implements
	        ListCellRenderer<Object>
	{
		private static final long serialVersionUID = 3278668944817504772L;
		
		private String text; // list上的text
		private Color background; // bei jing se
		private Color foreground;
		private boolean online = false;
		private String B3SStr = "";
		private Vector<String> onlineList = new Vector<String>(); // 记录在线用户
		
		public UserListCellRenderer()
		{
			this.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 30));
		}
		
		@Override
		public Component getListCellRendererComponent(JList<?> list,
		        Object value, int index, boolean isSelected,
		        boolean cellHasFocus)
		{
			text = (String) value;
			this.setFont(CommonData.getInputFont());
			// background=isSelected?list.getSelectionBackground():list.getBackground();
			/*System.out
                    .println("MainUI.UserListCellRenderer.getListCellRendererComponent():\t"
                    		+ "onlineList=" + onlineList 
                    		+ "\text=" + text);*/
			if (onlineList.contains(text) || text.equals("群聊"))
			{
				online = true;
			}
			else
			{
				online = false;
			}
			background = isSelected ? CommonData.getItemFocusColor()
			        : Color.white;
			foreground = isSelected ? list.getSelectionForeground() : list
			        .getForeground();
			
			return this;
		}
		
		public void setSelectForeGround(Color fore)
		{
			foreground = fore;
		}
		
		public void setB3Select(String selectedStr)
		{
			B3SStr = selectedStr;
		}
		
		public void setOnline(String status)
		{
			if (!onlineList.contains(status))
			{
				onlineList.add(status);
			}
		}
		
		public void setOffline(String status)
		{
			if (onlineList.contains(status))
			{
				onlineList.remove(status);
			}
		}
		
		public boolean isOnline(String status)
		{
			return onlineList.contains(status);
		}
		
		/**
		 * 重绘父类面板
		 */
		@Override
		public void paintComponent(Graphics g)
		{
			g.setColor(background);
			g.fillRect(0, 0, getWidth(), getHeight());
			g.setColor(foreground);
			try
			{
				if (online)
				{
					Image ryuko = ImageIO.read(new File(
					        "pic/common/headImg.png"));
					g.drawImage(ryuko, 0, getHeight() / 2 - 23, null);
				}
				else
				{
					Image ryuko = ImageIO.read(new File(
					        "pic/common/headImg_gray.png"));
					g.drawImage(ryuko, 0, getHeight() / 2 - 23, null);
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			if (text.equals("群聊"))
			{
				g.drawString(text, (getWidth() - 50) / 2,
				        (getHeight() - 10) / 2);
			}
			else
			{
				g.drawString("账号：" + text.split("\n")[0],
				        (getWidth() - 50) / 2, (getHeight() - 10) / 2);
				g.drawString("用户名： " + text.split("\n")[1],
				        (getWidth() - 50) / 2, (getHeight() - 10) / 2
				                + this.getFont().getSize());
			}
		}
		
		@Override
		public Dimension getPreferredSize()
		{
			return new Dimension(130, 120); // Cell的尺寸
		}
		
	}
	
	/**
	 * list 监听器
	 * 
	 * @author Myf
	 * 
	 */
	class listMouseListener extends MouseAdapter
	{
		private JPopupMenu jpop = new JPopupMenu();
		private ChatList clist = null;
		private boolean friendChat;
		private String id_now;
		private String name_now;
		
		public listMouseListener()
		{
			friendChat = false;
		}
		
		public listMouseListener(ChatList chatlist)
		{
			friendChat = true;
			clist = chatlist;
			JMenuItem friendItem = new JMenuItem("删除好友");
			friendItem.addMouseListener(new MouseAdapter()
			{
				
				@Override
				public void mouseReleased(MouseEvent e)
				{
					if (id_now.equals("0"))
					{
						JOptionPane.showMessageDialog(null, "系统好友不能删除！");
						return;
					}
					if (e.getButton() == MouseEvent.BUTTON1)
					{
						ChatClientProxy.getInstance().sendRequest(
						        MessageTypeEnum.RemoveFriend,
						        MessageTypeEnum.RemoveFriend.toString() + ":::"
						                + Integer.valueOf(CommonData.getId())
						                + ":" + id_now);
					}
				}
				
			});
			jpop.add(friendItem);
		}
		
		@Override
		public void mouseClicked(MouseEvent e)
		{
			
			JList<?> userList = (JList<?>) e.getSource();
			
			String value = userList.getModel()
			        .getElementAt(userList.locationToIndex(e.getPoint()))
			        .toString().trim();
			if (value != "群聊")
			{
				id_now = value.split("\n")[0];
				name_now = value.split("\n")[1];
			}
			if (e.getButton() == MouseEvent.BUTTON3 && friendChat == true)
			{
				clist.setSelectedValue(value, true);
				jpop.show(clist, e.getX(), e.getY());
			}
			if (e.getClickCount() == 2)
			{
				int index = userList.locationToIndex(e.getPoint());
				if (index >= 0)
				{
					// 群聊
					if (e.getSource() == groupChatList)
					{
						System.out.println("群聊");
						CommonData.getGroupChatFrame().setVisible(true);
					}
					
					// 一对一聊天
					else
					{
						String userName = userList.getModel()
						        .getElementAt(index).toString().trim();
						if (clist.isUserOnline(userName))
						{
							
							if (CommonData.getChatFrameMap().get(userName) == null)
							{
								// 添加到聊天用户表
								CommonData.getChatFrameMap().put(userName,
								        new ChatUI(userName));
							}
							
							CommonData.getChatFrameMap().get(userName)
							        .setVisible(true);
						}
						else
						{
							JOptionPane.showMessageDialog(null, "用户离线！无法发送！");
						}
					}
				}
			}
		}
	}
	
	// 测试用
	public static void main(String[] args)
	{
		MainUI m = new MainUI();
		m.setVisible(true);
		m.addOnlineUserItem("1\na");
		m.addOnlineUserItem("2\nb");
	}
}
