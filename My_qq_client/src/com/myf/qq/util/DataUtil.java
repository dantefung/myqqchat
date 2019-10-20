package com.myf.qq.util;

import com.myf.qq.domain.Message;

public class DataUtil
{
	private static String delimeter = ":::";
	
	/**
	 * 将数据格式化:   消息（请求）类型:::数据1:::数据2:::数据3::: ... 
	 * @param msgType
	 * @param args
	 * @return
	 */
	public static String dataFormat(MessageTypeEnum msgType, Object...args)
	{
		String msg = msgType.toString();
		// 用户传递的是多个参数，进行格式化
		for(Object str : args)
		{
			msg+=delimeter + str;
		}

		return msg;
	}

	/**
	 * 生成消息Bean
	 * @param msgType 消息类型
	 * @param args    传递的数据的数组
	 * @return
	 */
	public static Message genMessage(MessageTypeEnum msgType, Object...args)
	{
		return new Message(msgType, dataFormat(msgType, args));
	}
	
	/**
	 * 生成消息Bean
	 * @param msgType 消息类型
	 * @param formattedMsg 已经格式化后的待传递的数据
	 * @return
	 */
	public static Message genMessage(MessageTypeEnum msgType, String formattedMsg)
	{
		return new Message(msgType, formattedMsg);
	}
	
	// For test ... 
	public static void main(String[] args)
    {
	   System.out.println(DataUtil.dataFormat(MessageTypeEnum.Login, "123","张三"));
    }
}
