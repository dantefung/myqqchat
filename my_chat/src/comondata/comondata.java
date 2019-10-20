package comondata;

import java.awt.Color;
import java.awt.Font;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;

import controller.receiveFileThread;

import model.entity.userimformation;

import view.ChatView;
import view.SignUp;
import view.MainView;

public class comondata {
	private static String id;
	private static String name;
	private static String sex;
	private static String myIp;
	private static String serverIp;
	private static int serverPort;
	private static ChatView groupChatFrame;
	private static MainView mainFrame;
	private static HashMap<String, ChatView> chatFrameMap;
	private static HashMap<String, userimformation> userImformationMap;
	private static int fileListenPort;
	private static final int Size = 1024;
	private static String filename;
	private static SignUp signUp = null;
	private static int htmlFontSize = 20;
	private static Color htmlFontColor = Color.black;
	private static int htmlFontStyle = Font.PLAIN;//素色
	private static String htmlFontName = "宋体";

	private static Font btnFont = new Font("黑体", Font.PLAIN
			, 12);
	private static Font titleFont = new Font("宋体", Font.BOLD, 20);
	private static Font titleWithSegoeFont = new Font("Segoe Scrip",
			Font.PLAIN, 25);
	private static Font tabFont = new Font("宋体", Font.PLAIN, 16);
	private static Font logTextFont = new Font("Segoe Script", Font.PLAIN, 15);
	private static Font hintFont = new Font("仿宋", Font.PLAIN, 13);
	private static Font inputFont = new Font("仿宋", Font.BOLD, 13);
	private static Font comboxFont = new Font("仿宋", Font.BOLD, 15);
	private static Font signupFont = new Font("黑体", Font.BOLD, 16);
	private static Color qqColor = new Color(232, 236, 247);
	private static Color borderColor = new Color(215, 217, 227);
	private static Color PaleTurquoise1 = new Color(6, 157, 213);
	private static Color focusColor = new Color(12, 122, 185);
	private static Color itemFocusColor = new Color(253, 236, 171);
	private static Color mainColor = new Color(212, 229, 236);
	private static Color mainFocusColor = new Color(168, 188, 191);

	static {
		fileListenPort = 1343;
		name = "";
		sex = "";
		try {
			serverIp = InetAddress.getLocalHost().getHostAddress().toString();
			myIp = getLocalIp();
		} catch (Exception e) {
			myIp = null;
			e.printStackTrace();
		}
//		serverPort = 6688;
		serverPort = 9090;
		chatFrameMap = new HashMap<>();
	}

	public static void setHtmlFontSize(int fontsize) {
		htmlFontSize = fontsize;
	}

	public static int getHtmlFontSize() {
		return htmlFontSize;
	}

	public static void setHtmlFontColor(Color fontcolor) {
		htmlFontColor = fontcolor;
	}

	public static Color getHtmlFontColor() {
		return htmlFontColor;
	}

	public static void setHtmlFontName(String fontname) {
		htmlFontName = fontname;
	}

	public static String getHtmlFontName() {
		return htmlFontName;
	}
	
	public static void setHtmlFontStyle(int fontstyle){
		htmlFontStyle = fontstyle;
	}
	
	public static int getHtmlFontStyle(){
		return htmlFontStyle;
	}

	private static String getLocalIp() throws IOException {
		Process process = Runtime.getRuntime().exec("ipconfig");//调用doc命令。
		BufferedReader br = new BufferedReader(new InputStreamReader(
				process.getInputStream()));//将调用doc命令后的结果读到内存里
		String msg = null;
		boolean yitai = false;
		while ((msg = br.readLine()) != null) {
			if (msg.contains("以太网适配器 本地连接:")) {
				yitai = true;
			}
			if (yitai) {
				if (msg.contains("IPv4 地址 ")) {//trim():Returns a copy of the string, with leading and trailing whitespace omitted
					return msg.substring(msg.indexOf(":") + 1).trim();
					//trim():Returns a copy of the string, with leading and trailing whitespace omitted
					//函数的返回值是一个去除开头和结尾空格的字符串
				}
			}
		}
		return null;
	}

	public static Color getMainFocusColor() {
		return mainFocusColor;
	}

	public static Color getMainColor() {
		return mainColor;
	}

	public static Color getItemFocusColor() {
		return itemFocusColor;
	}

	public static void setSignUp(SignUp sp) {
		signUp = sp;
	}

	public static SignUp getSignUp() {
		return signUp;
	}

	public static Font getSignUpFont() {
		return signupFont;
	}

	public static Font getComboxFont() {
		return comboxFont;
	}

	public static Font getTitleWithSegoeFont() {
		return titleWithSegoeFont;
	}

	public static Color getFocusColor() {
		return focusColor;
	}

	public static Color getqqColor() {
		return qqColor;
	}

	public static Color getBorderColor() {
		return borderColor;
	}

	public static Color getPaleTurquoise1() {
		return PaleTurquoise1;
	}

	public static Font getInputFont() {
		return inputFont;
	}

	public static Font getHintFont() {
		return hintFont;
	}

	public static Font getLogTextFont() {
		return logTextFont;
	}

	public static Font getTabFont() {
		return tabFont;
	}

	public static Font getBtnFont() {
		return btnFont;
	}

	public static Font getTitleFont() {
		return titleFont;
	}

	public static String getServerIp() {
		return serverIp;
	}

	public static void setServerIp(String ip) {
		serverIp = ip;
	}

	public static int getFileListenPort() {
		return fileListenPort;
	}

	public static int getSize() {
		return Size;
	}

	public static String getName() {
		return name;
	}

	public static void setName(String n) {
		name = n;
	}

	public static void setId(String i) {
		id = i;
	}

	public static String getId() {
		return id;
	}

	public static String getSex() {
		return sex;
	}

	public static void setSex(String s) {
		sex = s;
	}

	public static String getMyIp() {
		return myIp;
	}

	public static void setMyIp(String si) {
		myIp = si;
	}

	public static int getServerPort() {
		return serverPort;
	}

	public static void setServerPort(int spt) {
		serverPort = spt;
	}

	public static ChatView getGroupChatFrame() {
		if (comondata.groupChatFrame == null) {
			try {
				comondata.groupChatFrame = (ChatView) Class.forName("view.ChatView")
						.newInstance();

			} catch (Exception e) {
				System.err.println("群聊天窗口类异常");
				return null;
			}
		}
		return comondata.groupChatFrame;
	}

	public static void setGroupChatFrame(ChatView gcf) {
		groupChatFrame = gcf;
	}

	public static MainView getMainFrame() {
		return mainFrame;
	}

	public static void setMainFrame(MainView mf) {
		mainFrame = mf;
	}

	public static HashMap<String, ChatView> getChatFrameMap() {
		return chatFrameMap;
	}

	public static void setChatFramMap(HashMap<String, ChatView> cfm) {
		chatFrameMap = cfm;
	}

	public static HashMap<String, userimformation> getUserInformationMap() {
		return userImformationMap;
	}

	public static void setUserImformationMap(
			HashMap<String, userimformation> uim) {
		userImformationMap = uim;
	}

	public static String getFileName() {
		return filename;
	}

	public static void setFileName(String fn) {
		filename = fn;
	}
}