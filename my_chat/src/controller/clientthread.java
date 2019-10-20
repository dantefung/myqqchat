package controller;

import java.io.DataInputStream;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;

import javax.swing.JOptionPane;

import comondata.comondata;

import model.biz.ChatHandleBiz;
import model.biz.LoginHandleBiz;
import model.biz.SignUpHandleBiz;

public class clientthread extends Thread {
	private Socket socket = null;
	private static DataOutputStream dos = null;
	private static DataInputStream dis = null;
	private String separatorStr = ""; // ��Ϣ�ָ���
	boolean isListen = false;

	public clientthread(Socket socket_) {
		try {
			separatorStr = ":::";
			this.socket = socket_;

			// ��ʼ�����������
			dos = new DataOutputStream(socket.getOutputStream());
			dis = new DataInputStream(socket.getInputStream());

		} catch (IOException e) {
			// System.out.println("e.printStackTrace");
			e.printStackTrace();
		}
	}

	/**
	 * ������Ϣ
	 * 
	 * @param msgStr
	 *            ��Ϣ��ʽ������
	 */
	public void sendData(String msgStr) {
		try {
			System.out.println("send ---> " + msgStr);
			dos.writeUTF(msgStr);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

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
				System.out.println("msgStr == " + msgStr);

				// ȷ���ָ������ַ����е�λ��
				separatorIndex = msgStr.indexOf(separatorStr);

				// ��ȡ��Ϣ����
				MessageTypeEnum msgType = MessageTypeEnum.valueOf(msgStr
						.substring(0, separatorIndex));

				switch (msgType) {

				/***************** ���� Login ��� ******************/
				case LoginReturn: {
					// ����biz����д���
					model.biz.LoginHandleBiz.getLoginHandleBiz().loginHandle(
							msgStr.substring(separatorIndex + 3));

					break;
				}

				/***************** ���� �������������û�������Ϣ ******************/
				case AllOnlineUserDetailReturn: {
					// ����biz����д���
					LoginHandleBiz.getLoginHandleBiz()
							.onlineUserDetailReturnHandle(
									msgStr.substring(separatorIndex + 3));
					// //System.out.println("GET!!!!!!!!!!");
					// //System.out.print("LEHGTH IS ==");
					// //System.out.println(msgStr.substring(separatorIndex +
					// 3).length());
					// //System.out.println(msgStr.substring(separatorIndex +
					// 3));
					break;
				}

				/***************** ���� �������������û�������Ϣ ******************/
				case AllOfflineUserDetailReturn: {
					// ����biz����д���
					LoginHandleBiz.getLoginHandleBiz()
							.offlineUserDetailReturnHandle(
									msgStr.substring(separatorIndex + 3));
					// //System.out.println("GET!!!!!!!!!!");
					// //System.out.print("LEHGTH IS ==");
					// //System.out.println(msgStr.substring(separatorIndex +
					// 3).length());
					// //System.out.println(msgStr.substring(separatorIndex +
					// 3));
					break;
				}

				/***************** ���� �����û����� ******************/
				case NewClientConnect: {
					// ����biz����д���
					LoginHandleBiz.getLoginHandleBiz().newClientConnectHandle(
							msgStr.substring(separatorIndex + 3));

					break;
				}

				/***************** ���� �û����� ******************/
				case ClientOffLine: {
					// ����biz����д���
					LoginHandleBiz.getLoginHandleBiz().ClientOffLineHandle(
							msgStr.substring(separatorIndex + 3));

					break;
				}

				/***************** Ⱥ����Ϣ ******************/
				case GroupChat: {
					// ����biz����д���
					ChatHandleBiz.getChatHandleBiz().groupChatHandle(
							msgStr.substring(separatorIndex + 3));

					break;
				}

				/***************** һ��һ������Ϣ ******************/
				case SingleChat: {
					// ����biz����д���
					ChatHandleBiz.getChatHandleBiz().singleChatHandle(
							msgStr.substring(separatorIndex + 3));

					break;
				}
				/***************** �ļ��������� ******************/
				case FileSend: {
					// �����ļ�
					fileSend.getFileSend().ready(
							msgStr.substring(separatorIndex + 3));
					// System.out.println("fileSend in client");

					break;
				}
				/***************** �ļ����� ******************/
				case FileReady: {
					// �ļ�����
					fileSend.getFileSend().sendFile(
							msgStr.substring(separatorIndex + 3));
					// System.out.println("FileReady in client");

					break;
				}
				/***************** ���û�ע�� ******************/
				case SignUp: {
					SignUpHandleBiz.getSignUpHandleBiz().signUp(
							Integer.valueOf(msgStr
									.substring(separatorIndex + 3)));
					break;
				}

				case AddFriendResult: {
					JOptionPane.showMessageDialog(null,
							msgStr.substring(separatorIndex + 3));
					break;
				}
				case RemoveFriend: {
					comondata.getMainFrame().removeUserItem(
							msgStr.substring(separatorIndex + 3));
					break;
				}
				default:
					break;
				}

			}

		} catch (SocketException e) {
			System.err.println("��������Ͽ����ӣ���");
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}