package com.myf.qq.domain;

import com.myf.qq.util.MessageTypeEnum;

public class Message
{
	/**传递的消息**/
	private String msg;
	/**消息类型**/
	private MessageTypeEnum type;

	public Message()
    {
	    
    }
	
	public Message(MessageTypeEnum type, String msg)
    {
	    super();
	    this.msg = msg;
	    this.type = type;
    }

	public String getMsg()
	{
		return msg;
	}

	public void setMsg(String msg)
	{
		this.msg = msg;
	}

	public MessageTypeEnum getType()
	{
		return type;
	}

	public void setType(MessageTypeEnum type)
	{
		this.type = type;
	}

	@Override
    public String toString()
    {
	    return "Message [msg=" + msg + ", type=" + type + "]";
    }
	
	
}
