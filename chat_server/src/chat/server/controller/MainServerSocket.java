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
    
    // ����Socket
    private static ServerSocket serverSocket;
    
    // �ͻ���Socket
    private static Socket clientSocket;
    
    // �Ƿ��������
    private boolean isListen = true; 
    
    private MainServerSocket()
    {
        try
        {
            serverSocket = new ServerSocket(CommonData.getListenPort());//����һ����ض˿ڵķ������׽��֡�
            
        } catch(IOException e)
        {
            e.printStackTrace();
        }
    }
    
    /**
     * ��ȡmainSocket����
     * @return
     */
    public static MainServerSocket getMainSocket()
    {
        return MainServerSocket.mainSocket;
    }
    
    /**
     * ������������������ʼ���տͻ�����
     */
    public void beginListen()
    {
        try
        {   //���û�������
        	/*
        	 * ������
        	 * ͨ�������������ǲ���ϵ������ڷ�����������ϣ���������
        	 * ���������û�ϣ��ͬʱʹ�÷��������ܾ���ͻ�������
        	 * ��ʹ��ĳ���û����ܻ���ʱ������ӷ������ռ����
        	 * ��ʵ�����ǿ��������̵߳�ħ����������⴦��ø��á�
        	 * 
        	 * ���˼�룺
        	 * ÿ��������һ���µ��׽������ӣ�Ҳ����˵���ɹ��ص���accept��ʱ��
        	 * ������һ���µ��߳�������������͸ÿͻ���֮������ӣ�����������������
        	 * ���ȴ���һ�����ӡ�
        	 * Ϊ��ʵ��������ƣ�������Ӧ�þ�������һ�´����ѭ��������
        	 *
        	 */
            while(isListen)
            {
                System.out.println("listening...");
                
                // �������������У�����,�ȴ����ӣ���ǰ�߳�ֱ����������Ϊֹ��
                //�÷�������һ���׽��ֶ��󣬳������ͨ����������������еĿͻ��˽���ͨ�š�
                clientSocket = serverSocket.accept();
                
                System.out.println("one client connect");
                
                // �յ��ͻ��˺�����һ�����߳�
                new ServerThread(clientSocket).start();
            }
            
        } 
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }
    
}
