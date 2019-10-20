package controller;

import java.io.IOException;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.filechooser.FileNameExtensionFilter;

import comondata.comondata;

public class receivequestion {
	private static final receivequestion mainSocket = new receivequestion(); // ���屾��ʵ������
	private static Socket socket; // ���Ӷ���
	private static clientthread serverThread; // �����߳�

	private receivequestion() {
		try {
			// System.out.println(comondata.getServerIp());
			// System.out.println(comondata.getServerPort());

			// ��ʼ������
			socket = new Socket(comondata.getServerIp().trim(),
					comondata.getServerPort());
			serverThread = new clientthread(socket);
			System.out.println("clientThread="+serverThread);
			// ���ӷ���������������
			this.getServerThread().start();

		} catch (ConnectException e) {
			System.err.println("δ���ҵ�������");
			// e.printStackTrace();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * ��ȡ������ʵ��
	 * 
	 * @return ������ʵ��  receivequestion mainSocket
	 */
	public static receivequestion getMainSocket() {
		return mainSocket;
	}

	/**
	 * ��ȡ�����߳�
	 * 
	 * @return �����߳�ʵ��
	 */
	public clientthread getServerThread() {
		return serverThread;
	}

	/**
	 * ���ӷ����������Ե���
	 * 
	 * @param userName_
	 * @param sex_
	 */
	public void login(String userName_, String password_) {
		// sendMsg��ʽΪmsgType:::name:::password
		String sendMsg = MessageTypeEnum.Login.toString() + ":::" + userName_
				+ ":::" + password_;

		// ������Ϣ
		this.getServerThread().sendData(sendMsg);

	}
	
	/**
	 * ע���û�
	 * @param msg ע���û��ַ���,signup(����) :::(���ͷָ���) name \r(Ԫ�طָ���) sex \r(Ԫ�طָ���) birth \r(Ԫ�طָ���) live 
	 */
	public void signUp(String msg){
		
		// ������Ϣ
		this.getServerThread().sendData(msg);
	}

	/**
	 * �����ļ�����
	 * 
	 * @param Type
	 *            ��������
	 * @param usernameString
	 *            �û���
	 * @param ip
	 *            �û���ip��ַ
	 * @param filename
	 *            �ļ�����������·����
	 */
	public void sendQuest(String Type, String usernameStr, String ip,
			String filename) {
		String sendMsg;
		sendMsg = Type + ":::" + comondata.getId() + ":::" + usernameStr
				+ ":::" + ip + ":::" + filename;
		 System.out.println("request == " + sendMsg);
		this.getServerThread().sendData(sendMsg);

	}

}