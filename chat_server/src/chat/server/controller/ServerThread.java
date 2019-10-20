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
 * 服务线程类 功能：控制数据的基本收发
 * 
 * @author 熊小轲
 * 
 */
public class ServerThread extends Thread {
	private Socket socket = null;
	private DataInputStream dis = null;
	private DataOutputStream dos = null;
	private String separatorStr = ""; // 消息分隔符
	private boolean isListen = false;
	private String clientId = null; // 该线程服务的客户端用户名

	public ServerThread(Socket socket_) {
		try {
			this.socket = socket_;

			// 初始化消息定界符
			separatorStr = ":::";

			// 初始化输入输出流
			dis = new DataInputStream(socket.getInputStream());
			dos = new DataOutputStream(socket.getOutputStream());

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 向除拥有本实例以外的所有用户发送广播消息
	 */
	// public void broadcast(String msgStr) {
	// // 遍历在线用户表
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
	 * 向该客户端发送消息
	 * 
	 * @param socket_
	 * @param msgStr
	 */
	public void sendData(String msgStr_) {
		try {
			dos.writeUTF(msgStr_);//写到Socket的

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取本实例的socket对象
	 * 
	 * @return
	 */
	public Socket getSocket() {
		return this.socket;
	}

	/**
	 * 设置是否断续监听
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
			// 消息内容
			String msgStr = "";

			// 分隔符在字符串中的位置
			int separatorIndex = 0;

			// 是否接收消息
			isListen = true;

			while (isListen) {
				// 接收消息
				msgStr = dis.readUTF();

				// 确定分隔符在字符串中的位置
				separatorIndex = msgStr.indexOf(separatorStr);

				System.out.println("msg == " + msgStr);

				// 提取消息类型
				MessageTypeEnum msgType = MessageTypeEnum.valueOf(msgStr
						.substring(0, separatorIndex));

				switch (msgType) {

				/***************** 初始化客户信息 ******************/
				case Login: {
					// msgStr 格式为msgtype:::id:::password
					clientId = msgStr.substring(separatorIndex + 3)
							.split(":::")[0];

					// 交给biz层处理
					LoginHandle.getLoginHandle().clientLoginHandle(
							msgStr.substring(separatorIndex + 3), this);

					break;
				}

				/***************** 群聊消息 ******************/
				case GroupChat: {
					// 交给biz层处理
					ChatHandleBiz.getChatHandleBiz().groupChatHandle(
							msgStr.substring(separatorIndex + 3));

					break;
				}

				/***************** 一对一聊天消息 ******************/
				case SingleChat: {
					// 交给biz层处理
					ChatHandleBiz.getChatHandleBiz().singleChatHandle(
							msgStr.substring(separatorIndex + 3));

					break;
				}

				/***************** 文件接收 ******************/
				case FileSend: {
					FileSendHandleBiz.getFileSendHandleBiz().singleFileSend(
							msgStr.substring(separatorIndex + 3));
					break;
				}

				/***************** 文件接收准备 ******************/
				case FileReady: {
					FileSendHandleBiz.getFileSendHandleBiz().singleFileReady(
							msgStr.substring(separatorIndex + 3));
					break;
				}

				/***************** 新用户注册 ******************/
				case SignUp: {
					DataBaseHandleBiz.getDataBaseHandleBiz().signUp(this,
							msgStr.substring(separatorIndex + 3));
					break;
				}
				/***************** 好友添加 ******************/
				case AddFriend: {
					AddFriendBiz.getAddFriendBiz().addFriend(
							msgStr.substring(separatorIndex + 3));
					break;
				}
				/***************** 好友删除 ******************/
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
				// 处理用户下线
				LoginHandle.getLoginHandle()
						.ClientOffLineHandle(clientId, this);

				if (clientId != null) {
					DataBaseHandleBiz.getDataBaseHandleBiz().setLogin(
							Integer.valueOf(clientId), false);
				}

				// 关闭流和连接
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
