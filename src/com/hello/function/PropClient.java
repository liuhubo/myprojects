package com.hello.function;

import java.io.IOException;
import java.util.Properties;
import java.util.function.Function;
/**
 * Function<T,R>
 * T->Input of the function[argument]
 * R->result of the funciton
 * Function<T,T>
 * 
 * 
 */
public class PropClient {
	private static <T> T process(Function<Properties, T> action) {
		Properties prop = new Properties();
		try {
			prop.load(PropClient.class.getResourceAsStream("pro.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return action.apply(prop);
	}

	public static String get(String key) {
		return process(prop->prop.getProperty(key));
	}
	
	public static Object set(String key,Object value) {
		return process(prop->prop.put(key, value));
	}
	
	public static void main(String arg[]) {
		String driver = PropClient.get("jdbc.driver.class");
		System.out.println(driver);
	}
}
