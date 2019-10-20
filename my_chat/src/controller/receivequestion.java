package controller;

import java.io.IOException;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.filechooser.FileNameExtensionFilter;

import comondata.comondata;

public class receivequestion {
	private static final receivequestion mainSocket = new receivequestion(); // 定义本类实例对象
	private static Socket socket; // 连接对像
	private static clientthread serverThread; // 服务线程

	private receivequestion() {
		try {
			// System.out.println(comondata.getServerIp());
			// System.out.println(comondata.getServerPort());

			// 初始化参数
			socket = new Socket(comondata.getServerIp().trim(),
					comondata.getServerPort());
			serverThread = new clientthread(socket);
			System.out.println("clientThread="+serverThread);
			// 连接服务器，开启监听
			this.getServerThread().start();

		} catch (ConnectException e) {
			System.err.println("未能找到服务器");
			// e.printStackTrace();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取连接类实例
	 * 
	 * @return 连接类实例  receivequestion mainSocket
	 */
	public static receivequestion getMainSocket() {
		return mainSocket;
	}

	/**
	 * 获取服务线程
	 * 
	 * @return 服务线程实例
	 */
	public clientthread getServerThread() {
		return serverThread;
	}

	/**
	 * 连接服务器，尝试登入
	 * 
	 * @param userName_
	 * @param sex_
	 */
	public void login(String userName_, String password_) {
		// sendMsg格式为msgType:::name:::password
		String sendMsg = MessageTypeEnum.Login.toString() + ":::" + userName_
				+ ":::" + password_;

		// 发送消息
		this.getServerThread().sendData(sendMsg);

	}
	
	/**
	 * 注册用户
	 * @param msg 注册用户字符串,signup(类型) :::(类型分隔符) name \r(元素分隔符) sex \r(元素分隔符) birth \r(元素分隔符) live 
	 */
	public void signUp(String msg){
		
		// 发送消息
		this.getServerThread().sendData(msg);
	}

	/**
	 * 发送文件请求
	 * 
	 * @param Type
	 *            请求类型
	 * @param usernameString
	 *            用户名
	 * @param ip
	 *            用户的ip地址
	 * @param filename
	 *            文件名（不包括路径）
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