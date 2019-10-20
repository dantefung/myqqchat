package model.biz;

import javax.swing.JFrame;

import view.ChatView;

import comondata.comondata;

public class ChatHandleBiz
{
    private static final ChatHandleBiz chatHandleBiz = new ChatHandleBiz();

    private ChatHandleBiz()
    {

    }

    /**
     * 返回聊天消息处理类实例
     * 
     * @return
     */
    public static ChatHandleBiz getChatHandleBiz()
    {
        return ChatHandleBiz.chatHandleBiz;
    }

    /**
     * 处理群聊消息
     * 
     * @param msgStr_
     *            格式：msgType:::fromUser:::content
     */
    public void groupChatHandle(String msgStr_)
    {
        String[] msgInfos = msgStr_.split(":::");
        String fromUser = msgInfos[0];
        String content = msgInfos[1];

        // 从基本信息类获取单例群聊天窗体，添加聊天内容
        comondata.getGroupChatFrame().setVisible(true);
        comondata.getGroupChatFrame().addChatContent(content);
    }

    /**
     * 处理单聊消息
     * 
     * @param msgStr_
     *            格式：fromUser:::content
     */
    public void singleChatHandle(String msgStr_)
    {
        String[] msgInfos = msgStr_.split(":::");
        String fromUser = msgInfos[0];
        String content = msgInfos[1];

        // 判断是否有与该用户聊过天，若无则添加一实例
        if (comondata.getChatFrameMap().get(fromUser) == null)
        {
            comondata.getChatFrameMap().put(fromUser, new ChatView(fromUser));
        }
        
        // 当窗体可见时
        if (comondata.getChatFrameMap().get(fromUser).isVisible())
        {
            // 窗体可见且最小化时，让其获取焦点并保持最小化
            if (comondata.getChatFrameMap().get(fromUser).getState() == JFrame.ICONIFIED) //JFrame.MAXIMIZED_BOTH
            {
            	comondata.getChatFrameMap().get(fromUser).setVisible(true);
            	comondata.getChatFrameMap().get(fromUser).setExtendedState(JFrame.ICONIFIED);
            }
            
            // 窗体不是最小化时不做操作
            else
            {
            }
        }
        
        // 当窗体不可见时
        else
        {
            // 若窗体不可见则让其可见
        	comondata.getChatFrameMap().get(fromUser).setVisible(true);
        }
        
        // 从基本信息类获取目标用户聊天窗体，添加聊天内容
        comondata.getChatFrameMap().get(fromUser).addChatContent(content);
    }
}
