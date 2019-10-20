package model.biz;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.HashMap;

import javax.swing.JOptionPane;

import model.entity.userimformation;

import view.LoginView;
import view.MainView;

import comondata.comondata;

public class LoginHandleBiz {
	private static final LoginHandleBiz loginHandleBiz = new LoginHandleBiz(); // ���屾��ʵ������

	private LoginHandleBiz() {

	}

	/**
	 * ���ص�¼���������ʵ��
	 * 
	 * @return
	 */
	public static LoginHandleBiz getLoginHandleBiz() {
		return LoginHandleBiz.loginHandleBiz;
	}

	/**
	 * �����¼���
	 * 
	 * @param resultStr
	 */
	public void loginHandle(String resultStr) {
		// resultStr ��ʽ��result
		int resultValue = Integer.parseInt(resultStr.trim().split(":")[0]);
		comondata.setName(resultStr.trim().split(":")[1]);

		// ��¼�ɹ�
		if (resultValue == 1) {
			// System.out.println("login successfully! \n I am " +
			// comondata.getName());

			// ����������ʵ��
			MainView mainFrame = new MainView();
			mainFrame.setVisible(true);

			// ����ȫ����Ϣ�е�������
			comondata.setMainFrame(mainFrame);
		}

		// ����ͬ���û�
		else if (resultValue == 0) {
			JOptionPane.showMessageDialog(null, "���û��Ѿ���¼��", "information",
					JOptionPane.INFORMATION_MESSAGE);
			new LoginView().setVisible(true);
		}

		// ��¼����
		else if (resultValue == -1) {
			JOptionPane.showMessageDialog(null, "�������", "erro",
					JOptionPane.ERROR_MESSAGE);
			new LoginView().setVisible(true);
		}
	}

	/**
	 * �����¼�󷵻ص������û�������Ϣ��
	 * 
	 * @param msgStr_
	 *            ��ʽ��objectString
	 */
	@SuppressWarnings("unchecked")
	public void onlineUserDetailReturnHandle(String msgStr_) {
		byte[] buf;// ������

		sun.misc.BASE64Decoder decoder = new sun.misc.BASE64Decoder();
		try {
			// �����ַ���,ת�����ֽ�����
			buf = decoder.decodeBuffer(msgStr_);

			// �ֽ�����ת������
			ObjectInputStream ois = new ObjectInputStream(
					new ByteArrayInputStream(buf));

			// ��ȡ����
			HashMap<String, userimformation> UserImformaintionMap = (HashMap<String, userimformation>) ois
					.readObject();

			// System.out.println("UserInformationMap");

			// ͬ��������Ϣ
			comondata.setUserImformationMap(UserImformaintionMap);

			// ��ʼ�������û�
			comondata.getMainFrame().initUserList(UserImformaintionMap);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * �����¼�󷵻ص������û�������Ϣ��
	 * 
	 * @param msgStr_
	 *            ��ʽ��objectString
	 */
	@SuppressWarnings("unchecked")
	public void offlineUserDetailReturnHandle(String msgStr_) {
		byte[] buf;// ������

		sun.misc.BASE64Decoder decoder = new sun.misc.BASE64Decoder();
		try {
			// �����ַ���,ת�����ֽ�����
			buf = decoder.decodeBuffer(msgStr_);

			// �ֽ�����ת������
			ObjectInputStream ois = new ObjectInputStream(
					new ByteArrayInputStream(buf));

			// ��ȡ����
			HashMap<String, userimformation> UserImformaintionMap = (HashMap<String, userimformation>) ois
					.readObject();

			// System.out.println("UserInformationMap");

			// ͬ��������Ϣ
			comondata.setUserImformationMap(UserImformaintionMap);

			// ��ʼ�������û�
			comondata.getMainFrame().initOffUserList(UserImformaintionMap);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * ���������û�����
	 * 
	 * @param msgStr_
	 *            ��Ϣ��ʽ��userName:::Sex
	 */
	public void newClientConnectHandle(String msgStr_) {
		String[] msgInfos = msgStr_.split(":::");
		String userName = msgInfos[0];
		String isLogin = msgInfos[1];

		// ��ӵ���������
		if (isLogin.equals("LOGIN")) {
			comondata.getMainFrame().addOnlineUserItem(userName);
		} else if (isLogin.equals("LOGOUT")) {
			comondata.getMainFrame().addOfflineUserItem(userName);
		}
	}

	/**
	 * �����û�����
	 * 
	 * @param msgStr_
	 *            ��Ϣ��ʽ��userName
	 */
	public void ClientOffLineHandle(String msgStr_) {
		String[] msgInfos = msgStr_.split(":::");
		String userName = msgInfos[0];

		System.out.println("test clientofflinehandle " + userName);
		comondata.getMainFrame().addOfflineUserItem(userName);
	}
}