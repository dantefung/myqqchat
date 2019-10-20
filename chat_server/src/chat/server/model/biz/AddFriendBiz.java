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
										+ "�û���Ϊ��"
										+ DataBaseHandleBiz
												.getDataBaseHandleBiz()
												.getName_F_Id(mid)
										+ " ���û������Ϊ����");
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
									+ "��ӳɹ�!");
			return true;
		}
		CommonData
				.getClientThreadMap()
				.get(String.valueOf(mid))
				.sendData(
						MessageTypeEnum.AddFriendResult.toString() + ":::"
								+ "���ʧ��!");
		return false;
	}
}
