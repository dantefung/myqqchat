package chat.server.model.biz;

import chat.server.common.CommonData;
import chat.server.controller.MessageTypeEnum;

public class AddFriendBiz {
	private static AddFriendBiz addFriendBiz = new AddFriendBiz();

	public static AddFriendBiz getAddFriendBiz() {
		return addFriendBiz;
	}

	public boolean addFriend(String msgStr_) {
		int mid = Integer.valueOf(msgStr_.split(":")[0]);
		int fid = Integer.valueOf(msgStr_.split(":")[1]);
		System.out.println("in addfriend");
		if (!DataBaseHandleBiz.getDataBaseHandleBiz().isFriend(mid, fid)
				&& DataBaseHandleBiz.getDataBaseHandleBiz().isUserExist(fid)
				&& mid != fid) {
			DataBaseHandleBiz.getDataBaseHandleBiz().addFriend_F_Id(mid, fid);
			DataBaseHandleBiz.getDataBaseHandleBiz().addFriend_F_Id(fid, mid);
			if (DataBaseHandleBiz.getDataBaseHandleBiz().IsLogin(fid)) {
				CommonData
						.getClientThreadMap()
						.get(String.valueOf(fid))
						.sendData(
								MessageTypeEnum.AddFriendResult.toString()
										+ ":::"
										+ "用户名为："
										+ DataBaseHandleBiz
												.getDataBaseHandleBiz()
												.getName_F_Id(mid)
										+ " 的用户添加你为好友");
				CommonData
						.getClientThreadMap()
						.get(String.valueOf(mid))
						.sendData(
								MessageTypeEnum.NewClientConnect.toString()
										+ ":::"
										+ String.valueOf(fid)
										+ "\n"
										+ DataBaseHandleBiz
												.getDataBaseHandleBiz()
												.getName_F_Id(
														Integer.valueOf(String
																.valueOf(fid)))
										+ ":::" + "LOGIN");
				CommonData
				.getClientThreadMap()
				.get(String.valueOf(fid))
				.sendData(
						MessageTypeEnum.NewClientConnect.toString()
								+ ":::"
								+ String.valueOf(mid)
								+ "\n"
								+ DataBaseHandleBiz
										.getDataBaseHandleBiz()
										.getName_F_Id(
												Integer.valueOf(String
														.valueOf(mid)))
								+ ":::" + "LOGIN");
			}
			else {
				CommonData
				.getClientThreadMap()
				.get(String.valueOf(mid))
				.sendData(
						MessageTypeEnum.NewClientConnect.toString()
								+ ":::"
								+ String.valueOf(fid)
								+ "\n"
								+ DataBaseHandleBiz
										.getDataBaseHandleBiz()
										.getName_F_Id(
												Integer.valueOf(String
														.valueOf(fid)))
								+ ":::" + "LOGOUT");
			}
			CommonData
					.getClientThreadMap()
					.get(String.valueOf(mid))
					.sendData(
							MessageTypeEnum.AddFriendResult.toString() + ":::"
									+ "添加成功!");
			return true;
		}
		CommonData
				.getClientThreadMap()
				.get(String.valueOf(mid))
				.sendData(
						MessageTypeEnum.AddFriendResult.toString() + ":::"
								+ "添加失败!");
		return false;
	}
}
