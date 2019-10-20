package controller;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import model.biz.FileSendHandleBiz;

import comondata.comondata;

public class receiveFileThread extends Thread{
	public static BufferedInputStream fileBufferedInputStream;
	public static String path;
	public static String fileName;
	private static ServerSocket fileserServerSocket;
	
	public receiveFileThread(String p, String f){
		path = p;
		fileName = f;
	}
	
	public void stopFileserverSocket(){
		try {
			fileserServerSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void run(){
		//建立端口的服务器Socket
		try {
			fileserServerSocket = new ServerSocket(comondata.getFileListenPort());
		//阻塞监听
		System.out.println("开始监听");
		Socket socket = fileserServerSocket.accept();			
		System.out.println("监听到有人建立连接");			
		//文件接收
		try {
			String filename = path + "\\" + fileName;
			//System.out.println("filename in receive == " + filename);
			fileBufferedInputStream = new BufferedInputStream(socket.getInputStream());		
			receiveFile(fileBufferedInputStream, fileserServerSocket, socket, filename);
			
		} catch (IOException e) {
			System.err.println("scoket");
			e.printStackTrace();
		}
		} catch (IOException e1) {
			System.err.println("serverSocket");
			e1.printStackTrace();
			return;
		}
	}
	
	public void receiveFile(BufferedInputStream in, ServerSocket ss, Socket s, String filename){
		try {
			FileSendHandleBiz.getFileSendHandleBiz().recFileHandle(in, ss, s, filename);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
