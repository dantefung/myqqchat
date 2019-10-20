package chat.server.common;

import java.util.HashMap;

import model.entity.userimformation;

import chat.server.controller.ServerThread;

/**
 * ˵����������Ϣ��
 * ���ܣ������ṩ���õ���Ϣ�Ļ�ȡ������
 * 
 * @author 
 * 
 */
public class CommonData
{
    private static HashMap<String, ServerThread>     clientThreadMap; // �������пͻ��˵������߳���
    private static HashMap<String, userimformation> onlineUserMap;  // ���������û��Ļ�����Ϣ

    private static int                               listenPort;     // ���ؼ����˿�

    // ��̬�����
    static
    {
        clientThreadMap = new HashMap<>();
        onlineUserMap = new HashMap<>();
        
//        listenPort = 6688;
          listenPort = 9090;
    }

    /**
     * ���пͻ��˵������߳���
     * 
     * @return
     */
    public static HashMap<String, ServerThread> getClientThreadMap()
    {
        return CommonData.clientThreadMap;
    }

    public static void setServerIp(HashMap<String, ServerThread> clientThreadMap_)
    {
        CommonData.clientThreadMap = clientThreadMap_;
    }

    /**
     * ���������û�������Ϣ��
     * 
     * @return
     */
    public static HashMap<String, userimformation> getOnlineUserMap()
    {
        return CommonData.onlineUserMap;
    }

    public static void setOnlineUserMap(HashMap<String, userimformation> onlineUserMap_)
    {
        CommonData.onlineUserMap = onlineUserMap_;
    }

    /**
     * ���ؼ����˿�
     * 
     * @return
     */
    public static int getListenPort()
    {
        return CommonData.listenPort;
    }

    public static void setListenPort(int listenPort_)
    {
        CommonData.listenPort = listenPort_;
    }
   
}
