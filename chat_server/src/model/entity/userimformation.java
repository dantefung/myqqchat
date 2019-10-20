package model.entity;

import java.io.Serializable;

public class userimformation  implements Serializable
{
	private String  		id;
	private String 			name;
	
	public userimformation(String Id, String Name)
	{
		id = Id;
		name = Name;
	}
	public void setName(String Name)
	{
		name = Name;
	}
	public String getName()
	{
		return name;
	}
	public void SetId(String Id)
	{
		id = Id;
	}
	public String getId()
	{
		return id;
	}
}
