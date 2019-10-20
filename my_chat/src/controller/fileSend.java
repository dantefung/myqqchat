package controller;

import java.awt.Component;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Timer;
import java.util.TimerTask;

import javax.sql.CommonDataSource;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.omg.CORBA.PUBLIC_MEMBER;

import view.ChatView;

import com.sun.jndi.url.corbaname.corbanameURLContextFactory;
import comondata.comondata;

import model.biz.FileSendHandleBiz;

public class fileSend {
	public static final fileSend files = new fileSend();

	public static BufferedInputStream fileBufferedInputStream;
	public static BufferedOutputStream fileBufferedOutputStream;

	private static receiveFileThread rcThread;

	public fileSend() {
	}

	public static receiveFileThread getrcThread() {
		return rcThread;
	}

	public static fileSend getFileSend() {
		return files;
	}

	/**
	 * �����ļ�׼����ʼ
	 */
	public void ready(String msgStr) {
		String[] msgInfo = msgStr.split(":::");
		String toUserName = msgInfo[0];
		String fileName = msgInfo[2];
		// System.out.println("tousername == " + toUserName);

		// �ж��Ƿ�������û��Ĺ��죬���������һʵ��
		if (comondata.getChatFrameMap().get(toUserName) == null) {
			comondata.getChatFrameMap().put(toUserName,
					new ChatView(toUserName));
		}

		// ������ɼ�ʱ
		if (comondata.getChatFrameMap().get(toUserName).isVisible()) {
			// ����ɼ�����С��ʱ�������ȡ���㲢������С��
			if (comondata.getChatFrameMap().get(toUserName).getState() == JFrame.ICONIFIED) // JFrame.MAXIMIZED_BOTH
			{
				comondata.getChatFrameMap().get(toUserName).setVisible(true);
				comondata.getChatFrameMap().get(toUserName)
						.setExtendedState(JFrame.ICONIFIED);
			}

			// ���岻����С��ʱ��������
			else {
			}
		}

		// �����岻�ɼ�ʱ
		else {
			// �����岻�ɼ�������ɼ�
			comondata.getChatFrameMap().get(toUserName).setVisible(true);
		}

		// �����ļ�
		String path = "D:\\"; // �ļ��洢��·�� Ĭ��D��

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}

		JFileChooser fileChooser = new JFileChooser();

		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		int selectRst = fileChooser.showOpenDialog(comondata.getChatFrameMap()
				.get(toUserName));
		if (selectRst == JFileChooser.APPROVE_OPTION) {
			path = fileChooser.getSelectedFile().toPath().toString();
			receivequestion.getMainSocket().sendQuest(
					MessageTypeEnum.FileReady.toString(),
					toUserName.split("\n")[0], comondata.getMyIp(), fileName);
		}
		rcThread = new receiveFileThread(path, fileName);
		rcThread.start();
	}

	public void sendFile(String msgStr) {
		String[] msgInfo = msgStr.split(":::");
		String ip = msgInfo[1];
		try {
			// �ͻ���Socket
			Socket fileSocket;
			// System.out.println("IP == " + ip);
			fileSocket = new Socket(ip, comondata.getFileListenPort());
			fileBufferedOutputStream = new BufferedOutputStream(
					fileSocket.getOutputStream());
			// System.out.println("commondata.getfilename == " +
			// comondata.getFileName());
			FileSendHandleBiz.getFileSendHandleBiz().sendFileHandle(
					fileBufferedOutputStream, comondata.getFileName());
			fileSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
