package chat.server.common;

import java.util.HashMap;

import model.entity.userimformation;

import chat.server.controller.ServerThread;

/**
 * 说明：基本信息类
 * 功能：用于提供常用的信息的获取和设置
 * 
 * @author 
 * 
 */
public class CommonData
{
    private static HashMap<String, ServerThread>     clientThreadMap; // 保存所有客户端的连接线程类
    private static HashMap<String, userimformation> onlineUserMap;  // 所有在线用户的基本信息

    private static int                               listenPort;     // 本地监听端口

    // 静态代码块
    static
    {
        clientThreadMap = new HashMap<>();
        onlineUserMap = new HashMap<>();
        
//        listenPort = 6688;
          listenPort = 9090;
    }

    /**
     * 所有客户端的连接线程类
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
     * 所有在线用户基本信息表
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
     * 本地监听端口
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
