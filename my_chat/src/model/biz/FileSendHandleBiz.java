package model.biz;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Timer;

import comondata.comondata;

public class FileSendHandleBiz {

	private static final FileSendHandleBiz FILEHANDLE = new FileSendHandleBiz();

	private FileSendHandleBiz() {

	}

	public static FileSendHandleBiz getFileSendHandleBiz() {
		return FILEHANDLE;
	}

	/**
	 * �ļ�����
	 * 
	 * @param out
	 *            �����
	 * @param filename
	 *            ����·�����ļ���
	 * @throws IOException
	 */
	public void sendFileHandle(BufferedOutputStream out, String filename)
			throws IOException {
		// System.out.println("filename == " + filename);
		int fileSize = 0;
		byte[] buffer = new byte[comondata.getSize()];
		FileInputStream fis = new FileInputStream(filename);
		fileSize = fis.available();
		BufferedInputStream in = new BufferedInputStream(fis);
		DataOutputStream dOut = new DataOutputStream(out);
		dOut.writeInt(fileSize);

		Timer timer = new Timer();
		CulTimerBiz culTimer = new CulTimerBiz(fileSize, "����");
		timer.schedule(culTimer, 1000, 1000);

		while (in.read(buffer, 0, comondata.getSize()) != -1 && fileSize != -1) {
			out.write(buffer, 0, comondata.getSize());
			culTimer.setCulData(comondata.getSize());
		}

		timer.cancel();

		in.close();
		out.close();
		System.out.println("�ļ���СΪ��" + fileSize + " ");
		System.out.println("�ļ��������!");
	}

	/**
	 * �����ļ��Ľ���
	 * 
	 * @param in
	 *            ������
	 * @param serversocket
	 *            ������׽��֣����ڽ�����ɺ�رգ�
	 * @param socket
	 *            �׽��֣����ڽ�����ɺ�رգ�
	 * @param filename
	 *            ������·�����ļ�����
	 * @throws IOException
	 */
	public void recFileHandle(BufferedInputStream in,
			ServerSocket serversocket, Socket socket, String filename)
			throws IOException {
		// System.out.println("receive file");
		int fileSize;
		// String path = "D:\\Users\\BlackWorkStation\\Desktop\\�¤���.jpg";
		BufferedOutputStream Out = new BufferedOutputStream(
				new FileOutputStream(filename));
		DataInputStream dIn = new DataInputStream(in);
		fileSize = dIn.readInt();

		Timer timer = new Timer();
		CulTimerBiz culTimer = new CulTimerBiz(fileSize, "����");
		timer.schedule(culTimer, 1000, 1000);

		byte[] buffer = new byte[comondata.getSize()];
		while (in.read(buffer, 0, comondata.getSize()) != -1) {
			Out.write(buffer, 0, comondata.getSize());
			culTimer.setCulData(comondata.getSize());
		}

		timer.cancel();

		System.out.println("�ѽ��գ�" + fileSize + " ");
		Out.close();
		in.close();
		socket.close();
		serversocket.close();
		System.out.println("�ļ��������!");
	}

}
