package com.group.redis;

import java.util.Set;

import redis.clients.jedis.Jedis;

public class RedisTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
//		System.out.println(JedisUtil.isJedisConn());
		JedisUtil.set("name", "Jay");
		System.out.println("key-name|value jay>>"+JedisUtil.get("name"));
		JedisUtil.set("name", "Liuhubo");
		System.out.println("key-name|value Liuhubo>>"+JedisUtil.get("name"));
		JedisUtil.trimAll("nameList");
		JedisUtil.lpush("nameList", "Jay");
		JedisUtil.lpush("nameList", "Rose");
		JedisUtil.lpush("nameList", "Mike");
		JedisUtil.lpush("nameList", "Bill");
		System.out.println("remove name before:"+JedisUtil.lrange("nameList"));
		System.out.println(JedisUtil.getPopValue("nameList"));
		System.out.println(JedisUtil.getPopValue("nameList"));
		System.out.println(JedisUtil.getPopValue("nameList"));
		System.out.println(JedisUtil.getPopValue("nameList"));
		System.out.println("remove name after:"+JedisUtil.lrange("nameList"));
		JedisUtil.delete("mutex");
		System.out.println(JedisUtil.add("mutex", "mutex"));
		System.out.println(JedisUtil.add("mutex", "mutex"));
		System.out.println(JedisUtil.add("mutex", "mutex"));
		JedisUtil.delete("mutex");
		System.out.println(JedisUtil.add("mutex", "mutex"));
		System.out.println(JedisUtil.isJedisConn());
	}

}
