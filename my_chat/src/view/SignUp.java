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
 * ��¼����
 * 
 * @author KumaHime
 * 
 */
public class SignUp extends JFrame {
	private JLabel niknameLab; // �ǳ�
	private JLabel passwordLab; // �����ǩ
	private JLabel repasswordLab; // ȷ�������ǩ
	private JLabel sexLab; // �Ա��ǩ
	private JLabel birthLab; // ���ձ�ǩ
	private JLabel liveLab; // סַ��ǩ
	private JLabel verifyLab; // ��֤��ǩ
	private JLabel agreeLab; // ͬ���ǩ
	private JLabel readLab; // ���Ķ���ǩ
	private JLabel femaleLab; // Ů�Ա�ǩ
	private JLabel maleLab; // ���Ա�ǩ

	JButton signupBtn; // ע�ᰴť

	private JTextField niknameField; // �ǳ������
	private JTextField verifyField; // �����������
	private JPasswordField passwordField; // ���������
	private JPasswordField repasswordField; // ȷ�����������

	private JRadioButton maleSelectBtn; // ����ѡ��ť ����ѡ��ť��
	private JRadioButton femaleSelectBtn; // Ů��ѡ��ť

	private JComboBox<String> yearCombox; // ��ݵ�ѡ����������Ͽ�
	private JComboBox<String> monthCombox; // �·ݵ�ѡ��������
	private JComboBox<String> dayCombox; // ���ڵ�ѡ��������
	private JComboBox<String> countryComBox; // ����ѡ��������
	private JComboBox<String> provinceCombox; // ʡѡ��������

	private JButton signinBtn; // ע�ᰴť
	private Dimension labSize = new Dimension(100, 50); // ��ǩ��С
	private Dimension txtSize = new Dimension(300, 30); // ������С


	private final int yearLong = 150; // ��ݳ���

	/**
	 * ��ʼ�����棨�����С 500*500)
	 */
	private void init() {
		this.setAlwaysOnTop(true);
		
		// ���ý�����Ϊϵͳ���
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		// ��ǰ�趨�����С
		this.setSize(500, 500);
		// ��ǰ���ò��ָ�ʽ����Ҫ���ɸı���棩
		this.getContentPane().setLayout(null);
		// ��ǰ������ɫ
		this.getContentPane().setBackground(Color.white);

		// ��ǩ��ʼ����ʼ
		int i = 0;
		int labBeginX = 0, labBegiY = 0;

		
		//////////////////////////////ҳ�������׼��/////////////////////////////
		niknameLab = new JLabel("�ǳ�  ", JLabel.CENTER);
		niknameLab.setFont(comondata.getInputFont());
		niknameLab.setHorizontalAlignment(JLabel.RIGHT);
		niknameLab.setLocation(labBeginX, labBegiY + (labSize.height * i++));
		niknameLab.setSize(labSize);

		passwordLab = new JLabel("����  ", JLabel.CENTER);
		passwordLab.setFont(comondata.getInputFont());
		passwordLab.setHorizontalAlignment(JLabel.RIGHT);
		passwordLab.setLocation(labBeginX, labBegiY + (labSize.height * i++));
		passwordLab.setSize(labSize);

		repasswordLab = new JLabel("ȷ������  ", JLabel.CENTER);
		repasswordLab.setFont(comondata.getInputFont());
		repasswordLab.setHorizontalAlignment(JLabel.RIGHT);
		repasswordLab.setLocation(labBeginX, labBegiY + (labSize.height * i++));
		repasswordLab.setSize(labSize);

		sexLab = new JLabel("�Ա�  ", JLabel.CENTER);
		sexLab.setFont(comondata.getInputFont());
		sexLab.setHorizontalAlignment(JLabel.RIGHT);
		sexLab.setLocation(labBeginX, labBegiY + (labSize.height * i++));
		sexLab.setSize(labSize);

		birthLab = new JLabel("����  ", JLabel.CENTER);
		birthLab.setFont(comondata.getInputFont());
		birthLab.setHorizontalAlignment(JLabel.RIGHT);
		birthLab.setLocation(labBeginX, labBegiY + (labSize.height * i++));
		birthLab.setSize(labSize);

		liveLab = new JLabel("���ڵ�  ", JLabel.CENTER);
		liveLab.setFont(comondata.getInputFont());
		liveLab.setHorizontalAlignment(JLabel.RIGHT);
		liveLab.setLocation(labBeginX, labBegiY + (labSize.height * i++));
		liveLab.setSize(labSize);

		verifyLab = new JLabel("������  ", JLabel.CENTER);
		verifyLab.setFont(comondata.getInputFont());
		verifyLab.setHorizontalAlignment(JLabel.RIGHT);
		verifyLab.setLocation(labBeginX, labBegiY + (labSize.height * i++));
		verifyLab.setSize(labSize);

		maleLab = new JLabel("��", JLabel.CENTER);
		maleLab.setHorizontalAlignment(JLabel.LEFT);
		maleLab.setFont(comondata.getInputFont());
		maleLab.setSize(40, 20);
		maleLab.setLocation(
				sexLab.getLocation().x + sexLab.getSize().width + 20,
				sexLab.getLocation().y + sexLab.getHeight() / 2
						- maleLab.getHeight() / 2);

		femaleLab = new JLabel("Ů", JLabel.CENTER);
		femaleLab.setHorizontalAlignment(JLabel.LEFT);
		femaleLab.setFont(comondata.getInputFont());
		femaleLab.setSize(40, 20);
		femaleLab.setLocation(
				maleLab.getLocation().x + maleLab.getSize().width + 20,
				sexLab.getLocation().y + sexLab.getHeight() / 2
						- maleLab.getHeight() / 2);

		agreeLab = new JLabel("ͬ����������", JLabel.CENTER);

		readLab = new JLabel("���Ķ���������", JLabel.CENTER);

		//////////////////////////�������ӵ������������ȥ/////////////////////////////
		this.getContentPane().add(niknameLab);
		this.getContentPane().add(passwordLab);
		this.getContentPane().add(repasswordLab);
		this.getContentPane().add(sexLab);
		this.getContentPane().add(birthLab);
		this.getContentPane().add(liveLab);
		this.getContentPane().add(verifyLab);
		this.getContentPane().add(maleLab);
		this.getContentPane().add(femaleLab);
		// ��ǩ��ʼ�����

		// ���������ʼ����ʼ
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
		// ���������ʼ�����

		// ѡ�������ʼ����ʼ
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
		// ѡ�������ʼ������

		// ѡ���������ʼ����ʼ
		String[] year = new String[yearLong];
		for (int j = 0; j < year.length; j++) {
			year[j] = String.valueOf(j + 1900) + "��";
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
			month[j] = String.valueOf(j + 1) + "��";
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
			day[j] = String.valueOf(j + 1) + "��";
		}
		dayCombox = new JComboBox<String>(day);
		dayCombox.setSize(70, monthCombox.getSize().height);
		dayCombox.setLocation(
				monthCombox.getLocation().x + monthCombox.getWidth(),
				monthCombox.getLocation().y + monthCombox.getSize().height / 2
						- monthCombox.getSize().height / 2);
		dayCombox.setFont(comondata.getComboxFont());
		dayCombox.setEditable(true);

		String[] country = { "�й�", "����", "�ձ�", "�¹�" };
		countryComBox = new JComboBox<String>(country);
		countryComBox.setSize(100, liveLab.getSize().height - 30);
		countryComBox.setLocation(liveLab.getLocation().x + liveLab.getWidth(),
				liveLab.getLocation().y + liveLab.getSize().height / 2
						- countryComBox.getSize().height / 2);
		countryComBox.setFont(comondata.getComboxFont());
		countryComBox.setEditable(true);

		String[] province = { "����", "����", "���", "�Ϻ�", "�㶫", "����" };
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
		// ѡ���������ʼ������

		// ��ť��ʼ����ʼ
		signinBtn = new JButton("����ע��");
		signinBtn.setFont(comondata.getSignUpFont());
		signinBtn.setSize(180, 40);
		signinBtn.setLocation(verifyLab.getLocation().x
				+ verifyLab.getSize().width, verifyLab.getLocation().y
				+ verifyLab.getSize().height);
		signinBtn.setActionCommand("ע��");
		signinBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getActionCommand().equals("ע��")) {
					String msg = getSignUp();
					if (msg == null) {
						System.err.println("δ������Ϣ");
						return;
					}
					switch (msg) {
					case "��������벻һ��":
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
		// ��ť��ʼ������

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
	 * ���캯��
	 */
	public SignUp() {
		// ��ʼ������
		init();
	}

	/**
	 * ���ö�ѡ��ѡ��ť�۽���
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
	 * �����Ա�ѡ��ť��������
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
	 * ��ȡ��Ҫ��¼��Ϣ���ַ���
	 * 
	 * @return ���Ϊnull�����ʾʧ�ܣ����򣬽�����Ҫ��¼����Ϣ���ַ���
	 */
	private String getSignUp() {
		// �ָ����
		String sep = "\r";

		if (niknameField.getText().equals("")
				|| String.valueOf(passwordField.getPassword()).equals("")
				|| String.valueOf(repasswordField.getPassword()).equals("")
				|| String.valueOf(verifyField.getText()).equals("")) {
			return null;
		}

		if (!String.valueOf(passwordField.getPassword()).equals(
				String.valueOf(repasswordField.getPassword()))) {
			return "��������벻һ��";
		}
		
		// �Ա�
		String sex = maleSelectBtn.isSelected() ? "��" : "Ů";

		String birth = (String) yearCombox.getSelectedItem() + " "
				+ (String) monthCombox.getSelectedItem() + " "
				+ (String) dayCombox.getSelectedItem();

		// ���ڵ�
		String live = (String) countryComBox.getSelectedItem() + " "
				+ (String) provinceCombox.getSelectedItem();

		// ����ֵ
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
