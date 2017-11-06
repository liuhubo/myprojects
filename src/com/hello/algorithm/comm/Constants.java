/**
 * 
 */
package com.hello.algorithm.comm;

import java.io.IOException;
import java.util.Properties;

/**
 * @author liuhubo
 *
 */
public class Constants {
	private static Properties p=null;
	static{
		p=new Properties();
		try {
			p.load(Constants.class.getResourceAsStream("/cons.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
public static int[] array=new int[]{4,1,3,1,2,1,4,1,2,54,1,34,32,1,23,2,26,1,18,4,16,1,2,17,1,14,15,2,1,4,3,2,11,14,4,2,13,29,10000,2,9999,2,999,888,777,666,6665,5556,4456,5433,23456,6545,3456,3323,4532,4324,2,1234,5677,2,5678,2,1,9878,9877,1,12,13,44,43,1,54,34,1,23,27,1,26,24,1,456,453,1,432,421,1,412,1,411,401,400,666,665,663,664,633};
public static String [] bigString=p.getProperty("array").split(",");
}
