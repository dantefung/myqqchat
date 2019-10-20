package com.myf.qq.control;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;

import javax.swing.JOptionPane;

import com.myf.qq.domain.Message;
import com.myf.qq.service.UserService;
import com.myf.qq.service.impl.ChatHandleBiz;
import com.myf.qq.util.CommonData;
import com.myf.qq.util.FileSendHelper;
import com.myf.qq.util.MessageTypeEnum;

/**
 *             ,.
 *                         (_|,.
 *                        ,' /, )_______   _
 *                     __j o``-'        `.'-)'
 *                    (")                 \'
 *                     `-j                |
 *                       `-._(           /
 *                          |_\  |--^.  /
 *                         /_]'|_| /_)_/
 *                            /_]'  /_]'
 * 
 * 
 * 
 * QQ Client
 * 客户端总控制器
 * 
 * 约定：
 *    数据传输的格式     消息类型:::登录账号:::登录密码
 * @author myf
 *
 */
public class ChatClient extends Thread
{
	/** QQ 客户端通讯插座  **/
	private Socket socket;
	
	/** socket输出流的装饰流 :可读写基本类型数据**/
	private DataOutputStream dos;
	
	/** socket输入流的装饰流:可读写基本类型数据**/
	private DataInputStream dis;
	
	/** 消息分割符 **/
	private String delimeter = ":::";
	
	/** 是否不断监听服务器的信息 **/
	private boolean isIisten = false;
	
	/** 分隔符在字符串中的位置 **/
	private int delimeterIndex = 0;
	
	/** 消息javabean **/
	private Message message;
	
	/** 业务逻辑层的实例 **/
	private UserService userService;
	// 提供注入的接口
	public void setUserService(UserService userService)
    {
	    this.userService = userService;
    }
	public UserService getUserService()
    {
	    return userService;
    }
	
	/* 使用重叠构造器 */
	public ChatClient() throws Exception
    {
		// 1、建立TCP通讯插座
	    this(new Socket(CommonData.getServerIp(), CommonData.getServerPort()));
    }
	
	/* 私有化，防止API调用者使用 */
	private ChatClient(Socket socket_) throws Exception
	{
		this.socket = socket_;
		// 2、获取对应的流对象，因为TCP是基于IO流进行数据传输的
		dos = new DataOutputStream(socket.getOutputStream());
		dis = new DataInputStream(socket.getInputStream());
	}
	
	/**
	 * 3、准备发送给服务器的数据，抽取成一个方法：专门维护数据传输
	 * 用于发送数据给服务器的控制器
	 * @param command
	 */
	public void sendRequest(Message message)
	{
		switch (message.getType())
		{
		
		   /***************** 发送 Login 请求 ******************/
			case Login:
			{
				sendData(message);
				break;
			}
			
			/***************** 发送 返回所有在线用户基本信息 请求******************/
			case AllOnlineUserDetailReturn:
			{
				break;
			}
			
			/***************** 发送 返回所有离线用户基本信息 请求******************/
			case AllOfflineUserDetailReturn:
			{
				break;
			}
			
			/***************** 发送 有新用户上线 ******************/
			case NewClientConnect:
			{
				
				break;
			}
			
			/***************** 发送 用户下线 请求******************/
			case ClientOffLine:
			{
				
				break;
			}
			
			/***************** 群聊消息 ******************/
			case GroupChat:
			{
				sendData(message);
				break;
			}
			
			/***************** 一对一聊天消息 ******************/
			case SingleChat:
			{
				/*
				 * 消息格式：// 消息格式：msgType:::fromUser:::toUser:::formatContent
				 * 参数说明：
				 * msgType:消息类型
				 * fromUser:来自哪个用户（ID）
				 * toUser:发送至哪个用户（ID）
				 * formatContent:"<font color = green>" + comondata.getName()"  " + timeStr + " :</font><br>" + chatContent;
				 */
				System.out.println("message=" + message);
				sendData(message);
				break;
			}
			/***************** 文件接收请求 ******************/
			case FileSend:
			{
				
				break;
			}
			/***************** 文件发送 ******************/
			case FileReady:
			{
				/*
				 * 消息格式：Type + ":::" + comondata.getId() + ":::" + usernameStr ":::" + ip + ":::" + filename;
				 * 参数说明：
				 * type:请求类型
				 * usernameString:用户名
				 * ip：用户的ip地址
				 * filename:文件名（不包含路径）
				 */
				sendData(message);
				break;
			}
			/***************** 新用户注册 ******************/
			case AddFriendResult:
			{
				break;
			}
			case RemoveFriend:
			{
				break;
			}
			default:
				break;
		}
	}
	
	/**
	 * 4、接收服务器端的数据
	 */
	@Override
	public void run()
	{
		try
        {
			isIisten = true;
			
			while(isIisten)
			{
		        // 4、获取服务器反馈的数据
		        String msg = dis.readUTF();
		        System.out.println("接收到服务器返回的数据为： " + msg);
		        // 4.1、确定分隔符的位置
		        delimeterIndex = msg.indexOf(delimeter);
		        String type = msg.substring(0, delimeterIndex).trim();
		        System.out.println(type);
		        // 4.2、提取消息类型
		        MessageTypeEnum msgType = MessageTypeEnum.valueOf(type);
		        // 4.3、分发处理
		        switch (msgType) {

					/***************** 处理 Login 结果 ******************/
					case LoginReturn: {
						// 交给biz层进行处理
						userService.loginHandle(
								msg.substring(delimeterIndex + 3));

						break;
					}

					/***************** 处理 返回所有在线用户基本信息 ******************/
					case AllOnlineUserDetailReturn: {
						// 交给biz层进行处理
						userService.onlineUserDetailReturnHandle(
										msg.substring(delimeterIndex + 3));
						break;
					}

					/***************** 处理 返回所有离线用户基本信息 ******************/
					case AllOfflineUserDetailReturn: {
						// 交给biz层进行处理
						userService.offlineUserDetailReturnHandle(
										msg.substring(delimeterIndex + 3));
						break;
					}

					/***************** 处理 有新用户上线 ******************/
					case NewClientConnect: {
						// 交给biz层进行处理
						userService.newClientConnectHandle(
								msg.substring(delimeterIndex + 3));

						break;
					}

					/***************** 处理 用户下线 ******************/
					case ClientOffLine: {
						// 交给biz层进行处理
						userService.ClientOffLineHandle(
								msg.substring(delimeterIndex + 3));

						break;
					}

					/***************** 群聊消息 ******************/
					case GroupChat: {
						// 交给biz层进行处理
						ChatHandleBiz.getChatHandleBiz().groupChatHandle(
								msg.substring(delimeterIndex + 3));

						break;
					}

					/***************** 一对一聊天消息 ******************/
					case SingleChat: {
						// 交给biz层进行处理
						ChatHandleBiz.getChatHandleBiz().singleChatHandle(
								msg.substring(delimeterIndex + 3));

						break;
					}
					/***************** 文件接收请求 ******************/
					case FileSend: {
						// 接收文件
						FileSendHelper.getFileSendHelper().ready(
								msg.substring(delimeterIndex + 3));
						// System.out.println("FileSendHelper in client");

						break;
					}
					/***************** 文件发送 ******************/
					case FileReady: {
						// 文件发送
						FileSendHelper.getFileSendHelper().sendFile(
								msg.substring(delimeterIndex + 3));
						// System.out.println("FileReady in client");

						break;
					}
					case AddFriendResult: {
						JOptionPane.showMessageDialog(null,
								msg.substring(delimeterIndex + 3));
						break;
					}
					case RemoveFriend: {
						CommonData.getMainFrame().removeUserItem(
								msg.substring(delimeterIndex + 3));
						break;
					}
					default:
						break;
					}

				
				// 5、释放资源？不了。
			}
			
        } 
		catch (SocketException e) {
			System.err.println("与服务器断开连接！！");
			e.printStackTrace();
        }
        catch (Exception e)
        {
	        e.printStackTrace();
        }
		
	}
	
	/**
	 * 发送消息
	 * @param message 消息bean
	 */
	private void sendData(Message message)
    {
	    try
	    {
	    	System.out.println("send ---> " + message.getMsg());
	        dos.writeUTF(message.getMsg());
	    }
	    catch (IOException e)
	    {
	        e.printStackTrace();
	    }
    }
	
}
