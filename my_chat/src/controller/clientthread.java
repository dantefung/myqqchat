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
	private String separatorStr = ""; // 消息分隔符
	boolean isListen = false;

	public clientthread(Socket socket_) {
		try {
			separatorStr = ":::";
			this.socket = socket_;

			// 初始化输入输出流
			dos = new DataOutputStream(socket.getOutputStream());
			dis = new DataInputStream(socket.getInputStream());

		} catch (IOException e) {
			// System.out.println("e.printStackTrace");
			e.printStackTrace();
		}
	}

	/**
	 * 发送消息
	 * 
	 * @param msgStr
	 *            消息格式化内容
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
			// 消息内容
			String msgStr = "";

			// 分隔符在字符串中的位置
			int separatorIndex = 0;

			// 是否接收消息
			isListen = true;

			while (isListen) {
				// 接收消息
				msgStr = dis.readUTF();
				System.out.println("msgStr == " + msgStr);

				// 确定分隔符在字符串中的位置
				separatorIndex = msgStr.indexOf(separatorStr);

				// 提取消息类型
				MessageTypeEnum msgType = MessageTypeEnum.valueOf(msgStr
						.substring(0, separatorIndex));

				switch (msgType) {

				/***************** 处理 Login 结果 ******************/
				case LoginReturn: {
					// 交给biz层进行处理
					model.biz.LoginHandleBiz.getLoginHandleBiz().loginHandle(
							msgStr.substring(separatorIndex + 3));

					break;
				}

				/***************** 处理 返回所有在线用户基本信息 ******************/
				case AllOnlineUserDetailReturn: {
					// 交给biz层进行处理
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

				/***************** 处理 返回所有离线用户基本信息 ******************/
				case AllOfflineUserDetailReturn: {
					// 交给biz层进行处理
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

				/***************** 处理 有新用户上线 ******************/
				case NewClientConnect: {
					// 交给biz层进行处理
					LoginHandleBiz.getLoginHandleBiz().newClientConnectHandle(
							msgStr.substring(separatorIndex + 3));

					break;
				}

				/***************** 处理 用户下线 ******************/
				case ClientOffLine: {
					// 交给biz层进行处理
					LoginHandleBiz.getLoginHandleBiz().ClientOffLineHandle(
							msgStr.substring(separatorIndex + 3));

					break;
				}

				/***************** 群聊消息 ******************/
				case GroupChat: {
					// 交给biz层进行处理
					ChatHandleBiz.getChatHandleBiz().groupChatHandle(
							msgStr.substring(separatorIndex + 3));

					break;
				}

				/***************** 一对一聊天消息 ******************/
				case SingleChat: {
					// 交给biz层进行处理
					ChatHandleBiz.getChatHandleBiz().singleChatHandle(
							msgStr.substring(separatorIndex + 3));

					break;
				}
				/***************** 文件接收请求 ******************/
				case FileSend: {
					// 接收文件
					fileSend.getFileSend().ready(
							msgStr.substring(separatorIndex + 3));
					// System.out.println("fileSend in client");

					break;
				}
				/***************** 文件发送 ******************/
				case FileReady: {
					// 文件发送
					fileSend.getFileSend().sendFile(
							msgStr.substring(separatorIndex + 3));
					// System.out.println("FileReady in client");

					break;
				}
				/***************** 新用户注册 ******************/
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
			System.err.println("与服务器断开连接！！");
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}