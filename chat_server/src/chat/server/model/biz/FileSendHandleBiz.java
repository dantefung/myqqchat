package chat.server.model.biz;

import java.awt.TrayIcon.MessageType;

import chat.server.common.CommonData;
import chat.server.controller.MessageTypeEnum;

public class FileSendHandleBiz {
	private static FileSendHandleBiz fileSendHandleBiz = new FileSendHandleBiz();

	/**
	 * 构造函数
	 */
	public FileSendHandleBiz() {

	}

	/**
	 * 获取FileSendHandleBiz类单例
	 * 
	 * @return FileSendHandleBiz单例
	 */
	public static FileSendHandleBiz getFileSendHandleBiz() {
		return fileSendHandleBiz;
	}

	public void singleFileReady(String msgStr_) {
		String[] msgInfo = msgStr_.split(":::");
		String fromUserName = msgInfo[0];
		String toUsrName = msgInfo[1];
		String ip = msgInfo[2];
		String filename = msgInfo[3];

		String message = MessageTypeEnum.FileReady.toString()
				+ ":::"
				+ fromUserName
				+ "\n"
				+ DataBaseHandleBiz.getDataBaseHandleBiz().getName_F_Id(
						Integer.valueOf(fromUserName)) + ":::" + ip + ":::"
				+ filename;

		// System.out.println(message + "  tousr ==" + toUsrName);
		CommonData.getClientThreadMap().get(toUsrName).sendData(message);
	}

	public void singleFileSend(String msgStr_) {
		String[] msgInfo = msgStr_.split(":::");
		String fromUserId = msgInfo[0];
		String toUsrName = msgInfo[1];
		String ip = msgInfo[2];
		String filename = msgInfo[3];

		String message = MessageTypeEnum.FileSend.toString()
				+ ":::"
				+ fromUserId
				+ "\n"
				+ DataBaseHandleBiz.getDataBaseHandleBiz().getName_F_Id(
						Integer.valueOf(fromUserId)) + ":::" + ip + ":::"
				+ filename;

		// System.out.println(message + "  tousr ==" + toUsrName);
		CommonData.getClientThreadMap().get(toUsrName).sendData(message);
	}
}
