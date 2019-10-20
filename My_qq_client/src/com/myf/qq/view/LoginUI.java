package com.myf.qq.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.myf.qq.control.ChatClient;
import com.myf.qq.control.ChatClientProxy;
import com.myf.qq.service.BtnMouseMotionHandleService;
import com.myf.qq.service.impl.DragHandleServiceImpl;
import com.myf.qq.sys.domain.Config;
import com.myf.qq.sys.domain.LoginConfig;
import com.myf.qq.sys.factory.BeanFactory;
import com.myf.qq.sys.mgr.ConfigurationManager;
import com.myf.qq.sys.util.Constants;
import com.myf.qq.util.CommonData;
import com.myf.qq.util.DataUtil;
import com.myf.qq.util.MessageTypeEnum;

/**
 *        _____                    _____                    _____                    _____          
         /\    \                  /\    \                  /\    \                  /\    \         
        /::\____\                /::\____\                /::\____\                /::\____\        
       /::::|   |               /::::|   |               /::::|   |               /::::|   |        
      /:::::|   |              /:::::|   |              /:::::|   |              /:::::|   |        
     /::::::|   |             /::::::|   |             /::::::|   |             /::::::|   |        
    /:::/|::|   |            /:::/|::|   |            /:::/|::|   |            /:::/|::|   |        
   /:::/ |::|   |           /:::/ |::|   |           /:::/ |::|   |           /:::/ |::|   |        
  /:::/  |::|___|______    /:::/  |::|___|______    /:::/  |::|___|______    /:::/  |::|___|______  
 /:::/   |::::::::\    \  /:::/   |::::::::\    \  /:::/   |::::::::\    \  /:::/   |::::::::\    \ 
/:::/    |:::::::::\____\/:::/    |:::::::::\____\/:::/    |:::::::::\____\/:::/    |:::::::::\____\
\::/    / ~~~~~/:::/    /\::/    / ~~~~~/:::/    /\::/    / ~~~~~/:::/    /\::/    / ~~~~~/:::/    /
 \/____/      /:::/    /  \/____/      /:::/    /  \/____/      /:::/    /  \/____/      /:::/    / 
             /:::/    /               /:::/    /               /:::/    /               /:::/    /  
            /:::/    /               /:::/    /               /:::/    /               /:::/    /   
           /:::/    /               /:::/    /               /:::/    /               /:::/    /    
          /:::/    /               /:::/    /               /:::/    /               /:::/    /     
         /:::/    /               /:::/    /               /:::/    /               /:::/    /      
        /:::/    /               /:::/    /               /:::/    /               /:::/    /       
        \::/    /                \::/    /                \::/    /                \::/    /        
         \/____/                  \/____/                  \/____/                  \/____/    
 * 
 * 
 * 登录页面
 * 1、页面展示 extends JFrame
 * 2、事件处理 implements ActionListener
 * 
 * Decription:
 *  The class that is interested in processing an action event implements this interface, 
 *  and the object created with that class is registered with a component, 
 *  using the component's addActionListener method.
 * 
 * @author myf
 * @version 2.0
 * @since 2016-2-6
 * 
 */
public class LoginUI extends JFrame implements ActionListener
{
	/** 用户名文本域 **/
	private JTextField userNameJTF;
	
	/** 密码文本域 **/
	private JPasswordField passWordJTF;
	
	/** 头像标签 **/
	private JLabel headImgJL;
	
	/** 背景图标签 **/
	private JLabel backgroundJL;
	
	/** 注册标签 **/
	private JLabel registerJL;
	
	/** 忘记密码 **/
	private JLabel forgetPwdJL;
	
	/** 缩小按钮 **/
	private JButton shrinkJB;
	
	/** 关闭按钮 **/
	private JButton closeJB;
	
	/** 登录按钮 **/
	private JButton loginJB;
	
	/** 多帐号登录 **/
	private JLabel multiLogJL;
	
	/** 二维码 **/
	private JLabel logQRJL;
	
	/** 记住密码 **/
	private JLabel remeberPwdJL;
	
	/** 自动登录 **/
	private JLabel autoLoginJL;
	
	/** 复选框(用标签代替) **/
	private JLabel checkboxJL;
	
	private JLabel checkboxJL_;
	
	/** 登录页面的配置信息类（JavaBean） **/
	private Config config;
	
	/** 复选框是否勾选 **/
	private boolean isChecked;
	
	/** QQ客户端线程 **/
	private ChatClient chatClient;
	
	public LoginUI()
	{
		// IOC容器启动
		BeanFactory.init();
		System.out.println("IOC容器启动... ...");
		config = ConfigurationManager.getConfig(LoginConfig.class);
		System.out.println("系统配置初始化完毕... ...");
		// 初始化登录界面
		initUI();
		System.out.println("登录界面初始化完毕... ...");
	}
	
	/**
	 * 初始化页面
	 */
	private void initUI()
	{
		// 1、 框架的一些设置
		jframeSetting();
		// 2、准备组件
		prepareComponent();
		// 3、将组件镶嵌进JFrame中
		inlayComponentToJFrameContentPane();
		// 4、调整组件的样式
		ajustComponentStyle();
		// 5、给组件绑定事件
		bindEventToComponent();
		
	}
	
	//////////////////////////////自定义方法/////////////////////////////////////
	
	/**
	 * 框架的一些设置
	 */
	private void jframeSetting()
	{
		try
		{
			// 将框架的外观设置为当前系统风格
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		catch (ClassNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (InstantiationException e)
		{
			e.printStackTrace();
		}
		catch (IllegalAccessException e)
		{
			e.printStackTrace();
		}
		catch (UnsupportedLookAndFeelException e)
		{
			e.printStackTrace();
		}
		
		// 设置框架的大小
		this.setSize(432, 331);
		// 置顶
		this.setAlwaysOnTop(true);
		// 默认点关闭按钮时退出程序
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		// 可变尺寸。
		this.setResizable(false);
		// 屏幕居中
		Dimension screenDim = Toolkit.getDefaultToolkit().getScreenSize();// 获得屏幕的大小（维度）
		this.setLocation((screenDim.width - 350) / 2,
		        (screenDim.height - 250) / 2);
		// 设置面板的背景色
		this.getContentPane().setBackground(CommonData.getQqColor());
		// 去掉默认的边框修饰，可注释掉，运行后对比注释前后的效果
		this.setUndecorated(true);
		// 鼠标拖动
		DragHandleServiceImpl dragAdp = new DragHandleServiceImpl(this);
		this.addMouseListener(dragAdp);
		this.addMouseMotionListener(dragAdp);
	}
	
	/**
	 * 组件准备
	 */
	private void prepareComponent()
	{
		userNameJTF = new JTextField(config.getUserNameHint());
		passWordJTF = new JPasswordField(config.getPassWordHint());
		headImgJL = new JLabel(new ImageIcon(config.getHeadImgURL()));
		shrinkJB = new JButton(new ImageIcon(config.getShrinkURL()));
		closeJB = new JButton(new ImageIcon(config.getCloseURL()));
		backgroundJL = new JLabel(new ImageIcon(config.getLoginbgURL()));
		loginJB = new JButton(new ImageIcon(config.getLoginbtnURL()));
		registerJL = new JLabel("注册账号");
		forgetPwdJL = new JLabel("忘记密码");
		remeberPwdJL = new JLabel("记住密码");
		autoLoginJL = new JLabel("自动登录");
		checkboxJL = new JLabel(new ImageIcon(config.getCheckboxURL()));
		checkboxJL_ = new JLabel(new ImageIcon(config.getCheckboxURL()));
		multiLogJL = new JLabel(new ImageIcon(config.getMultiLogURL()));
		logQRJL = new JLabel(new ImageIcon(config.getLoginQRURL()));
	}
	
	/**
	 * 框架中的面板内嵌组件
	 */
	private void inlayComponentToJFrameContentPane()
	{
		this.getContentPane().setBackground(CommonData.getBgColor());
		this.getContentPane().setLayout(null);
		this.getContentPane().add(userNameJTF);
		this.getContentPane().add(passWordJTF);
		this.getContentPane().add(headImgJL);
		this.getContentPane().add(backgroundJL, 0);
		this.getContentPane().add(loginJB);
		this.getContentPane().add(registerJL);
		this.getContentPane().add(forgetPwdJL);
		this.getContentPane().add(multiLogJL);
		this.getContentPane().add(logQRJL);
	    this.getContentPane().add(remeberPwdJL);
	    this.getContentPane().add(checkboxJL);
	    this.getContentPane().add(checkboxJL_);
	    this.getContentPane().add(autoLoginJL);
	}
	
	/**
	 * 调整组件样式
	 */
	private void ajustComponentStyle()
	{
		// 背景图片设置
		ImageIcon bgIcon = new ImageIcon(config.getLoginbgURL());
		backgroundJL.setSize(bgIcon.getIconWidth(), bgIcon.getIconHeight());
		backgroundJL.add(shrinkJB);
		backgroundJL.add(closeJB);
		
		// 关闭按钮设置
		ImageIcon closeIcon = new ImageIcon(config.getCloseURL());
		closeJB.setLocation(this.getWidth() - closeIcon.getIconWidth() + 5, 0);
		closeJB.setSize(closeIcon.getIconWidth(), closeIcon.getIconHeight());
		closeJB.setContentAreaFilled(false);
		closeJB.setBorder(BorderFactory.createEmptyBorder());
		
		// 缩小按钮设置
		ImageIcon miniIcon = new ImageIcon(config.getShrinkURL());
		shrinkJB.setLocation(closeJB.getLocation().x - miniIcon.getIconWidth()-5,
		        closeJB.getLocation().y);
		shrinkJB.setSize(miniIcon.getIconWidth(), miniIcon.getIconHeight()+12);
		shrinkJB.setContentAreaFilled(false);
		shrinkJB.setBorder(BorderFactory.createEmptyBorder());
		
		// 用户名标签设置
		userNameJTF.setLocation(getWidth() / 2 - getWidth() / 4 + 30 / 2,
		        getHeight() / 2 - 30 / 2 + 42);
		userNameJTF.setSize(getWidth() / 2 - 30, 28);
		userNameJTF.setFont(CommonData.getLogTextFont());
		userNameJTF.setBorder(BorderFactory.createLineBorder(Color.gray));
		
		// 密码标签设置
		passWordJTF.setBorder(BorderFactory.createLineBorder(Color.gray));
		passWordJTF.setEchoChar((char) 0);
		passWordJTF.setLocation(userNameJTF.getLocation().x,
		        userNameJTF.getLocation().y + 28);
		passWordJTF.setSize(userNameJTF.getSize());
		passWordJTF.setFont(CommonData.getLogTextFont());
		
		if(Constants.FALSE.equals(ConfigurationManager.getLoginInfo().getProperty(Constants.ISREMMEBER)))
		{
			userNameJTF.setForeground(Color.gray);
			passWordJTF.setForeground(Color.gray);
		}
		else
		{
			userNameJTF.setForeground(Color.black);
			userNameJTF.setText(ConfigurationManager.getLoginInfo().getProperty(Constants.USERNAME));
			passWordJTF.setForeground(Color.black);
			passWordJTF.setText(ConfigurationManager.getLoginInfo().getProperty(Constants.PASSWORD));
			passWordJTF.setEchoChar('●');
			checkboxJL.setIcon(new ImageIcon(config.getCheckboxCheckedURL()));
		}
		
		// QQ头像设置
		ImageIcon imgIcon = new ImageIcon(config.getHeadImgURL());
		headImgJL.setSize(imgIcon.getIconWidth(), imgIcon.getIconHeight());
		headImgJL.setLocation(
		        userNameJTF.getLocation().x - imgIcon.getIconWidth() - 12,
		        userNameJTF.getLocation().y);
		
		// 登录按钮
		loginJB.setBorderPainted(false);
		loginJB.setFocusPainted(false);
		loginJB.setFont(CommonData.getBtnFont());
		loginJB.setLocation(passWordJTF.getLocation().x,
		        passWordJTF.getLocation().y + 70);
		loginJB.setSize(passWordJTF.getSize());
		
		// 注册按钮设置
		registerJL.setForeground(CommonData.getFgColor());
		registerJL.setBackground(CommonData.getPaleTurquoise1());
		registerJL.setHorizontalAlignment(JLabel.LEFT);
		registerJL.setSize(60, userNameJTF.getSize().height);
		registerJL.setLocation(
		        userNameJTF.getLocation().x + userNameJTF.getSize().width + 12,
		        userNameJTF.getLocation().y);
		
		// 忘记密码
		forgetPwdJL.setForeground(CommonData.getFgColor());
		forgetPwdJL.setBackground(CommonData.getPaleTurquoise1());
		forgetPwdJL.setHorizontalAlignment(JLabel.LEFT);
		forgetPwdJL.setSize(60, passWordJTF.getSize().height);
		forgetPwdJL.setLocation(
		        passWordJTF.getLocation().x + passWordJTF.getSize().width + 12,
		        passWordJTF.getLocation().y);
		
		// 记住密码
		remeberPwdJL.setForeground(Color.GRAY);
		remeberPwdJL.setFont(CommonData.getLogTextFont());
		remeberPwdJL.setBackground(CommonData.getPaleTurquoise1());
		remeberPwdJL.setSize(60, passWordJTF.getSize().height);
		remeberPwdJL.setLocation(
		        passWordJTF.getLocation().x + 18,
		        passWordJTF.getLocation().y + 30);
		
		// 自动登录
		autoLoginJL.setForeground(Color.GRAY);
		autoLoginJL.setFont(CommonData.getLogTextFont());
		autoLoginJL.setBackground(CommonData.getPaleTurquoise1());
		autoLoginJL.setSize(60, passWordJTF.getSize().height);
		autoLoginJL.setLocation(
				passWordJTF.getLocation().x + passWordJTF.getWidth() * 2/3 + 15,
				passWordJTF.getLocation().y + 30);
		// 复选框
		ImageIcon chkIcon = new ImageIcon(config.getCheckboxURL());
		checkboxJL.setSize(chkIcon.getIconWidth(), chkIcon.getIconHeight());
		checkboxJL.setLocation(
				passWordJTF.getLocation().x,
				passWordJTF.getLocation().y+38);

		checkboxJL_.setSize(chkIcon.getIconWidth(), chkIcon.getIconHeight());
		checkboxJL_.setLocation(
				passWordJTF.getLocation().x + passWordJTF.getWidth() * 2/3 - 3,
				passWordJTF.getLocation().y+38);
		
		// 多账号登录
		ImageIcon multilogIcon = new ImageIcon(config.getMultiLogURL());
		multiLogJL.setSize(multilogIcon.getIconWidth(), multilogIcon.getIconHeight());
		multiLogJL.setLocation(0, this.getHeight()-35);
		
		// 二维码
		ImageIcon logQRIcon = new ImageIcon(config.getLoginQRURL());
		logQRJL.setSize(logQRIcon.getIconWidth(), logQRIcon.getIconHeight());
		logQRJL.setLocation(this.getWidth()-logQRIcon.getIconWidth(), this.getHeight()-logQRIcon.getIconHeight()-3);
		
	}
	
	/**
	 * 给组件绑定事件
	 */
	private void bindEventToComponent()
	{
		// 关闭按钮
		closeJB.addMouseListener(new BtnMouseMotionHandleService(this, closeJB,
		        "close", config));
		// 缩小按钮
		shrinkJB.addMouseListener(new BtnMouseMotionHandleService(this, shrinkJB,
		        "minimization", config));
		
		// 账号输入框（文本域）
		userNameJTF.addFocusListener(new FocusListener()
		{
			
			@Override
			public void focusLost(FocusEvent arg0)
			{
				if("".equals(userNameJTF.getText()))
				{
					userNameJTF.setText(config.getUserNameHint());
					userNameJTF.setForeground(Color.gray);
				}
			}
			
			@Override
			public void focusGained(FocusEvent arg0)
			{
				if(config.getUserNameHint().equals(userNameJTF.getText()))
				{
					userNameJTF.setText("");
					userNameJTF.setForeground(Color.black);
				}
			}
		});
		userNameJTF.addKeyListener(new KeyListener()
		{
			
			@Override
			public void keyTyped(KeyEvent e)
			{
				// 如果输入的不是0-9的数字，就自动清除
				if (!(e.getKeyChar() >= KeyEvent.VK_0 && e.getKeyChar() <= KeyEvent.VK_9))
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
		userNameJTF.addMouseListener(new MouseListener()
		{
			
			@Override
			public void mouseReleased(MouseEvent e)
			{
				
				
			}
			
			@Override
			public void mousePressed(MouseEvent e)
			{
				
				
			}
			
			@Override
			public void mouseExited(MouseEvent e)
			{
				userNameJTF.setBorder(BorderFactory.createLineBorder(Color.gray));
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e)
			{
				userNameJTF.setBorder(BorderFactory.createLineBorder(CommonData.getFocusColor()));
			}
			
			@Override
			public void mouseClicked(MouseEvent e)
			{
				
				
			}
		});
		
		// 密码输入框（文本域）
		passWordJTF.addFocusListener(new FocusListener()
		{
			
			@Override
			public void focusLost(FocusEvent e)
			{
				if ("".equals(passWordJTF.getText().trim()))
                {
					passWordJTF.setText(config.getPassWordHint());
					passWordJTF.setEchoChar((char)0);
					passWordJTF.setForeground(Color.GRAY);
                }
			}
			
			@Override
			public void focusGained(FocusEvent e)
			{
				if(config.getPassWordHint().equals(passWordJTF.getText().trim()))
				{
					passWordJTF.setText("");
					passWordJTF.setForeground(Color.black);
				}
				
			
			}
		});
		passWordJTF.addKeyListener(new KeyListener()
		{
			
			@Override
			public void keyTyped(KeyEvent arg0)
			{
				
				
			}
			
			@Override
			public void keyReleased(KeyEvent arg0)
			{
				
				
			}
			
			@Override
			public void keyPressed(KeyEvent ke)
			{
				//如果是密码显示的字符全部为●
				passWordJTF.setEchoChar('●');
				char[] password = passWordJTF.getPassword();
			}
		});
		passWordJTF.addMouseListener(new MouseListener()
		{
			
			@Override
			public void mouseReleased(MouseEvent arg0)
			{
				
				
			}
			
			@Override
			public void mousePressed(MouseEvent arg0)
			{
				
				
			}
			
			@Override
			public void mouseExited(MouseEvent arg0)
			{
				passWordJTF.setBorder(BorderFactory.createLineBorder(Color.gray));
			}
			
			@Override
			public void mouseEntered(MouseEvent arg0)
			{
				passWordJTF.setBorder(BorderFactory.createLineBorder(CommonData.getFocusColor()));
			}
			
			@Override
			public void mouseClicked(MouseEvent arg0)
			{
				
				
			}
		});
		// 注册
		registerJL.addMouseListener(new MouseListener()
		{
			
			@Override
			public void mouseReleased(MouseEvent arg0)
			{
				/*if (CommonData.getRegisterUI() == null)
				{
					CommonData.setRegisterUI(new RegisterUI());
					CommonData.getRegisterUI().setVisible(true);
				}
				else
				{
					CommonData.getRegisterUI().setVisible(true);
				}*/
				//判断当前系统是否支持Java AWT Desktop扩展
				if (java.awt.Desktop.isDesktopSupported())
				{
					try
					{
						// 创建一个URI实例,注意不是URL
						java.net.URI uri = java.net.URI
						        .create("http://localhost:9090/QQ/user?action=registerUI");
						// 获取当前系统桌面扩展
						java.awt.Desktop dp = java.awt.Desktop.getDesktop();
						// 判断系统桌面是否支持要执行的功能
						if (dp.isSupported(java.awt.Desktop.Action.BROWSE))
						{
							// 获取系统默认浏览器打开链接
							dp.browse(uri);
						}
					}
					catch (java.lang.NullPointerException e)
					{
						// 此为uri为空时抛出异常
						JOptionPane.showMessageDialog(getContentPane(), "请联系QQ:476400902，检查URL是否有误！", "出错啦~",JOptionPane.WARNING_MESSAGE);
					}
					catch (java.io.IOException e)
					{
						// 此为无法获取系统默认浏览器
						JOptionPane.showMessageDialog(getContentPane(), "无法获取系统默认浏览器!", "出错啦~",JOptionPane.WARNING_MESSAGE);
					}
				}
			}
			
			@Override
			public void mousePressed(MouseEvent arg0)
			{
				
			}
			
			@Override
			public void mouseExited(MouseEvent arg0)
			{
				registerJL.setForeground(CommonData.getFgColor());
				setCursor(java.awt.Cursor.getDefaultCursor());
			}
			
			@Override
			public void mouseEntered(MouseEvent arg0)
			{
				registerJL.setForeground(CommonData.getFgHoverColor());
				setCursor(java.awt.Cursor
				        .getPredefinedCursor(java.awt.Cursor.HAND_CURSOR));
			}
			
			@Override
			public void mouseClicked(MouseEvent arg0)
			{
				
			}
		});
		
		// 忘记密码
		forgetPwdJL.addMouseListener(new MouseListener()
		{
			
			@Override
			public void mouseReleased(MouseEvent e)
			{
				
				
			}
			
			@Override
			public void mousePressed(MouseEvent e)
			{
				
				
			}
			
			@Override
			public void mouseExited(MouseEvent arg0)
			{
				forgetPwdJL.setForeground(CommonData.getFgColor());
				setCursor(java.awt.Cursor.getDefaultCursor());
			}
			
			@Override
			public void mouseEntered(MouseEvent arg0)
			{
				forgetPwdJL.setForeground(CommonData.getFgHoverColor());
				setCursor(java.awt.Cursor
				        .getPredefinedCursor(java.awt.Cursor.HAND_CURSOR));
			}
			
			@Override
			public void mouseClicked(MouseEvent e)
			{
				
				
			}
		});
		
		// 多帐号登录
		multiLogJL.addMouseListener(new MouseListener()
		{
			
			@Override
			public void mouseReleased(MouseEvent arg0)
			{
				
				
			}
			
			@Override
			public void mousePressed(MouseEvent arg0)
			{
				
				
			}
			
			@Override
			public void mouseExited(MouseEvent arg0)
			{
				multiLogJL.setIcon(new ImageIcon(config.getMultiLogURL()));
			}
			
			@Override
			public void mouseEntered(MouseEvent arg0)
			{
				multiLogJL.setIcon(new ImageIcon(config.getLoginmultiPressURL()));
				multiLogJL.setToolTipText("多帐号登录");
			}
			
			@Override
			public void mouseClicked(MouseEvent arg0)
			{
				
				
			}
		});
		
		// 二维码
		logQRJL.addMouseListener(new MouseListener()
		{
			
			@Override
			public void mouseReleased(MouseEvent arg0)
			{
				
				
			}
			
			@Override
			public void mousePressed(MouseEvent arg0)
			{
				
				
			}
			
			@Override
			public void mouseExited(MouseEvent arg0)
			{
				logQRJL.setIcon(new ImageIcon(config.getLoginQRURL()));
			}
			
			@Override
			public void mouseEntered(MouseEvent arg0)
			{
				logQRJL.setIcon(new ImageIcon(config.getLoginQRPressURL()));
				logQRJL.setToolTipText("二维码登录");
			}
			
			@Override
			public void mouseClicked(MouseEvent arg0)
			{
				
			}
		});
		
		// 复选框
		checkboxJL.addMouseListener(new MouseListener()
		{
			// 标志checkbox是否被选中,默认状态是未被勾选
			private boolean flag = false;
			
			@Override
			public void mouseReleased(MouseEvent e)
			{
				
			}
			
			@Override
			public void mousePressed(MouseEvent e)
			{
				// 初始化flag的值
				if(Constants.TRUE.equals(ConfigurationManager.getLoginInfo().getProperty(Constants.ISREMMEBER)))
				{
					flag=true;
				}
				
				flag = !flag;
				if(flag)
				{
					checkboxJL.setIcon(new ImageIcon(config.getCheckboxCheckedURL()));
					if(userNameJTF.getText() != null && !"".equals(userNameJTF.getText())
							&& passWordJTF.getPassword() != null && passWordJTF.getPassword().length > 0)
					{
						if(!config.getUserNameHint().equals(userNameJTF.getText()) && !config.getPassWordHint().equals(passWordJTF.getText()))
						{
							 ConfigurationManager.writeLoginInfo2Prop(Constants.TRUE, userNameJTF.getText(), passWordJTF.getText());
						}
					}
					
					// 记住密码复选框被勾选，标识为true
					isChecked = true;
				}
				else
				{
					checkboxJL.setIcon(new ImageIcon(config.getCheckboxURL()));
					ConfigurationManager.writeLoginInfo2Prop(Constants.FALSE, "", "");
					// 记住密码复选框没被勾选，标识为false
					isChecked = false;
				}
			}
			
			@Override
			public void mouseExited(MouseEvent e)
			{
				// 未被勾选
				if(!flag)
				{
					if(Constants.FALSE.equals(ConfigurationManager.getLoginInfo().getProperty(Constants.ISREMMEBER)))
					{
						checkboxJL.setIcon(new ImageIcon(config.getCheckboxURL()));
					}
				}
			}
			
			@Override
			public void mouseEntered(MouseEvent e)
			{
				// 未被勾选
				if(!flag)
				{
					if(Constants.FALSE.equals(ConfigurationManager.getLoginInfo().getProperty(Constants.ISREMMEBER)))
					{
						checkboxJL.setIcon(new ImageIcon(config.getCheckboxHoverURL()));
					}
				}
			}
			
			@Override
			public void mouseClicked(MouseEvent e)
			{
				
			}
		});
		checkboxJL_.addMouseListener(new MouseListener()
		{
			private boolean flag = false;
			@Override
			public void mouseReleased(MouseEvent e)
			{
				
				
			}
			
			@Override
			public void mousePressed(MouseEvent e)
			{
				flag = !flag;
				if(flag)
				{
					checkboxJL_.setIcon(new ImageIcon(config.getCheckboxCheckedURL()));
				}
				else
				{
					checkboxJL_.setIcon(new ImageIcon(config.getCheckboxURL()));
				}
				
			}
			
			@Override
			public void mouseExited(MouseEvent e)
			{
				if(!flag)
				{
					checkboxJL_.setIcon(new ImageIcon(config.getCheckboxURL()));
				}
			}
			
			@Override
			public void mouseEntered(MouseEvent e)
			{
				if(!flag)
				{
				    checkboxJL_.setIcon(new ImageIcon(config.getCheckboxHoverURL()));
				}
			}
			
			@Override
			public void mouseClicked(MouseEvent e)
			{
				
			}
		});
		
		
		
		/** Core：登录按钮   **/
		/** 设定动作的指令，用于区分不同的动作，以便采取相应的响应操作 **/
		loginJB.setActionCommand("登录");
		/** 将本类LoginUI注册为动作的监听者 **/
		loginJB.addActionListener(this);
		loginJB.addMouseListener(new MouseListener()
		{
			
			@Override
			public void mouseReleased(MouseEvent arg0)
			{
				loginJB.setSelected(false);
				loginJB.setIcon(new ImageIcon(config.getLoginbtnURL()));
			}
			
			@Override
			public void mousePressed(MouseEvent arg0)
			{
				loginJB.setSelected(true);
				loginJB.setSelectedIcon(new ImageIcon(config
				        .getLoginbtnPressURL()));
			}
			
			@Override
			public void mouseExited(MouseEvent arg0)
			{
				loginJB.setSelected(false);
				loginJB.setIcon(new ImageIcon(config.getLoginbtnURL()));
			}
			
			@Override
			public void mouseEntered(MouseEvent arg0)
			{
				loginJB.setSelected(true);
				loginJB.setSelectedIcon(new ImageIcon(config
				        .getLoginbtnHoverURL()));
			}
			
			@Override
			public void mouseClicked(MouseEvent arg0)
			{
				
			}
		});
	}
	
	
	/////////////////////////////实现（覆写）方法//////////////////////////////////////////
	/**
	 * When the action event occurs, that object's actionPerformed method is invoked.
	 */
	@Override
	public void actionPerformed(ActionEvent event)
	{
		System.out.println("点击了登录按钮！！");
		// 大年初一，无聊...
		switch(event.getActionCommand())
		{
			case "登录":
				toLogin();
				break;
			default:break;
		}
	}
	
	private void toLogin()
    {
		
		if(userNameJTF.getText() != null && !"".equals(userNameJTF.getText())
				&& passWordJTF.getPassword() != null && passWordJTF.getPassword().length > 0)
		{
			if(!config.getUserNameHint().equals(userNameJTF.getText()) && !config.getPassWordHint().equals(passWordJTF.getText()))
			{
				/*
				 * 场景：
				 *    用户先点击了记住密码，此时，无账号密码数据写入配置文件。
				 *    因此，在用户点击登录后检查一下记录密码的复选框是否勾选并做相应的操作。
				 */
				if(isChecked)
				{
					 ConfigurationManager.writeLoginInfo2Prop(Constants.TRUE, userNameJTF.getText(), passWordJTF.getText());
				}
				
				CommonData.setId(userNameJTF.getText().trim());
				CommonData.setSex("男"); // 暂时默认为 “男”
				// 与服务器交互
				// 发送登录请求给服务器
				ChatClientProxy.getInstance().sendRequest(DataUtil.genMessage(MessageTypeEnum.Login, userNameJTF.getText(), passWordJTF.getText()));
				// 关闭当前的登录窗口
				this.dispose();
			}
			else
			{
				JOptionPane.showMessageDialog(getContentPane(), "请输入用户名或密码！", "温馨提示",JOptionPane.WARNING_MESSAGE);
			}
		}
    }

	// For test ...
	public static void main(String[] args)
	{
		new LoginUI().setVisible(true);
	}
	
}
