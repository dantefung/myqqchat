package model.biz;

import comondata.comondata;
import controller.MessageTypeEnum;
import controller.clientthread;
import controller.receivequestion;
//Biz ÒµÎñÂß¼­²ã
public class AddFriendBiz {
	private static AddFriendBiz addFriendBiz = new AddFriendBiz();

	public static AddFriendBiz getAddFriendBiz() {
		return addFriendBiz;
	}

	public void addFriendRequest(int mid, int fid) {
		receivequestion
				.getMainSocket()
				.getServerThread()
				.sendData(
						MessageTypeEnum.AddFriend.toString() + ":::"
								+ String.valueOf(mid) + ":"
								+ String.valueOf(fid));
	}
}
