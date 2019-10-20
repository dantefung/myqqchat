package com.myf.qq.service;


/**
 * 
 * @author myf
 *
 */
public interface UserService
{
	void addFriend(int mid, int fid);

	void onlineUserDetailReturnHandle(String substring);

	void offlineUserDetailReturnHandle(String substring);

	void loginHandle(String substring);

	void newClientConnectHandle(String substring);

	void ClientOffLineHandle(String substring);
}
