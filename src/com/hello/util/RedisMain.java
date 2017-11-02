package com.hello.util;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.hello.service.DataServiceIface;

public class RedisMain {

	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
		DataServiceIface service = context.getBean(DataServiceIface.class);
		service.set("batteryCapacity", "100%");
		System.out.println(service.get("batteryCapacity"));
	}

}