package chat.server.controller;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.util.Iterator;
import java.util.Map.Entry;

import chat.server.common.CommonData;
import chat.server.controller.MessageTypeEnum;
import chat.server.model.biz.AddFriendBiz;
import chat.server.model.biz.ChatHandleBiz;
import chat.server.model.biz.DataBaseHandleBiz;
import chat.server.model.biz.FileSendHandleBiz;
import chat.server.model.biz.LoginHandle;
import chat.server.model.biz.RemoveFriendBiz;

/**
 * �����߳��� ���ܣ��������ݵĻ����շ�
 * 
 * @author ��С��
 * 
 */
public class ServerThread extends Thread {
	private Socket socket = null;
	private DataInputStream dis = null;
	private DataOutputStream dos = null;
	private String separatorStr = ""; // ��Ϣ�ָ���
	private boolean isListen = false;
	private String clientId = null; // ���̷߳���Ŀͻ����û���

	public ServerThread(Socket socket_) {
		try {
			this.socket = socket_;

			// ��ʼ����Ϣ�����
			separatorStr = ":::";

			// ��ʼ�����������
			dis = new DataInputStream(socket.getInputStream());
			dos = new DataOutputStream(socket.getOutputStream());

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * ���ӵ�б�ʵ������������û����͹㲥��Ϣ
	 */
	// public void broadcast(String msgStr) {
	// // ���������û���
	// Iterator<Entry<String, ServerThread>> iter = CommonData
	// .getClientThreadMap().entrySet().iterator();
	//
	// while (iter.hasNext()) {
	// Entry<String, ServerThread> entry = (Entry<String, ServerThread>) iter
	// .next();
	// ServerThread clientThread = (ServerThread) entry.getValue();
	// if (clientThread != this) {
	// clientThread.sendData(msgStr);
	// }
	// // String key = entry.getKey().toString();
	// }
	// }

	public void broadcast(String msgStr) {
		int[] ids = DataBaseHandleBiz.getDataBaseHandleBiz().getFriend_F_Id(
				Integer.valueOf(clientId));
		for (int i = 0; i < ids.length; i++) {
			// System.out.println("ids" + i + " -->" + +ids[i]);
			if (DataBaseHandleBiz.getDataBaseHandleBiz().IsLogin(ids[i])) {
				CommonData.getClientThreadMap().get(String.valueOf(ids[i]))
						.sendData(msgStr);
			}
		}
	}

	/**
	 * ��ÿͻ��˷�����Ϣ
	 * 
	 * @param socket_
	 * @param msgStr
	 */
	public void sendData(String msgStr_) {
		try {
			dos.writeUTF(msgStr_);//д��Socket��

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * ��ȡ��ʵ����socket����
	 * 
	 * @return
	 */
	public Socket getSocket() {
		return this.socket;
	}

	/**
	 * �����Ƿ��������
	 * 
	 * @param value
	 */
	public void setListen(boolean value) {
		this.isListen = value;
	}

	//////////////////////////////////////////////
	
	@Override
	public void run() {
		try {
			// ��Ϣ����
			String msgStr = "";

			// �ָ������ַ����е�λ��
			int separatorIndex = 0;

			// �Ƿ������Ϣ
			isListen = true;

			while (isListen) {
				// ������Ϣ
				msgStr = dis.readUTF();

				// ȷ���ָ������ַ����е�λ��
				separatorIndex = msgStr.indexOf(separatorStr);

				System.out.println("msg == " + msgStr);

				// ��ȡ��Ϣ����
				MessageTypeEnum msgType = MessageTypeEnum.valueOf(msgStr
						.substring(0, separatorIndex));

				switch (msgType) {

				/***************** ��ʼ���ͻ���Ϣ ******************/
				case Login: {
					// msgStr ��ʽΪmsgtype:::id:::password
					clientId = msgStr.substring(separatorIndex + 3)
							.split(":::")[0];

					// ����biz�㴦��
					LoginHandle.getLoginHandle().clientLoginHandle(
							msgStr.substring(separatorIndex + 3), this);

					break;
				}

				/***************** Ⱥ����Ϣ ******************/
				case GroupChat: {
					// ����biz�㴦��
					ChatHandleBiz.getChatHandleBiz().groupChatHandle(
							msgStr.substring(separatorIndex + 3));

					break;
				}

				/***************** һ��һ������Ϣ ******************/
				case SingleChat: {
					// ����biz�㴦��
					ChatHandleBiz.getChatHandleBiz().singleChatHandle(
							msgStr.substring(separatorIndex + 3));

					break;
				}

				/***************** �ļ����� ******************/
				case FileSend: {
					FileSendHandleBiz.getFileSendHandleBiz().singleFileSend(
							msgStr.substring(separatorIndex + 3));
					break;
				}

				/***************** �ļ�����׼�� ******************/
				case FileReady: {
					FileSendHandleBiz.getFileSendHandleBiz().singleFileReady(
							msgStr.substring(separatorIndex + 3));
					break;
				}

				/***************** ���û�ע�� ******************/
				case SignUp: {
					DataBaseHandleBiz.getDataBaseHandleBiz().signUp(this,
							msgStr.substring(separatorIndex + 3));
					break;
				}
				/***************** ������� ******************/
				case AddFriend: {
					AddFriendBiz.getAddFriendBiz().addFriend(
							msgStr.substring(separatorIndex + 3));
					break;
				}
				/***************** ����ɾ�� ******************/
				case RemoveFriend: {
					RemoveFriendBiz.getRemoveFriendBiz().removeFriend(
							msgStr.substring(separatorIndex + 3));
					break;
				}
				default:
					break;
				}
			}

		} catch (SocketException e) {
			try {
				// �����û�����
				LoginHandle.getLoginHandle()
						.ClientOffLineHandle(clientId, this);

				if (clientId != null) {
					DataBaseHandleBiz.getDataBaseHandleBiz().setLogin(
							Integer.valueOf(clientId), false);
				}

				// �ر���������
				dos.flush();
				dos.close();
				dis.close();
				socket.close();

			} catch (IOException e1) {
				e1.printStackTrace();
			}
			// e.printStackTrace();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
