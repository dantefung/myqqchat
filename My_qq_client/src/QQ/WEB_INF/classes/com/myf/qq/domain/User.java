package QQ.WEB_INF.classes.com.myf.qq.domain;

import java.io.Serializable;

public class User implements Serializable
{
	/**
	 * 
	 */
    private static final long serialVersionUID = -3437139207937780277L;
	// QQ账号
	private String id;
	// 用户名
	private String name;
	// 密码
	private String password;
	// 生日
	private String birthday;
	// 居住地
	private String live;
	// 性别
	private String sex;
	// 
	private String login;
	//
	private String friend;
	
	public static String LOGOUT = "0";
	
	public static String LOGIN = "1";
	
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public String getPassword()
	{
		return password;
	}
	public void setPassword(String password)
	{
		this.password = password;
	}
	public String getBirthday()
	{
		return birthday;
	}
	public void setBirthday(String birthday)
	{
		this.birthday = birthday;
	}
	public String getLive()
	{
		return live;
	}
	public void setLive(String live)
	{
		this.live = live;
	}
	public String getSex()
	{
		return sex;
	}
	public void setSex(String sex)
	{
		this.sex = sex;
	}
	public String getLogin()
	{
		return login;
	}
	public void setLogin(String login)
	{
		this.login = login;
	}
	public String getFriend()
	{
		return friend;
	}
	public void setFriend(String friend)
	{
		this.friend = friend;
	}
	public String getId()
	{
		return id;
	}
	public void setId(String id)
	{
		this.id = id;
	}
	@Override
    public String toString()
    {
	    return "User [id=" + id + ", name=" + name + ", password=" + password
	            + ", birthday=" + birthday + ", live=" + live + ", sex=" + sex
	            + ", login=" + login + ", friend=" + friend + "]";
    }
	
}
