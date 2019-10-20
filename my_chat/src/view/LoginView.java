package view;

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
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import model.biz.BtnMouseMotionHandleBiz;
import model.biz.dragHandleBiz;

import comondata.comondata;
import controller.receivequestion;
//Frame 架构，建造，设计。
public class LoginView extends JFrame implements ActionListener {
	private static final long serialVersionUID = -4448454494390799227L;
	private JTextField namefid; // 名字区域
	private JTextField ipfid; // ip区域
	private JPasswordField psfid;// 密码区域
	private JTextField portfid; // 端口区域
	private JLabel gifLabel;
	private JLabel imgLabel;
	private JButton closebtn;
	private JButton minibtn;
	private JLabel signuplab; // 注册标签

	JButton loginbtn; // 登录按钮

	public LoginView() {
		{

			try {
				UIManager.setLookAndFeel(UIManager
						.getCrossPlatformLookAndFeelClassName());
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (UnsupportedLookAndFeelException e) {
				e.printStackTrace();
			}

			this.setSize(400, 300);

			/////////////////////////////组件的准备///////////////////////////
			ImageIcon gifIcon = new ImageIcon("picture/anime.gif");
			gifLabel = new JLabel(gifIcon);
			gifLabel.setSize(gifIcon.getIconWidth(), gifIcon.getIconHeight());

			ImageIcon imgIcon = new ImageIcon("picture/ryuko2.png");
			imgLabel = new JLabel(imgIcon);
			imgLabel.setSize(imgIcon.getIconWidth(), imgIcon.getIconHeight());

			ImageIcon closeIcon = new ImageIcon("picture/qqclose.png");
			closebtn = new JButton(closeIcon);
			closebtn.setLocation(getWidth() - closeIcon.getIconWidth(), 0);
			closebtn.setSize(closeIcon.getIconWidth(),
					closeIcon.getIconHeight());
			closebtn.setContentAreaFilled(false);
			closebtn.setBackground(Color.red);
			closebtn.setBorder(BorderFactory.createEmptyBorder());

			closebtn.addMouseListener(new BtnMouseMotionHandleBiz(this,
					closebtn, "close"));

			ImageIcon miniIcon = new ImageIcon("picture/qqmini.png");
			minibtn = new JButton(miniIcon);
			minibtn.setLocation(
					closebtn.getLocation().x - miniIcon.getIconWidth(),
					closebtn.getLocation().y);
			minibtn.setSize(miniIcon.getIconWidth(), miniIcon.getIconHeight());
			minibtn.setBorder(BorderFactory.createEmptyBorder());
			minibtn.setBackground(comondata.getFocusColor());
			minibtn.setContentAreaFilled(false);

			minibtn.addMouseListener(new BtnMouseMotionHandleBiz(this, minibtn,
					"minimization"));

			gifLabel.add(minibtn);
			gifLabel.add(closebtn);

			namefid = new JTextField("user name");
			namefid.setForeground(Color.gray);
			namefid.setBorder(BorderFactory.createLineBorder(Color.gray));
			namefid.addFocusListener(new logTextFocusListener(namefid,
					"user name"));

			psfid = new JPasswordField("password");
			psfid.setForeground(Color.gray);
			psfid.setBorder(BorderFactory.createLineBorder(Color.gray));
			psfid.setEchoChar((char) 0);
			psfid.addFocusListener(new logTextFocusListener(psfid, "password"));
			portfid = new JTextField();
			portfid.setBorder(BorderFactory.createEtchedBorder());

			loginbtn = new JButton("登录");
			loginbtn.setBorderPainted(false);
			loginbtn.setFocusPainted(false);
			loginbtn.setActionCommand("登录");
			loginbtn.addActionListener(this); 
			loginbtn.setFont(comondata.getBtnFont());
			loginbtn.setBackground(comondata.getPaleTurquoise1());
			
			//New add by Dante-Fung : start
			loginbtn.addMouseListener(new MouseListener(){

				@Override
				public void mouseClicked(MouseEvent e) {
					
				}

				@Override
				public void mousePressed(MouseEvent e) {
					
				}

				@Override
				public void mouseReleased(MouseEvent e) {
					
				}

				@Override
				public void mouseEntered(MouseEvent e) {
					setCursor(java.awt.Cursor
							.getPredefinedCursor(java.awt.Cursor.HAND_CURSOR));
				}

				@Override
				public void mouseExited(MouseEvent e) {
					setCursor(java.awt.Cursor
							.getPredefinedCursor(java.awt.Cursor.DEFAULT_CURSOR));
				}});
			
		   //New add by Dante-Fung : end

			namefid.setLocation(getWidth() / 2 - getWidth() / 4 + 30 / 2,
					getHeight() / 2 - 30 / 2 + 30);
			namefid.setSize(getWidth() / 2 - 30, 30);
			namefid.setFont(comondata.getLogTextFont());
			namefid.addKeyListener(new KeyListener() {

				@Override
				public void keyTyped(KeyEvent e) {
					if (!(e.getKeyChar() >= KeyEvent.VK_0 && e.getKeyChar() <= KeyEvent.VK_9)) {
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

			psfid.setLocation(namefid.getLocation().x,
					namefid.getLocation().y + 30);
			psfid.setSize(namefid.getSize());
			psfid.setFont(comondata.getLogTextFont());
			portfid.setText(String.valueOf(comondata.getServerPort()));

			loginbtn.setLocation(psfid.getLocation().x,
					psfid.getLocation().y + 40);
			loginbtn.setSize(psfid.getSize());

			//设置QQ头像的位置。
			imgLabel.setLocation(
					namefid.getLocation().x - imgIcon.getIconWidth() - 15,
					namefid.getLocation().y);

			signuplab = new JLabel("注册账号");
			signuplab.setForeground(Color.blue);
			signuplab.setBackground(comondata.getPaleTurquoise1());
			signuplab.setHorizontalAlignment(JLabel.LEFT);
			signuplab.setSize(60, namefid.getSize().height);
			signuplab.setLocation(namefid.getLocation().x
					+ namefid.getSize().width, namefid.getLocation().y);
			signuplab.addMouseListener(new MouseListener() {

				@Override
				public void mouseReleased(MouseEvent arg0) {
					if (comondata.getSignUp() == null) {
						comondata.setSignUp(new SignUp());
					}
				}

				@Override
				public void mousePressed(MouseEvent arg0) {
				}

				@Override
				public void mouseExited(MouseEvent arg0) {
					setCursor(java.awt.Cursor.getDefaultCursor());
				}

				@Override
				public void mouseEntered(MouseEvent arg0) {
					setCursor(java.awt.Cursor
							.getPredefinedCursor(java.awt.Cursor.HAND_CURSOR));
				}

				@Override
				public void mouseClicked(MouseEvent arg0) {

				}
			});

			//////////////////////Frame获得内容面板，添加组件至内容面板///////////////////////////
			this.getContentPane().setBackground(Color.white);
			this.getContentPane().setLayout(null);
			this.getContentPane().add(gifLabel, 0);
			this.getContentPane().add(namefid);
			this.getContentPane().add(psfid);
			this.getContentPane().add(loginbtn);
			this.getContentPane().add(imgLabel);
			this.getContentPane().add(signuplab);

			this.setAlwaysOnTop(true); // 置顶
			this.setDefaultCloseOperation(DISPOSE_ON_CLOSE); // 默认点关闭按钮时退出程序
			this.setResizable(false);//可变尺寸。
			Dimension screenDim = Toolkit.getDefaultToolkit().getScreenSize();//获得屏幕的大小（维度）
			this.setLocation((screenDim.width - 350) / 2,
					(screenDim.height - 250) / 2); // 屏幕居中
			this.getContentPane().setBackground(comondata.getqqColor());
			this.setUndecorated(true);//去掉默认的边框修饰，可注释掉，运行后对比注释前后的效果
			dragHandleBiz dragAdp = new dragHandleBiz(this);
			this.addMouseListener(dragAdp);
			this.addMouseMotionListener(dragAdp);

			this.setTitle("登录");//设置了无默认修饰this.setUndecorated(true);
			                     //则标题的设置显得没有必要。this.setTitle("登录");可去掉。

			//内部类：监听键盘事件。
			class MyKeyListener implements KeyListener {

				@Override
				public void keyTyped(KeyEvent e) {
				}

				@Override
				public void keyPressed(KeyEvent e) {
					if (e.getKeyChar() == '\n') {//监听到回车就登录。
						MyLog();
					}
				}

				@Override
				public void keyReleased(KeyEvent e) {
				}

			}

			MyKeyListener myKey = new MyKeyListener();

			psfid.addKeyListener(myKey);
			namefid.addKeyListener(myKey);
			portfid.addKeyListener(myKey);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String actionStr = e.getActionCommand();
		switch (actionStr) {
		case "登录": {
			MyLog();

			break;
		}

		default:
			break;
		}
	}

	public void MyLog() {

		// 设置用户名和性别
		comondata.setId(namefid.getText().trim());
		comondata.setSex("男"); // 暂时默认为 “男”

		// 启动登录线程
		receivequestion.getMainSocket().login(namefid.getText().trim(),
				String.valueOf(psfid.getPassword()));

		this.dispose();
	}

	//工具类：聚焦
	private class logTextFocusListener implements FocusListener {
		private JTextField mainFild;
		private String hint;
		private JPasswordField mainpFild;
		private boolean password = false;
		private boolean empty = true;

		public logTextFocusListener(JTextField txtField, String hint) {
			mainFild = txtField;
			this.hint = hint;//暗示，示意，线索。
			password = false;
		}

		public logTextFocusListener(JPasswordField txtField, String hint) {
			mainpFild = txtField;
			mainFild = txtField;
			this.hint = hint;
			password = true;
		}

		@Override
		public void focusGained(FocusEvent e) {
			mainFild.setBorder(BorderFactory.createEtchedBorder(Color.gray,
					comondata.getFocusColor()));//一旦密码输入框获得焦点之后，就改变输入框的边界的颜色。
			if (empty) {
				mainFild.setText("");//重置为空，将暗示性文字去掉
				mainFild.setForeground(Color.black);
				if (password) {
					mainpFild.setEchoChar('●');//如果是密码显示的字符全部为●
				}
			}
		}

		@Override
		public void focusLost(FocusEvent e) {
			if (mainFild.getText().equals("")) {
				if (password) {
					mainpFild.setEchoChar((char) 0);
				}
				mainFild.setForeground(Color.gray);
				mainFild.setText(hint);
				empty = true;
			} else {
				empty = false;
			}
			mainFild.setBorder(BorderFactory.createLineBorder(Color.gray));
		}

	}
}
