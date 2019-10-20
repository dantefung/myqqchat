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
     * ����������Ϣ������ʵ��
     * 
     * @return
     */
    public static ChatHandleBiz getChatHandleBiz()
    {
        return ChatHandleBiz.chatHandleBiz;
    }

    /**
     * ����Ⱥ����Ϣ
     * 
     * @param msgStr_
     *            ��ʽ��msgType:::fromUser:::content
     */
    public void groupChatHandle(String msgStr_)
    {
        String[] msgInfos = msgStr_.split(":::");
        String fromUser = msgInfos[0];
        String content = msgInfos[1];

        // �ӻ�����Ϣ���ȡ����Ⱥ���촰�壬�����������
        comondata.getGroupChatFrame().setVisible(true);
        comondata.getGroupChatFrame().addChatContent(content);
    }

    /**
     * ��������Ϣ
     * 
     * @param msgStr_
     *            ��ʽ��fromUser:::content
     */
    public void singleChatHandle(String msgStr_)
    {
        String[] msgInfos = msgStr_.split(":::");
        String fromUser = msgInfos[0];
        String content = msgInfos[1];

        // �ж��Ƿ�������û��Ĺ��죬���������һʵ��
        if (comondata.getChatFrameMap().get(fromUser) == null)
        {
            comondata.getChatFrameMap().put(fromUser, new ChatView(fromUser));
        }
        
        // ������ɼ�ʱ
        if (comondata.getChatFrameMap().get(fromUser).isVisible())
        {
            // ����ɼ�����С��ʱ�������ȡ���㲢������С��
            if (comondata.getChatFrameMap().get(fromUser).getState() == JFrame.ICONIFIED) //JFrame.MAXIMIZED_BOTH
            {
            	comondata.getChatFrameMap().get(fromUser).setVisible(true);
            	comondata.getChatFrameMap().get(fromUser).setExtendedState(JFrame.ICONIFIED);
            }
            
            // ���岻����С��ʱ��������
            else
            {
            }
        }
        
        // �����岻�ɼ�ʱ
        else
        {
            // �����岻�ɼ�������ɼ�
        	comondata.getChatFrameMap().get(fromUser).setVisible(true);
        }
        
        // �ӻ�����Ϣ���ȡĿ���û����촰�壬�����������
        comondata.getChatFrameMap().get(fromUser).addChatContent(content);
    }
}
