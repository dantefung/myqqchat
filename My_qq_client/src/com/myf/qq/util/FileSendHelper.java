package com.myf.qq.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.Socket;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.myf.qq.control.ChatClientProxy;
import com.myf.qq.control.ReceiveFileThread;
import com.myf.qq.service.impl.FileSendHandleBiz;
import com.myf.qq.view.ChatUI;

public class FileSendHelper
{
	public static final FileSendHelper files = new FileSendHelper();

	public static BufferedInputStream fileBufferedInputStream;
	public static BufferedOutputStream fileBufferedOutputStream;

	private static ReceiveFileThread rcThread;

	public FileSendHelper() {
	}

	public static ReceiveFileThread getrcThread() {
		return rcThread;
	}

	public static FileSendHelper getFileSendHelper() {
		return files;
	}

	/**
	 * 接收文件准备开始
	 */
	public void ready(String msgStr) {
		String[] msgInfo = msgStr.split(":::");
		String toUserName = msgInfo[0];
		String fileName = msgInfo[2];
		// System.out.println("tousername == " + toUserName);

		// 判断是否有与该用户聊过天，若无则添加一实例
		if (CommonData.getChatFrameMap().get(toUserName) == null) {
			CommonData.getChatFrameMap().put(toUserName,
					new ChatUI(toUserName));
		}

		// 当窗体可见时
		if (CommonData.getChatFrameMap().get(toUserName).isVisible()) {
			// 窗体可见且最小化时，让其获取焦点并保持最小化
			if (CommonData.getChatFrameMap().get(toUserName).getState() == JFrame.ICONIFIED) // JFrame.MAXIMIZED_BOTH
			{
				CommonData.getChatFrameMap().get(toUserName).setVisible(true);
				CommonData.getChatFrameMap().get(toUserName)
						.setExtendedState(JFrame.ICONIFIED);
			}

			// 窗体不是最小化时不做操作
			else {
			}
		}

		// 当窗体不可见时
		else {
			// 若窗体不可见则让其可见
			CommonData.getChatFrameMap().get(toUserName).setVisible(true);
		}

		// 接收文件
		String path = "D:\\"; // 文件存储的路径 默认D盘

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}

		JFileChooser fileChooser = new JFileChooser();

		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		int selectRst = fileChooser.showOpenDialog(CommonData.getChatFrameMap()
				.get(toUserName));
		if (selectRst == JFileChooser.APPROVE_OPTION) {
			path = fileChooser.getSelectedFile().toPath().toString();
			ChatClientProxy.getInstance()
			               .sendRequest(DataUtil.genMessage(MessageTypeEnum.FileReady,
					       toUserName.split("\n")[0], CommonData.getLocalHostIp(), fileName));
		}
		rcThread = new ReceiveFileThread(path, fileName);
		rcThread.start();
	}

	public void sendFile(String msgStr) {
		String[] msgInfo = msgStr.split(":::");
		String ip = msgInfo[1];
		try {
			// 客户端Socket
			Socket fileSocket;
			// System.out.println("IP == " + ip);
			fileSocket = new Socket(ip, CommonData.getFileListenPort());
			fileBufferedOutputStream = new BufferedOutputStream(
					fileSocket.getOutputStream());
			// System.out.println("commondata.getfilename == " +
			// CommonData.getFileName());
			FileSendHandleBiz.getFileSendHandleBiz().sendFileHandle(
					fileBufferedOutputStream, CommonData.getFileName());
			fileSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
