package chat.server.model.biz;

import chat.server.common.CommonData;
import chat.server.controller.MessageTypeEnum;

public class RemoveFriendBiz {
	private static RemoveFriendBiz removeFriendBiz = new RemoveFriendBiz();

	public RemoveFriendBiz() {

	}

	public static RemoveFriendBiz getRemoveFriendBiz(){
		return removeFriendBiz;
	}
	
	public void removeFriend(String msgStr_) {
		int mid = Integer.valueOf(msgStr_.split(":")[0]);
		int fid = Integer.valueOf(msgStr_.split(":")[1]);

		DataBaseHandleBiz.getDataBaseHandleBiz().removeFriend_F_Id(mid, fid);
		DataBaseHandleBiz.getDataBaseHandleBiz().removeFriend_F_Id(fid, mid);

		if (DataBaseHandleBiz.getDataBaseHandleBiz().IsLogin(fid)) {
			CommonData
					.getClientThreadMap()
					.get(String.valueOf(fid))
					.sendData(
							MessageTypeEnum.RemoveFriend.toString()
									+ ":::"
									+ String.valueOf(mid)
									+ "\n"
									+ DataBaseHandleBiz.getDataBaseHandleBiz()
											.getName_F_Id(mid));
		}

		CommonData
				.getClientThreadMap()
				.get(String.valueOf(mid))
				.sendData(
						MessageTypeEnum.RemoveFriend.toString()
								+ ":::"
								+ String.valueOf(fid)
								+ "\n"
								+ DataBaseHandleBiz.getDataBaseHandleBiz()
										.getName_F_Id(fid));
	}
}
