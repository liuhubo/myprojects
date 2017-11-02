package com.hello.function;

import java.io.IOException;
import java.util.Properties;
import java.util.function.Function;
import org.springframework.util.StringUtils;
/**
 * Function<T,R>
 * T->Input of the function[argument]
 * R->result of the funciton
 * Function<T,T>
 * 
 * 
 */
public class PropClient {
	private static Properties prop;
	static {
		prop =  new Properties();
		try {
			prop.load(PropClient.class.getResourceAsStream("pro.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	private static <T> T process(Function<Properties, T> action) {
		
		return action.apply(prop);
	}

	public static String get(String key) {
		return process(prop->prop.getProperty(key));
	}
	
	public static Object set(String key,Object value) {
		return process(prop->prop.put(key, value));
	}
	
	public static Integer getInt(String key) {
		if(key==null||StringUtils.isEmpty(key.trim())) {
			throw new IllegalArgumentException("key:"+key+" invalid");
		}
		String value = process(prop->prop.getProperty(key));
		if(value==null||StringUtils.isEmpty(value.trim())) {
			value = "0";
		}
		return Integer.parseInt(value);
	}
	
	public static Long getLong(String key) {
		if(key==null || StringUtils.isEmpty(key)) {
			throw new IllegalArgumentException("key:"+key+" invalid");
		}
		String value = process(prop->prop.getProperty(key));
		if(value==null || StringUtils.isEmpty(value)) {
			value = "0";
		}
		return Long.parseLong(value);
	}

	public static void main(String arg[]) {
		String driver = PropClient.get("jdbc.driver.class");
		System.out.println(driver);
	}
}
