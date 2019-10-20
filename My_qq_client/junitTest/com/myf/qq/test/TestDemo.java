package com.myf.qq.test;

import static org.junit.Assert.*;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.InetAddress;
import java.util.Properties;

import org.junit.Test;

public class TestDemo
{
	/**
	 * 测试给配置文件中的key赋值，并以输出流的方式写到磁盘里
	 * @throws Exception
	 */
	@Test
    public void testProperties() throws Exception
    {
	    Properties prop = new Properties();
	    prop.load(new FileInputStream("junitTest/test.properties"));
	    prop.setProperty("username", "dante");
	    prop.setProperty("password", "123");
	    prop.setProperty("isRemmeber", "1");
	    prop.store(new FileOutputStream("junitTest/test.properties"), "login info");
    }
	
	@Test
    public void testInetAddress() throws Exception
    {
	    System.out.println(InetAddress.getLocalHost().getHostAddress().toString());
    }
}
