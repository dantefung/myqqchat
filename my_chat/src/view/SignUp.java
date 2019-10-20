package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import comondata.comondata;
import controller.MessageTypeEnum;
import controller.receivequestion;

/**
 * 登录界面
 * 
 * @author KumaHime
 * 
 */
public class SignUp extends JFrame {
	private JLabel niknameLab; // 昵称
	private JLabel passwordLab; // 密码标签
	private JLabel repasswordLab; // 确认密码标签
	private JLabel sexLab; // 性别标签
	private JLabel birthLab; // 生日标签
	private JLabel liveLab; // 住址标签
	private JLabel verifyLab; // 验证标签
	private JLabel agreeLab; // 同意标签
	private JLabel readLab; // 已阅读标签
	private JLabel femaleLab; // 女性标签
	private JLabel maleLab; // 男性标签

	JButton signupBtn; // 注册按钮

	private JTextField niknameField; // 昵称输入框
	private JTextField verifyField; // 邀请码输入框
	private JPasswordField passwordField; // 密码输入框
	private JPasswordField repasswordField; // 确认密码输入框

	private JRadioButton maleSelectBtn; // 男性选择按钮 （单选按钮）
	private JRadioButton femaleSelectBtn; // 女性选择按钮

	private JComboBox<String> yearCombox; // 年份的选择下拉框（组合框）
	private JComboBox<String> monthCombox; // 月份的选择下拉框
	private JComboBox<String> dayCombox; // 日期的选择下拉框
	private JComboBox<String> countryComBox; // 国家选择下拉框
	private JComboBox<String> provinceCombox; // 省选择下啦框

	private JButton signinBtn; // 注册按钮
	private Dimension labSize = new Dimension(100, 50); // 标签大小
	private Dimension txtSize = new Dimension(300, 30); // 输入框大小


	private final int yearLong = 150; // 年份长度

	/**
	 * 初始化界面（界面大小 500*500)
	 */
	private void init() {
		this.setAlwaysOnTop(true);
		
		// 设置界面风格为系统风格
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		// 提前设定界面大小
		this.setSize(500, 500);
		// 提前设置布局格式（需要自由改变界面）
		this.getContentPane().setLayout(null);
		// 提前设置颜色
		this.getContentPane().setBackground(Color.white);

		// 标签初始化开始
		int i = 0;
		int labBeginX = 0, labBegiY = 0;

		
		//////////////////////////////页面组件的准备/////////////////////////////
		niknameLab = new JLabel("昵称  ", JLabel.CENTER);
		niknameLab.setFont(comondata.getInputFont());
		niknameLab.setHorizontalAlignment(JLabel.RIGHT);
		niknameLab.setLocation(labBeginX, labBegiY + (labSize.height * i++));
		niknameLab.setSize(labSize);

		passwordLab = new JLabel("密码  ", JLabel.CENTER);
		passwordLab.setFont(comondata.getInputFont());
		passwordLab.setHorizontalAlignment(JLabel.RIGHT);
		passwordLab.setLocation(labBeginX, labBegiY + (labSize.height * i++));
		passwordLab.setSize(labSize);

		repasswordLab = new JLabel("确认密码  ", JLabel.CENTER);
		repasswordLab.setFont(comondata.getInputFont());
		repasswordLab.setHorizontalAlignment(JLabel.RIGHT);
		repasswordLab.setLocation(labBeginX, labBegiY + (labSize.height * i++));
		repasswordLab.setSize(labSize);

		sexLab = new JLabel("性别  ", JLabel.CENTER);
		sexLab.setFont(comondata.getInputFont());
		sexLab.setHorizontalAlignment(JLabel.RIGHT);
		sexLab.setLocation(labBeginX, labBegiY + (labSize.height * i++));
		sexLab.setSize(labSize);

		birthLab = new JLabel("生日  ", JLabel.CENTER);
		birthLab.setFont(comondata.getInputFont());
		birthLab.setHorizontalAlignment(JLabel.RIGHT);
		birthLab.setLocation(labBeginX, labBegiY + (labSize.height * i++));
		birthLab.setSize(labSize);

		liveLab = new JLabel("所在地  ", JLabel.CENTER);
		liveLab.setFont(comondata.getInputFont());
		liveLab.setHorizontalAlignment(JLabel.RIGHT);
		liveLab.setLocation(labBeginX, labBegiY + (labSize.height * i++));
		liveLab.setSize(labSize);

		verifyLab = new JLabel("邀请码  ", JLabel.CENTER);
		verifyLab.setFont(comondata.getInputFont());
		verifyLab.setHorizontalAlignment(JLabel.RIGHT);
		verifyLab.setLocation(labBeginX, labBegiY + (labSize.height * i++));
		verifyLab.setSize(labSize);

		maleLab = new JLabel("男", JLabel.CENTER);
		maleLab.setHorizontalAlignment(JLabel.LEFT);
		maleLab.setFont(comondata.getInputFont());
		maleLab.setSize(40, 20);
		maleLab.setLocation(
				sexLab.getLocation().x + sexLab.getSize().width + 20,
				sexLab.getLocation().y + sexLab.getHeight() / 2
						- maleLab.getHeight() / 2);

		femaleLab = new JLabel("女", JLabel.CENTER);
		femaleLab.setHorizontalAlignment(JLabel.LEFT);
		femaleLab.setFont(comondata.getInputFont());
		femaleLab.setSize(40, 20);
		femaleLab.setLocation(
				maleLab.getLocation().x + maleLab.getSize().width + 20,
				sexLab.getLocation().y + sexLab.getHeight() / 2
						- maleLab.getHeight() / 2);

		agreeLab = new JLabel("同意责任申明", JLabel.CENTER);

		readLab = new JLabel("已阅读责任申明", JLabel.CENTER);

		//////////////////////////将组件添加到内容面板里面去/////////////////////////////
		this.getContentPane().add(niknameLab);
		this.getContentPane().add(passwordLab);
		this.getContentPane().add(repasswordLab);
		this.getContentPane().add(sexLab);
		this.getContentPane().add(birthLab);
		this.getContentPane().add(liveLab);
		this.getContentPane().add(verifyLab);
		this.getContentPane().add(maleLab);
		this.getContentPane().add(femaleLab);
		// 标签初始化完毕

		// 输入区域初始化开始
		niknameField = new JTextField();
		niknameField.setSize(txtSize);
		niknameField.setLocation(
				niknameLab.getLocation().x + niknameLab.getWidth(),
				niknameLab.getLocation().y
						+ (niknameLab.getHeight() - niknameField.getHeight())
						/ 2);

		passwordField = new JPasswordField();
		passwordField.setSize(txtSize);
		passwordField.setLocation(
				passwordLab.getLocation().x + passwordLab.getWidth(),
				passwordLab.getLocation().y
						+ (passwordLab.getHeight() - passwordField.getHeight())
						/ 2);

		repasswordField = new JPasswordField();
		repasswordField.setSize(txtSize);
		repasswordField
				.setLocation(
						repasswordLab.getLocation().x
								+ repasswordLab.getWidth(),
						repasswordLab.getLocation().y
								+ (repasswordLab.getHeight() - repasswordField
										.getHeight()) / 2);

		verifyField = new JTextField();
		verifyField.setSize(180, repasswordField.getSize().height);
		verifyField.setLocation(verifyLab.getLocation().x
				+ verifyLab.getSize().width, verifyLab.getLocation().y
				+ verifyLab.getSize().height / 2 - verifyField.getSize().height
				/ 2);

		this.getContentPane().add(niknameField);
		this.getContentPane().add(passwordField);
		this.getContentPane().add(repasswordField);
		this.getContentPane().add(verifyField);
		// 输入区域初始化完毕

		// 选择区域初始化开始
		maleSelectBtn = new JRadioButton();
		maleSelectBtn.setEnabled(false);
		maleSelectBtn.setSize(20, 20);
		maleSelectBtn.setSelected(true);

		maleSelectBtn.setLocation(
				sexLab.getLocation().x + sexLab.getWidth(),
				sexLab.getLocation().y + sexLab.getHeight() / 2
						- maleSelectBtn.getHeight() / 2);
		maleSelectBtn.setBackground(Color.white);
		maleSelectBtn.setFocusPainted(false);

		femaleSelectBtn = new JRadioButton();
		femaleSelectBtn.setEnabled(false);
		femaleSelectBtn.setSize(20, 20);

		femaleSelectBtn.setLocation(maleSelectBtn.getLocation().x
				+ maleSelectBtn.getWidth() + maleLab.getWidth(),
				sexLab.getLocation().y + sexLab.getHeight() / 2
						- femaleSelectBtn.getHeight() / 2);
		femaleSelectBtn.setBackground(Color.white);
		femaleSelectBtn.setFocusPainted(false);

		maleSelectBtn.addMouseListener(new sexChange(maleSelectBtn,
				femaleSelectBtn));
		femaleSelectBtn.addMouseListener(new sexChange(femaleSelectBtn,
				maleSelectBtn));
		maleLab.addMouseListener(new sexChange(maleSelectBtn, femaleSelectBtn));
		femaleLab
				.addMouseListener(new sexChange(femaleSelectBtn, maleSelectBtn));

		this.getContentPane().add(maleSelectBtn);
		this.getContentPane().add(femaleSelectBtn);
		// 选择区域初始化结束

		// 选择下拉框初始化开始
		String[] year = new String[yearLong];
		for (int j = 0; j < year.length; j++) {
			year[j] = String.valueOf(j + 1900) + "年";
		}
		yearCombox = new JComboBox<String>(year);
		yearCombox.setSize(80, birthLab.getSize().height - 30);
		yearCombox.setLocation(birthLab.getLocation().x + birthLab.getWidth(),
				birthLab.getLocation().y + birthLab.getSize().height / 2
						- yearCombox.getSize().height / 2);
		yearCombox.setFont(comondata.getComboxFont());
		yearCombox.setEditable(true);

		String[] month = new String[12];
		for (int j = 0; j < month.length; j++) {
			month[j] = String.valueOf(j + 1) + "月";
		}
		monthCombox = new JComboBox<String>(month);
		monthCombox.setSize(70, yearCombox.getSize().height);
		monthCombox.setLocation(
				yearCombox.getLocation().x + yearCombox.getWidth(),
				yearCombox.getLocation().y + yearCombox.getSize().height / 2
						- monthCombox.getSize().height / 2);
		monthCombox.setFont(comondata.getComboxFont());
		monthCombox.setEditable(true);

		String[] day = new String[31];
		for (int j = 0; j < day.length; j++) {
			day[j] = String.valueOf(j + 1) + "日";
		}
		dayCombox = new JComboBox<String>(day);
		dayCombox.setSize(70, monthCombox.getSize().height);
		dayCombox.setLocation(
				monthCombox.getLocation().x + monthCombox.getWidth(),
				monthCombox.getLocation().y + monthCombox.getSize().height / 2
						- monthCombox.getSize().height / 2);
		dayCombox.setFont(comondata.getComboxFont());
		dayCombox.setEditable(true);

		String[] country = { "中国", "美国", "日本", "德国" };
		countryComBox = new JComboBox<String>(country);
		countryComBox.setSize(100, liveLab.getSize().height - 30);
		countryComBox.setLocation(liveLab.getLocation().x + liveLab.getWidth(),
				liveLab.getLocation().y + liveLab.getSize().height / 2
						- countryComBox.getSize().height / 2);
		countryComBox.setFont(comondata.getComboxFont());
		countryComBox.setEditable(true);

		String[] province = { "重庆", "北京", "天津", "上海", "广东", "西藏" };
		provinceCombox = new JComboBox<String>(province);
		provinceCombox.setSize(70, countryComBox.getSize().height);
		provinceCombox.setLocation(countryComBox.getLocation().x
				+ countryComBox.getWidth(), countryComBox.getLocation().y
				+ countryComBox.getSize().height / 2
				- provinceCombox.getSize().height / 2);
		provinceCombox.setFont(comondata.getComboxFont());
		provinceCombox.setEditable(true);

		this.getContentPane().add(yearCombox);
		this.getContentPane().add(monthCombox);
		this.getContentPane().add(dayCombox);
		this.getContentPane().add(countryComBox);
		this.getContentPane().add(provinceCombox);
		// 选择下拉框初始化结束

		// 按钮初始化开始
		signinBtn = new JButton("立即注册");
		signinBtn.setFont(comondata.getSignUpFont());
		signinBtn.setSize(180, 40);
		signinBtn.setLocation(verifyLab.getLocation().x
				+ verifyLab.getSize().width, verifyLab.getLocation().y
				+ verifyLab.getSize().height);
		signinBtn.setActionCommand("注册");
		signinBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getActionCommand().equals("注册")) {
					String msg = getSignUp();
					if (msg == null) {
						System.err.println("未填满信息");
						return;
					}
					switch (msg) {
					case "输入的密码不一致":
						System.err.println(msg);
						break;

					default:
						receivequestion.getMainSocket().signUp(msg);
						break;
					}
				}
			}
		});
		signinBtn.setFocusPainted(false);

		this.getContentPane().add(signinBtn);
		// 按钮初始化结束

		// test begin
		// test end

		this.setResizable(false);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setLocation(Toolkit.getDefaultToolkit().getScreenSize().width / 2
				- getWidth() / 2,
				Toolkit.getDefaultToolkit().getScreenSize().height / 2
						- getHeight() / 2);
		this.setVisible(true);
	}

	/**
	 * 构造函数
	 */
	public SignUp() {
		// 初始化界面
		init();
	}

	/**
	 * 设置多选项选择按钮聚焦类
	 * 
	 * @author KumaHime
	 * 
	 */
	class selectFocus implements FocusListener {
		private JComboBox<String> mainCombo;

		public selectFocus(JComboBox<String> monthCombox) {
			mainCombo = monthCombox;
		}

		@Override
		public void focusGained(FocusEvent arg0) {
			System.out.println(mainCombo.getEditor().getItem());
			System.out.println("focus");
		}

		@Override
		public void focusLost(FocusEvent arg0) {
			System.out.println("no focus");
		}

	}

	/**
	 * 设置性别选择按钮鼠标监听类
	 * 
	 * @author KumaHime
	 * 
	 */
	class sexChange implements MouseListener {
		private JRadioButton selfRadio;
		private JRadioButton otherRadio;

		public sexChange(JRadioButton sjr, JRadioButton ojr) {
			selfRadio = sjr;
			otherRadio = ojr;
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			if (e.getButton() == MouseEvent.BUTTON1) {
				selfRadio.setSelected(true);
				otherRadio.setSelected(false);
			}
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			setCursor(java.awt.Cursor
					.getPredefinedCursor(java.awt.Cursor.HAND_CURSOR));
		}

		@Override
		public void mouseExited(MouseEvent e) {
			setCursor(java.awt.Cursor.getDefaultCursor());
		}

		@Override
		public void mousePressed(MouseEvent e) {

		}

		@Override
		public void mouseReleased(MouseEvent e) {

		}
	}

	/**
	 * 获取将要登录信息的字符串
	 * 
	 * @return 如果为null，则表示失败，否则，将返回要登录的信息的字符串
	 */
	private String getSignUp() {
		// 分割符号
		String sep = "\r";

		if (niknameField.getText().equals("")
				|| String.valueOf(passwordField.getPassword()).equals("")
				|| String.valueOf(repasswordField.getPassword()).equals("")
				|| String.valueOf(verifyField.getText()).equals("")) {
			return null;
		}

		if (!String.valueOf(passwordField.getPassword()).equals(
				String.valueOf(repasswordField.getPassword()))) {
			return "输入的密码不一致";
		}
		
		// 性别
		String sex = maleSelectBtn.isSelected() ? "男" : "女";

		String birth = (String) yearCombox.getSelectedItem() + " "
				+ (String) monthCombox.getSelectedItem() + " "
				+ (String) dayCombox.getSelectedItem();

		// 所在地
		String live = (String) countryComBox.getSelectedItem() + " "
				+ (String) provinceCombox.getSelectedItem();

		// 返回值
		String msg = MessageTypeEnum.SignUp.toString() + ":::";

		//
		msg += niknameField.getText() + sep
				+ String.valueOf(passwordField.getPassword()) + sep + sex + sep
				+ birth + sep + live;
		return msg;
	}

	public static void main(String[] args) {
		new SignUp();
	}
}
