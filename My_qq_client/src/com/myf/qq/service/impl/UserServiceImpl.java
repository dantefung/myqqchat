package com.myf.qq.service.impl;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.HashMap;

import javax.swing.JOptionPane;

import QQ.WEB_INF.classes.com.myf.qq.domain.User;

import com.myf.qq.control.ChatClientProxy;
import com.myf.qq.service.UserService;
import com.myf.qq.util.CommonData;
import com.myf.qq.util.MessageTypeEnum;
import com.myf.qq.view.LoginUI;
import com.myf.qq.view.MainUI;

public class UserServiceImpl implements UserService
{
	// 单例模式：饿汉式
	private static final UserService userService = new UserServiceImpl(); // 定义本类实例对象
	
	public void addFriend(int mid, int fid) {
		ChatClientProxy
		.getInstance()
		.sendRequest(MessageTypeEnum.AddFriend,
				MessageTypeEnum.AddFriend.toString() + ":::"
						+ String.valueOf(mid) + ":"
						+ String.valueOf(fid));
				
	}
	
	/**
	 * 返回登录结果处理类实例
	 * 
	 * @return
	 */
	public static UserService getLoginHandleBiz() {
		return userService;
	}

	/**
	 * 处理登录结果
	 * 
	 * @param resultStr
	 */
	public void loginHandle(String resultStr) {
		// resultStr 格式：result
		int resultValue = Integer.parseInt(resultStr.trim().split(":")[0]);
		CommonData.setName(resultStr.trim().split(":")[1]);

		// 登录成功
		if (resultValue == 1) {
			// System.out.println("login successfully! \n I am " +
			// CommonData.getName());

			// 创建主窗体实例
			MainUI mainFrame = new MainUI();
			mainFrame.setVisible(true);

			// 保存全局信息中的主窗体
			CommonData.setMainFrame(mainFrame);
		}

		// 存在同名用户
		else if (resultValue == 0) {
			JOptionPane.showMessageDialog(null, "该用户已经登录！", "information",
					JOptionPane.INFORMATION_MESSAGE);
			new LoginUI().setVisible(true);
		}

		// 登录出错
		else if (resultValue == -1) {
			JOptionPane.showMessageDialog(null, "密码错误", "erro",
					JOptionPane.ERROR_MESSAGE);
			new LoginUI().setVisible(true);
		}
	}

	/**
	 * 处理登录后返回的在线用户基本信息表
	 * 
	 * @param msgStr_
	 *            格式：objectString
	 */
	@SuppressWarnings("unchecked")
	public void onlineUserDetailReturnHandle(String msgStr_) {
		byte[] buf;// 缓冲区

		sun.misc.BASE64Decoder decoder = new sun.misc.BASE64Decoder();
		try {
			// 解码字符串,转换成字节数组
			buf = decoder.decodeBuffer(msgStr_);

			// 字节数组转换成流
			ObjectInputStream ois = new ObjectInputStream(
					new ByteArrayInputStream(buf));

			// 读取对象
			HashMap<String, User> UserImformaintionMap = (HashMap<String, User>) ois
					.readObject();

			// System.out.println("UserInformationMap");

			// 同步本地信息
			CommonData.setUserImformationMap(UserImformaintionMap);

			// 初始化在线用户
			CommonData.getMainFrame().initUserList(UserImformaintionMap);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 处理登录后返回的离线用户基本信息表
	 * 
	 * @param msgStr_
	 *            格式：objectString
	 */
	@SuppressWarnings("unchecked")
	public void offlineUserDetailReturnHandle(String msgStr_) {
		byte[] buf;// 缓冲区

		sun.misc.BASE64Decoder decoder = new sun.misc.BASE64Decoder();
		try {
			// 解码字符串,转换成字节数组
			buf = decoder.decodeBuffer(msgStr_);

			// 字节数组转换成流
			ObjectInputStream ois = new ObjectInputStream(
					new ByteArrayInputStream(buf));

			// 读取对象
			HashMap<String, User> UserImformaintionMap = (HashMap<String, User>) ois.readObject();

			// System.out.println("UserInformationMap");

			// 同步本地信息
			CommonData.setUserImformationMap(UserImformaintionMap);

			// 初始化在线用户
			CommonData.getMainFrame().initOffUserList(UserImformaintionMap);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 处理有新用户上线
	 * 
	 * @param msgStr_
	 *            消息格式：userName:::Sex
	 */
	public void newClientConnectHandle(String msgStr_) {
		String[] msgInfos = msgStr_.split(":::");
		String userName = msgInfos[0];
		String isLogin = msgInfos[1];

		System.out.println("UserServiceImpl.newClientConnectHandle():\t userName=" + userName + "\tisLogin=" + isLogin);
		
		// 添加到主界面中
		if (isLogin.equals("LOGIN")) {
			CommonData.getMainFrame().addOnlineUserItem(userName);
		} else if (isLogin.equals("LOGOUT")) {
			CommonData.getMainFrame().addOfflineUserItem(userName);
		}
	}

	/**
	 * 处理用户下线
	 * 
	 * @param msgStr_
	 *            消息格式：userName
	 */
	public void ClientOffLineHandle(String msgStr_) {
		String[] msgInfos = msgStr_.split(":::");
		String userName = msgInfos[0];

		System.out.println("test clientofflinehandle " + userName);
		CommonData.getMainFrame().addOfflineUserItem(userName);
	}
	
}
