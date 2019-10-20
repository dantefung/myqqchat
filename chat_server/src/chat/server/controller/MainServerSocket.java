package chat.server.controller;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import chat.server.common.CommonData;

/**
 * 
 * @author 
 */
public class MainServerSocket
{
    private static final MainServerSocket mainSocket = new MainServerSocket();
    
    // 监听Socket
    private static ServerSocket serverSocket;
    
    // 客户端Socket
    private static Socket clientSocket;
    
    // 是否继续监听
    private boolean isListen = true; 
    
    private MainServerSocket()
    {
        try
        {
            serverSocket = new ServerSocket(CommonData.getListenPort());//创建一个监控端口的服务器套接字。
            
        } catch(IOException e)
        {
            e.printStackTrace();
        }
    }
    
    /**
     * 获取mainSocket单例
     * @return
     */
    public static MainServerSocket getMainSocket()
    {
        return MainServerSocket.mainSocket;
    }
    
    /**
     * 开启服务器监听，开始接收客户连接
     */
    public void beginListen()
    {
        try
        {   //多用户服务器
        	/*
        	 * 背景：
        	 * 通常，服务器总是不间断地运行在服务器计算机上，来自整个
        	 * 因特网的用户希望同时使用服务器。拒绝多客户端连接
        	 * 将使得某个用户可能会因长时间地连接服务而独占服务。
        	 * 其实，我们可以运用线程的魔力把这个问题处理得更好。
        	 * 
        	 * 设计思想：
        	 * 每当程序建立一个新的套接字连接，也就是说当成功地调用accept的时候
        	 * 将创建一个新的线程来处理服务器和该客户端之间的连接，而主程序将立即返回
        	 * 并等待下一个连接。
        	 * 为了实现这个机制，服务器应该具有类似一下代码的循环操作。
        	 *
        	 */
            while(isListen)
            {
                System.out.println("listening...");
                
                // 阻塞（即，空闲）监听,等待连接，当前线程直到建立连接为止。
                //该方法返回一个套接字对象，程序可以通过这个对象与连接中的客户端进行通信。
                clientSocket = serverSocket.accept();
                
                System.out.println("one client connect");
                
                // 收到客户端后启动一条新线程
                new ServerThread(clientSocket).start();
            }
            
        } 
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }
    
}
