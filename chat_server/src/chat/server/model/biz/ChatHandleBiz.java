package chat.server.model.biz;

import chat.server.common.CommonData;
import chat.server.controller.MessageTypeEnum;

/**
 * ˵����������Ϣ������
 * <p>
 * ���ܣ���������Ϣ���д�������Ϣ������ʾ����Ӧ�����촰����
 * 
 * @author ��С��
 * 
 */
public class ChatHandleBiz {
	private static final ChatHandleBiz chatHandleBiz = new ChatHandleBiz();

	private ChatHandleBiz() {

	}

	/**
	 * ����������Ϣ������ʵ��
	 * 
	 * @return
	 */
	public static ChatHandleBiz getChatHandleBiz() {
		return ChatHandleBiz.chatHandleBiz;
	}

	/**
	 * ����Ⱥ����Ϣ
	 * 
	 * @param msgStr_
	 */
	public void groupChatHandle(String msgStr_) {
		// msgStr ��ʽΪname:::content
		String[] msgInfo = msgStr_.split(":::");
		String fromUserName = msgInfo[0];
		String content = msgInfo[1];

		// ��Ϣ��ʽ��msgType:::fromUser:::content
		String sendMsg = MessageTypeEnum.GroupChat.toString()
				+ ":::"
				+ fromUserName
				+ "\n"
				+ DataBaseHandleBiz.getDataBaseHandleBiz().getName_F_Id(
						Integer.valueOf(fromUserName)) + ":::" + content;

		// System.out.println("group chat: " + fromUserName + " say: " + " : " +
		// content);

		// Ⱥ��
		CommonData.getClientThreadMap().get(fromUserName).broadcast(sendMsg);
	}

	/**
	 * ��������Ϣ
	 * 
	 * @param msgStr_
	 */
	public void singleChatHandle(String msgStr_) {
		// msgStr ��ʽΪ fromUser:::toUser:::content
		String[] msgInfo = msgStr_.split(":::");
		String fromUserId = msgInfo[0];
		String toUserId = msgInfo[1];
		String content = msgInfo[2];

		// ��Ϣ��ʽ��msgType:::fromUser:::content
		String sendMsg = MessageTypeEnum.SingleChat.toString()
				+ ":::"
				+ fromUserId
				+ "\n"
				+ DataBaseHandleBiz.getDataBaseHandleBiz().getName_F_Id(
						Integer.valueOf(fromUserId)) + ":::" + content;

		// System.out.println("single chat: " + fromUserName + " speak to " +
		// toUserName + " : " + content);

		// ��ָ���û�������Ϣ
		CommonData.getClientThreadMap().get(toUserId).sendData(sendMsg);
	}
}
