package com.myf.qq.sys.factory;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;

import com.myf.qq.service.UserService;
import com.myf.qq.sys.util.Constants;
import com.myf.qq.sys.util.Dom4jUtil;
/**
 * IOC容器：类似Spring的对象工厂
 * @author myf
 *
 */
public class BeanFactory {
	
	private static Document document;
	
	private static Map<String, Object> container = new HashMap<String, Object>();

	static
	{
		document = Dom4jUtil.parse(Constants.BEANXMLPATH);
	}
	
	public static void init()
    {
	    List<Element> beans = document.selectNodes("/beans/bean");
	    try
	    {
		    for(Element bean : beans)
		    {
		    	// 1、创建对象
		    	try
                {
	                container.put(bean.attribute("id").getValue()
	                		, Class.forName(bean.attribute("class").getValue()).newInstance());
                }
                catch (Exception e)// 如果拿到是类的短名，抛出异常
                {
                	// 则不将创建该类，让用户自己调用setBean方法注入
                	System.out.println("短名：" + bean.attribute("class").getValue());
                }
		    }
		    
		    for(Element bean : beans)
		    {
		    	// 2、处理对象之间的依赖关系
		    	// 根据类全名创建对象obj
		    	Object obj = container.get(bean.attribute("id").getValue());
		    	System.out.println(obj);
		    	List<Element> elems = bean.elements();
		    	for(Element elem : elems)
		    	{
		    		if (elem != null)
                    {
			    		// xml中该属性所引用的IOC容器中的对象的名称
			    		String refName = elem.attribute("ref").getValue();
			    		// 1、从IOC容器中取出obj所引用的对象refObj
			    		Object refObj = container.get(refName);
			    		
			    		// 2、通过反射机制将对象refObj注入对象obj中
			    		Class<?>[] interfaces = refObj.getClass().getInterfaces();
			    		Method method = null;
			    		// 2.1、凭凑obj的方法名
			    		// obj对象中的成员属性名
			    		String propertyName = elem.attribute("name").getValue();
			    		String methodName = "set"+String.valueOf(propertyName.charAt(0)).toUpperCase()+propertyName.substring(1);
			    		// 2.2、确定入参的类型
			    		// 判断注入的对象是否有实现接口
			    		if(interfaces != null && interfaces.length > 0)
			    		{
			    			// 有，设定obj对象的方法名methodName,入参类型为注入对象的接口类型
			    			method = obj.getClass().getDeclaredMethod(methodName, interfaces[0]);
			    		}
			    		else
			    		{
			    			// 没有，设定obj对象的方法名methodName,入参类型为注入对象的类型
			    			method = obj.getClass().getDeclaredMethod(methodName, refObj.getClass());
			    		}
			    		// 2.3、注入
			    		method.invoke(obj, refObj);
                    }
		    		
		    	}
		    }
		    System.out.println("IOC容器启动完毕... ...");
	    }
	    catch (Exception e)
	    {
	    	e.printStackTrace();
	    }
    }
	
	public static <T> T getBean(Class<T>  clazz)
	{
		String simpleName = clazz.getSimpleName();
		simpleName = String.valueOf(simpleName.charAt(0)).toLowerCase() + simpleName.substring(1);
		Element bean = (Element) document.selectSingleNode("/beans/bean[@id='"+simpleName+"']");
		Attribute attribute = bean.attribute("class");
		String value = attribute.getValue();
		try 
		{
			return (T) Class.forName(value).newInstance();
		} 
		catch (Exception e) 
		{
			throw new RuntimeException(e);
		}
	
	}
	
	public static Object getBean(String id) 
	{
		return container.get(id);
	}
	
	
	public static void setBean(String id, Object obj)
    {
		container.put(id, obj);
    }
	public static void main(String[] args) throws Exception  {
/*		URL url = BeanFactory.class.getClassLoader().getResource(".");
		URL url2 = BeanFactory.class.getClassLoader().getResource("/");
		String uri = url.toString();
		System.out.println(uri.substring(uri.indexOf("/")+1) + "Bean.xml");
		System.out.println(url);
		System.out.println(url2);
		System.out.println(BeanFactory.class.getSimpleName());*/
		BeanFactory.init();
		UserService bean = BeanFactory.getBean(UserService.class);
		System.out.println(document);
		System.out.println(bean);
	}
}
