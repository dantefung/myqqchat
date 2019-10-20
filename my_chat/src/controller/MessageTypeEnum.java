package controller;

public enum MessageTypeEnum {
	Login, // 登录
	LoginReturn, // 返回登录结果
	AllOnlineUserDetailReturn, // 返回所有在线用户基本信息
	AllOfflineUserDetailReturn, // 所有离线用户基本信息
	ClientOffLine, // 用户下线
	NewClientConnect, // 有新用户登入
	GroupChat, // 群聊
	SingleChat, // 一对一单聊
	FileSend, // 文件传送请求
	FileReady, // 等待传送
	SignUp, // 注册
	AddFriend, // 添加好友
	AddFriendResult, // 添加好友结果
	RemoveFriend // 删除好友
}
