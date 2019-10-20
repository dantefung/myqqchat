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
 * 客户登录处理类 说明：对用户登录和下线进行处理
 * 
 * @author 熊小轲
 * 
 */
public class LoginHandle {
	private static final LoginHandle loginHandle = new LoginHandle();

	public LoginHandle() {

	}

	/**
	 * 返回客户登录处理类实例
	 * 
	 * @return
	 */
	public static LoginHandle getLoginHandle() {
		return LoginHandle.loginHandle;
	}

	/**
	 * 处理客户登录
	 * 
	 * @param loginStr_
	 * @param serverThread_
	 */
	public void clientLoginHandle(String loginStr_, ServerThread serverThread_) {
		// msgStr 格式为name:::password
		String[] msgInfo = loginStr_.split(":::");
		String clientId = msgInfo[0];
		String clientPassword = msgInfo[1];
		System.out.println(msgInfo[1]);

		if (DataBaseHandleBiz.getDataBaseHandleBiz().IsLogin(
				Integer.valueOf(clientId))) {
			serverThread_.sendData(MessageTypeEnum.LoginReturn.toString()
					+ ":::"
					+ 0//存在同名用户
					+ ":"
					+ DataBaseHandleBiz.getDataBaseHandleBiz().getName_F_Id(
							Integer.valueOf(clientId)));
			return;
		}

		if (!DataBaseHandleBiz.getDataBaseHandleBiz().isId_E_Password(
				Integer.valueOf(clientId), clientPassword)) {
			serverThread_.sendData(MessageTypeEnum.LoginReturn.toString()
					+ ":::"
					+ -1// 登录出错
					+ ":"
					+ DataBaseHandleBiz.getDataBaseHandleBiz().getName_F_Id(
							Integer.valueOf(clientId)));
			return;
		}

		System.out.println("Logon successfully, initializing... ");

		// 登录成功 消息格式：msgType:::loginResult
		String sendStr = MessageTypeEnum.LoginReturn.toString()
				+ ":::"
				+ 1// 登录成功
				+ ":"
				+ DataBaseHandleBiz.getDataBaseHandleBiz().getName_F_Id(
						Integer.valueOf(clientId));
		serverThread_.sendData(sendStr);

		// 添加到所有在线用户表
		CommonData.getClientThreadMap().put(clientId, serverThread_);
		CommonData.getOnlineUserMap().put(
				clientId,
				new userimformation(String.valueOf(clientId), DataBaseHandleBiz
						.getDataBaseHandleBiz().getName_F_Id(
								Integer.valueOf(clientId))));

		DataBaseHandleBiz.getDataBaseHandleBiz().setLogin(
				Integer.valueOf(clientId), true);

		// 消息格式：msgType:::userName:::isLogin
		String sendMsg = MessageTypeEnum.NewClientConnect.toString()
				+ ":::"
				+ clientId
				+ "\n"
				+ DataBaseHandleBiz.getDataBaseHandleBiz().getName_F_Id(
						Integer.valueOf(clientId)) + ":::" + "LOGIN";

		// 广播好友上线
		serverThread_.broadcast(sendMsg);

		// 将 OnlineUserMap 对象转化成字符串进行发送
		String OnlineUserMapObjectStr = " ";
		try {
			// 序列化后的数据流保存在ByteArrayOutputStream中
			ByteArrayOutputStream baos = new ByteArrayOutputStream();

			// 序列化使用的输出流
			ObjectOutputStream oos = new ObjectOutputStream(baos);

			// 将写入对象
			oos.writeObject(getOnlineFriendUserMap(Integer.valueOf(clientId)));
			byte[] buf = baos.toByteArray();

			// 转换成字符串
			sun.misc.BASE64Encoder encoder = new sun.misc.BASE64Encoder();
			OnlineUserMapObjectStr = encoder.encode(buf);

			oos.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

		// 发送所有在线用户信息给该用户 消息格式：msgType:::objectStreamString
		String sendMsg_2 = MessageTypeEnum.AllOnlineUserDetailReturn.toString()
				+ ":::" + OnlineUserMapObjectStr;
		serverThread_.sendData(sendMsg_2);

		// 将 OfflineMap 对象转化成字符串进行发送
		String OfflineUserMapObjectStr = " ";
		try {
			// 序列化后的数据流保存在ByteArrayOutputStream中
			ByteArrayOutputStream baos = new ByteArrayOutputStream();

			// 序列化使用的输出流
			ObjectOutputStream oos = new ObjectOutputStream(baos);

			// 将写入对象
			oos.writeObject(getOfflineFriendUserMap(Integer.valueOf(clientId)));
			byte[] buf = baos.toByteArray();

			// 转换成字符串
			sun.misc.BASE64Encoder encoder = new sun.misc.BASE64Encoder();
			OfflineUserMapObjectStr = encoder.encode(buf);

			oos.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
		// 发送所有在线用户信息给该用户 消息格式：msgType:::objectStreamString
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
	 * 处理用户下线
	 * 
	 * @param msgStr_
	 *            消息格式：userName
	 */
	public void ClientOffLineHandle(String clientId_, ServerThread serverThread_) {
		System.out.println("one client off-line");

		// 在在线用户表中移除该用户
		CommonData.getOnlineUserMap().remove(clientId_);
		CommonData.getClientThreadMap().remove(clientId_);

		// 消息字符
		String sendMsg = MessageTypeEnum.ClientOffLine.toString()
				+ ":::"
				+ clientId_
				+ "\n"
				+ DataBaseHandleBiz.getDataBaseHandleBiz().getName_F_Id(
						Integer.valueOf(clientId_));

		// 广播用户下线
		serverThread_.broadcast(sendMsg);
	}
}
