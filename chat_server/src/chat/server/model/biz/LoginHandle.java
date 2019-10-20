package chat.server.model.biz;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import model.entity.userimformation;
import chat.server.common.CommonData;
import chat.server.controller.MessageTypeEnum;
import chat.server.controller.ServerThread;

/**
 * �ͻ���¼������ ˵�������û���¼�����߽��д���
 * 
 * @author ��С��
 * 
 */
public class LoginHandle {
	private static final LoginHandle loginHandle = new LoginHandle();

	public LoginHandle() {

	}

	/**
	 * ���ؿͻ���¼������ʵ��
	 * 
	 * @return
	 */
	public static LoginHandle getLoginHandle() {
		return LoginHandle.loginHandle;
	}

	/**
	 * ����ͻ���¼
	 * 
	 * @param loginStr_
	 * @param serverThread_
	 */
	public void clientLoginHandle(String loginStr_, ServerThread serverThread_) {
		// msgStr ��ʽΪname:::password
		String[] msgInfo = loginStr_.split(":::");
		String clientId = msgInfo[0];
		String clientPassword = msgInfo[1];
		System.out.println(msgInfo[1]);

		if (DataBaseHandleBiz.getDataBaseHandleBiz().IsLogin(
				Integer.valueOf(clientId))) {
			serverThread_.sendData(MessageTypeEnum.LoginReturn.toString()
					+ ":::"
					+ 0//����ͬ���û�
					+ ":"
					+ DataBaseHandleBiz.getDataBaseHandleBiz().getName_F_Id(
							Integer.valueOf(clientId)));
			return;
		}

		if (!DataBaseHandleBiz.getDataBaseHandleBiz().isId_E_Password(
				Integer.valueOf(clientId), clientPassword)) {
			serverThread_.sendData(MessageTypeEnum.LoginReturn.toString()
					+ ":::"
					+ -1// ��¼����
					+ ":"
					+ DataBaseHandleBiz.getDataBaseHandleBiz().getName_F_Id(
							Integer.valueOf(clientId)));
			return;
		}

		System.out.println("Logon successfully, initializing... ");

		// ��¼�ɹ� ��Ϣ��ʽ��msgType:::loginResult
		String sendStr = MessageTypeEnum.LoginReturn.toString()
				+ ":::"
				+ 1// ��¼�ɹ�
				+ ":"
				+ DataBaseHandleBiz.getDataBaseHandleBiz().getName_F_Id(
						Integer.valueOf(clientId));
		serverThread_.sendData(sendStr);

		// ��ӵ����������û���
		CommonData.getClientThreadMap().put(clientId, serverThread_);
		CommonData.getOnlineUserMap().put(
				clientId,
				new userimformation(String.valueOf(clientId), DataBaseHandleBiz
						.getDataBaseHandleBiz().getName_F_Id(
								Integer.valueOf(clientId))));

		DataBaseHandleBiz.getDataBaseHandleBiz().setLogin(
				Integer.valueOf(clientId), true);

		// ��Ϣ��ʽ��msgType:::userName:::isLogin
		String sendMsg = MessageTypeEnum.NewClientConnect.toString()
				+ ":::"
				+ clientId
				+ "\n"
				+ DataBaseHandleBiz.getDataBaseHandleBiz().getName_F_Id(
						Integer.valueOf(clientId)) + ":::" + "LOGIN";

		// �㲥��������
		serverThread_.broadcast(sendMsg);

		// �� OnlineUserMap ����ת�����ַ������з���
		String OnlineUserMapObjectStr = " ";
		try {
			// ���л����������������ByteArrayOutputStream��
			ByteArrayOutputStream baos = new ByteArrayOutputStream();

			// ���л�ʹ�õ������
			ObjectOutputStream oos = new ObjectOutputStream(baos);

			// ��д�����
			oos.writeObject(getOnlineFriendUserMap(Integer.valueOf(clientId)));
			byte[] buf = baos.toByteArray();

			// ת�����ַ���
			sun.misc.BASE64Encoder encoder = new sun.misc.BASE64Encoder();
			OnlineUserMapObjectStr = encoder.encode(buf);

			oos.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

		// �������������û���Ϣ�����û� ��Ϣ��ʽ��msgType:::objectStreamString
		String sendMsg_2 = MessageTypeEnum.AllOnlineUserDetailReturn.toString()
				+ ":::" + OnlineUserMapObjectStr;
		serverThread_.sendData(sendMsg_2);

		// �� OfflineMap ����ת�����ַ������з���
		String OfflineUserMapObjectStr = " ";
		try {
			// ���л����������������ByteArrayOutputStream��
			ByteArrayOutputStream baos = new ByteArrayOutputStream();

			// ���л�ʹ�õ������
			ObjectOutputStream oos = new ObjectOutputStream(baos);

			// ��д�����
			oos.writeObject(getOfflineFriendUserMap(Integer.valueOf(clientId)));
			byte[] buf = baos.toByteArray();

			// ת�����ַ���
			sun.misc.BASE64Encoder encoder = new sun.misc.BASE64Encoder();
			OfflineUserMapObjectStr = encoder.encode(buf);

			oos.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
		// �������������û���Ϣ�����û� ��Ϣ��ʽ��msgType:::objectStreamString
		String sendMsg_3 = MessageTypeEnum.AllOfflineUserDetailReturn
				.toString() + ":::" + OfflineUserMapObjectStr;
		serverThread_.sendData(sendMsg_3);

		System.out.println("finish initialization! user: \"" + clientId
				+ "\" is Listening... ");
	}

	public HashMap<String, userimformation> getOfflineFriendUserMap(int id) {
		int[] ids = DataBaseHandleBiz.getDataBaseHandleBiz().getFriend_F_Id(id);
		HashMap<String, userimformation> users = new HashMap<>();
		for (int i = 0; i < ids.length; i++) {
			if (!DataBaseHandleBiz.getDataBaseHandleBiz().IsLogin(ids[i])) {
				users.put(
						String.valueOf(ids[i]),
						new userimformation(String.valueOf(String
								.valueOf(ids[i])), DataBaseHandleBiz
								.getDataBaseHandleBiz().getName_F_Id(ids[i])));
			}
		}
		return users;
	}

	public HashMap<String, userimformation> getOnlineFriendUserMap(int id) {
		int[] ids = DataBaseHandleBiz.getDataBaseHandleBiz().getFriend_F_Id(id);
		HashMap<String, userimformation> users = new HashMap<>();
		for (int i = 0; i < ids.length; i++) {
			if (DataBaseHandleBiz.getDataBaseHandleBiz().IsLogin(ids[i])) {
				users.put(
						String.valueOf(ids[i]),
						new userimformation(String.valueOf(String
								.valueOf(ids[i])), DataBaseHandleBiz
								.getDataBaseHandleBiz().getName_F_Id(ids[i])));
			}
		}
		return users;
	}

	/**
	 * �����û�����
	 * 
	 * @param msgStr_
	 *            ��Ϣ��ʽ��userName
	 */
	public void ClientOffLineHandle(String clientId_, ServerThread serverThread_) {
		System.out.println("one client off-line");

		// �������û������Ƴ����û�
		CommonData.getOnlineUserMap().remove(clientId_);
		CommonData.getClientThreadMap().remove(clientId_);

		// ��Ϣ�ַ�
		String sendMsg = MessageTypeEnum.ClientOffLine.toString()
				+ ":::"
				+ clientId_
				+ "\n"
				+ DataBaseHandleBiz.getDataBaseHandleBiz().getName_F_Id(
						Integer.valueOf(clientId_));

		// �㲥�û�����
		serverThread_.broadcast(sendMsg);
	}
}
