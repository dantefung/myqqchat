package com.myf.qq.sys.mgr;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import com.myf.qq.sys.domain.Config;
import com.myf.qq.sys.domain.LoginConfig;
import com.myf.qq.sys.factory.ConfigureFactory;
import com.myf.qq.sys.util.Constants;

/**
 * 配置管理器
 * 
 * @author myf
 * 
 */
public class ConfigurationManager
{
	// 主配置文件
	private static Properties properties;
	
	// 用戶登錄信息配置文件
	private static Properties loginInfo;
	
	private static Config config;
	
	static
	{
		try
		{
			properties = new Properties();
			properties.load(new BufferedInputStream(new FileInputStream(Constants.MAINIFESTPATH)));
			
			loginInfo = new Properties();
			loginInfo.load(new BufferedInputStream(new FileInputStream(Constants.LONGININFO)));
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		catch (IOException e)
		{
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 获取配置类
	 * 
	 * @param clazz
	 *            指定具体的配置类
	 * @return
	 */
	public static Config getConfig(Class clazz)
	{
		if ("LoginConfig".equals(clazz.getSimpleName()))
		{
			initLoginUIConfig();
		}
		return config;
	}
	
	/**
	 * 初始化登录页面的配置
	 */
	public static void initLoginUIConfig()
	{
		config = (Config) ConfigureFactory.getInstance(LoginConfig.class);
		config.setUserNameHint(properties.getProperty(Constants.USERNAMEHINT));
		config.setPassWordHint(properties.getProperty(Constants.PASSWORDHINT));
		config.setUserName(loginInfo.getProperty(Constants.USERNAME));
		config.setPassWord(loginInfo.getProperty(Constants.PASSWORD));
		config.setHeadImgURL(properties.getProperty(Constants.HEADIMG));
		config.setLoginbgURL(properties.getProperty(Constants.LOGINBGIMG));
		config.setShrinkURL(properties.getProperty(Constants.QQMINIIMG));
		config.setCloseURL(properties.getProperty(Constants.QQCLOSEIMG));
		config.setLoginbtnURL(properties.getProperty(Constants.LOGINBTN));
		config.setLoginbtnHoverURL(properties.getProperty(Constants.LOGINBTNHOVER));
		config.setLoginbtnPressURL(properties.getProperty(Constants.LOGINBTNPRESS));
		config.setMultiLogURL(properties.getProperty(Constants.LOGINMULTI));
		config.setLoginmultiPressURL(properties.getProperty(Constants.LOGINMULTIPRESS));
		config.setLoginQRURL(properties.getProperty(Constants.LOGINQR));
		config.setLoginQRPressURL(properties.getProperty(Constants.LOGINQRPRESS));
		config.setCloseHoverURL(properties.getProperty(Constants.QQCLOSEHOVER));
		config.setShrinkHoverURL(properties.getProperty(Constants.QQMINIHOVER));
		config.setClosePressURL(properties.getProperty(Constants.QQCLOSEPRESS));
		config.setCheckboxURL(properties.getProperty(Constants.CHECKBOX));
		config.setCheckboxHoverURL(properties.getProperty(Constants.CHECKBOXHOVER));
		config.setCheckboxCheckedURL(properties.getProperty(Constants.CHECKBOXCHECKED));
	}

	/**
	 * 获取Properties类实例loginInfo
	 * 
	 * @return
	 */
	public static Properties getLoginInfo()
	{
		return loginInfo;
	}
	
	public static void writeLoginInfo2Prop(String isRemmber, String userName, String passWord)
	{
		 loginInfo.setProperty(Constants.ISREMMEBER, isRemmber);
		 loginInfo.setProperty(Constants.USERNAME, userName);
		 loginInfo.setProperty(Constants.PASSWORD, passWord);
		 try
	    {
	        ConfigurationManager.getLoginInfo().store(new BufferedOutputStream(new FileOutputStream(Constants.LONGININFO)), "login info");
	    }
	    catch (Exception ex)
	    {
	        ex.printStackTrace();
	        throw new RuntimeException(ex);
	    }
	}
	
}
