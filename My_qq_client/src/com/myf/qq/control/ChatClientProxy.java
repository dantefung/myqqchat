package com.myf.qq.control;

import com.myf.qq.domain.Message;
import com.myf.qq.service.impl.UserServiceImpl;
import com.myf.qq.sys.factory.BeanFactory;
import com.myf.qq.util.DataUtil;
import com.myf.qq.util.MessageTypeEnum;

/**
 * ChatClient的代理对象。
 * 代理，关注方法的调用。扩展，在方法执行前后添加额外的功能代码。
 * @author myf
 *
 */
public class ChatClientProxy
{
	private static boolean isFirst = true;
	// 交给外部容器创建并注入
	private static ChatClient chatClient;
	// 提供注入的入口
	public void setChatClient(ChatClient chatClient)
    {
	    this.chatClient = chatClient;
    }
	
	// 给外部容器使用的无参数构造方法
	public ChatClientProxy()
	{
		chatClient = (ChatClient)BeanFactory.getBean("chatClient");
		chatClient.start();
		System.out.println("QQ客户端线程启动完毕... ...");
	}

	// 静态工厂
	public static ChatClientProxy getInstance()
	{
		return (ChatClientProxy)BeanFactory.getBean("chatClientProxy");
	}
	
	/**
	 * 给服务器端发送请求
	 * @param type 发送的请求类型
	 * @param msg  发送的消息（数据）
	 */
	public void sendRequest(MessageTypeEnum type, String msg)
	{
		System.out.println("request == " + msg);
//		System.out.println("chatClient="+chatClient);
		chatClient.sendRequest(DataUtil.genMessage(type, msg));
	}
	
	public void sendRequest(Message message)
	{
		 System.out.println("request == " + message.getMsg());
		 chatClient.sendRequest(message);
	}
	
	
	
	/**
	 * 获取聊天客户端对象
	 * @return chatClient 
	 */
	public static ChatClient getChatClient()
	{
		return chatClient;
	}
	
	
}
