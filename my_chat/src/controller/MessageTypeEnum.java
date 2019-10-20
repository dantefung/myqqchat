package controller;

public enum MessageTypeEnum {
	Login, // ��¼
	LoginReturn, // ���ص�¼���
	AllOnlineUserDetailReturn, // �������������û�������Ϣ
	AllOfflineUserDetailReturn, // ���������û�������Ϣ
	ClientOffLine, // �û�����
	NewClientConnect, // �����û�����
	GroupChat, // Ⱥ��
	SingleChat, // һ��һ����
	FileSend, // �ļ���������
	FileReady, // �ȴ�����
	SignUp, // ע��
	AddFriend, // ��Ӻ���
	AddFriendResult, // ��Ӻ��ѽ��
	RemoveFriend // ɾ������
}
