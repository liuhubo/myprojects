package com.hello.util;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.hello.service.DataServiceIface;

public class RedisMain {

	public static void main(String[] args) throws InterruptedException {
		ApplicationContext context = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
		DataServiceIface service = context.getBean(DataServiceIface.class);
		service.set("batteryCapacity", "100%");
		System.out.println(service.get("batteryCapacity"));
		RedisClient redisClient = context.getBean(RedisClient.class);
//		service.Rpublish("powerBikeBattery:3710000", "电压低");
//		RmsgPubSubListener lst = new RmsgPubSubListener();
//		redisClient.rPubMsg("powerBikeBattery:www", "aaa");
		String redisKey = "Bike_001";
		writeToQueue(redisClient, redisKey, "Jay");
		writeToQueue(redisClient, redisKey, "Jack");
		writeToQueue(redisClient, redisKey, "Mike");
		writeToQueue(redisClient, redisKey, "Joy");
		writeToQueue(redisClient, redisKey, "Nicole");
		writeToQueue(redisClient, redisKey, "Keven");
		popByFIFO(redisClient, redisKey);
		popByFIFO(redisClient, redisKey);
		popByFIFO(redisClient, redisKey);
		popByFIFO(redisClient, redisKey);
		popByFIFO(redisClient, redisKey);
		popByFIFO(redisClient, redisKey);
		popByFIFO(redisClient, redisKey);
//		while(true) {
//			accessLimit(redisClient, redisKey);
//			Thread.currentThread().sleep(10000L);
//		}
	}

	/**
	 * Redis实现限速,限流
	 * 此方法 一分钟限制三次请求
	 * @param redis
	 * @param key
	 */
	public static void accessLimit(RedisClient redis,String key) {
		long ret = 0;
		ret = redis.incrAndSetTimeout(key, 60);
		if (ret > 3) {
			System.out.println("out of limt,refused!");
			return;
		}
		System.out.println("To Do The Business Logic");
	}
	
	public static void writeToQueue(RedisClient redis,String key,String name) {
		redis.lPush(key, name);
		System.out.println("left push:"+name);
	}
	
	/**
	 * 先进先出队列
	 * @param redis
	 * @param key
	 */
	public static void popByFIFO(RedisClient redis,String key) {
		String popEle = redis.rPop(key);
		System.out.println("pop ele:"+popEle);
	}
}
