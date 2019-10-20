package com.myf.qq.util;

import java.awt.Color;
import java.awt.Font;
import java.net.InetAddress;
import java.util.HashMap;

import QQ.WEB_INF.classes.com.myf.qq.domain.User;

import com.myf.qq.view.ChatUI;
import com.myf.qq.view.MainUI;
/**
 * 共有数据类
 * @author myf
 *
 */
public class CommonData
{
	///////////////////////// 系统部分  ///////////////////////
	/* 这部分可由用户指定   */
	// 服务器主机Ip
	private static String serverIp = null;
	// 服务器端口号
	private static int serverPort = 0;
	// 本地Ip
	private static String localHostIp = null;
	
	private static HashMap<String, ChatUI> chatFrameMap;
	
	private static HashMap<String, User> userImformationMap;
	
	private static ChatUI groupChatFrame;
	
	private static MainUI mainFrame;
	
	private static final int Size = 1024;
	
	private static int fileListenPort;
	
	///////////////////////// UI部分 ///////////////////////
	
	// Color
	private static Color qqColor = new Color(232, 236, 247);
	
	private static Color PaleTurquoise1 = new Color(6, 157, 213);
	
	private static Color focusColor = new Color(21, 131, 221);
	
	private static Color bgColor = new Color(235,242,249);
	
	private static Color fgColor = new Color(38, 133, 227);
	
	private static Color fgHoverColor = new Color(114, 214, 238);
	
	private static Color borderColor = new Color(118, 118, 118);
	
	private static Color mainFocusColor = new Color(168, 188, 191);
	
	private static Color mainColor = new Color(212, 229, 236);
	
	private static Color itemFocusColor = new Color(253, 236, 171);
	// Font
	private static Font logTextFont = new Font("微软雅黑", Font.PLAIN, 12);
	
	private static Font btnFont = new Font("黑体", Font.PLAIN, 12);
	
	private static Font titleFont = new Font("宋体", Font.BOLD, 20);
	
	private static Font inputFont = new Font("仿宋", Font.BOLD, 13);
	
	private static Font tabFont = new Font("宋体", Font.PLAIN, 16);
	
	private static Font titleWithSegoeFont = new Font("Segoe Scrip", Font.PLAIN, 25);
	
	// Font Style
	private static String htmlFontName = "宋体";
	
	// Font Color
	private static int htmlFontStyle = Font.PLAIN;//素色
	
	private static Color htmlFontColor = Color.black;
	
	// Font Size
	private static int htmlFontSize = 20;
	
	// title
	private static String id;
	
	private static String name;
	
	private static String sex;
	
	// File
	private static String fileName;
	
	// 如果成员变量没有指定就设置如下的默认配置
	static
	{
		try
        {
			fileListenPort = 1343;
			serverIp = (serverIp == null) ? InetAddress.getLocalHost().getHostAddress().toString() : serverIp;
			serverPort = (serverPort == 0) ? 9090 : 0;
			localHostIp = (localHostIp == null) ? InetAddress.getLocalHost().getHostAddress().toString() : localHostIp;
        }
        catch (Exception e)
        {
        	localHostIp = null;
        	serverIp = null;
            e.printStackTrace();
        }
		
		chatFrameMap = new HashMap<>();
	}
	
	
	public static Color getQqColor()
	{
		return qqColor;
	}
	
	public static Color getPaleTurquoise1()
	{
		return PaleTurquoise1;
	}
	
	public static Color getFocusColor()
	{
		return focusColor;
	}
	
	public static Color getBgColor()
	{
		return bgColor;
	}

	public static Font getLogTextFont()
	{
		return logTextFont;
	}

	public static Font getBtnFont()
	{
		return btnFont;
	}

	public static Color getFgColor()
	{
		return fgColor;
	}

	public static Color getFgHoverColor()
	{
		return fgHoverColor;
	}

	public static Color getBorderColor()
	{
		return borderColor;
	}

	public static String getServerIp()
	{
		return serverIp;
	}

	public static int getServerPort()
	{
		return serverPort;
	}

	public static String getLocalHostIp()
	{
		return localHostIp;
	}

	public static Font getTitleFont()
	{
		return titleFont;
	}

	public static String getHtmlFontName()
	{
		return htmlFontName;
	}

	public static int getHtmlFontStyle()
	{
		return htmlFontStyle;
	}

	public static int getHtmlFontSize()
	{
		return htmlFontSize;
	}

	public static String getName()
	{
		return name;
	}

	public static Color getHtmlFontColor()
	{
		return htmlFontColor;
	}

	public static String getId()
	{
		return id;
	}

	public static String getFileName()
	{
		return fileName;
	}

	public static void setFileName(String filename)
	{
		CommonData.fileName = filename;
	}

	public static void setHtmlFontName(String htmlFontName)
	{
		CommonData.htmlFontName = htmlFontName;
	}

	public static void setHtmlFontStyle(int htmlFontStyle)
	{
		CommonData.htmlFontStyle = htmlFontStyle;
	}

	public static void setHtmlFontColor(Color htmlFontColor)
	{
		CommonData.htmlFontColor = htmlFontColor;
	}

	public static void setHtmlFontSize(int htmlFontSize)
	{
		CommonData.htmlFontSize = htmlFontSize;
	}

	public static Font getInputFont()
	{
		return inputFont;
	}

	public static Color getMainFocusColor()
	{
		return mainFocusColor;
	}

	public static Color getMainColor()
	{
		return mainColor;
	}

	public static Font getTabFont()
	{
		return tabFont;
	}

	public static Font getTitleWithSegoeFont()
	{
		return titleWithSegoeFont;
	}

	public static Color getItemFocusColor()
	{
		return itemFocusColor;
	}
	
	public static ChatUI getGroupChatFrame() {
		if (groupChatFrame == null) {
			try {
				groupChatFrame = (ChatUI) Class.forName("com.myf.qq.view.ChatUI")
						.newInstance();

			} catch (Exception e) {
				System.err.println("群聊天窗口类异常");
				return null;
			}
		}
		return groupChatFrame;
	}

	public static HashMap<String, ChatUI> getChatFrameMap()
	{
		return chatFrameMap;
	}

	public static int getSize()
	{
		return Size;
	}

	public static int getFileListenPort()
	{
		return fileListenPort;
	}

	public static MainUI getMainFrame()
	{
		return mainFrame;
	}

	public static void setMainFrame(MainUI mainFrame)
	{
		CommonData.mainFrame = mainFrame;
	}

	public static void setName(String name)
	{
		CommonData.name = name;
	}

	public static HashMap<String, User> getUserImformationMap()
	{
		return userImformationMap;
	}

	public static void setUserImformationMap(
	        HashMap<String, User> userImformationMap)
	{
		CommonData.userImformationMap = userImformationMap;
	}

	public static String getSex()
	{
		return sex;
	}

	public static void setSex(String sex)
	{
		CommonData.sex = sex;
	}

	public static void setId(String id)
	{
		CommonData.id = id;
	}
	
	
}
