package com.myf.qq.sys.factory;

import com.myf.qq.sys.domain.Config;
/**
 * 
 * @author myf
 *
 */
public class ConfigureFactory
{
	// 懒汉式单例模式
	
	private static Config config;
	
	public static Object getInstance(Class clazz)
	{
		synchronized (ConfigureFactory.class)
        {
			if(config == null)
			{
				try
                {
	                config = (Config) clazz.newInstance();
                }
                catch (Exception e)
                {
	                e.printStackTrace();
	                throw new RuntimeException(e);
                }
			}
        }
		
		return config;
	}

}
