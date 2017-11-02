package com.hello.util;

import org.springframework.stereotype.Component;

import redis.clients.jedis.JedisPubSub;
@Component
public class RmsgPubSubListener extends JedisPubSub{

	public void onMessage(String channel, String message) {
		System.out.println("redis receive a msg:"+message);
	}

	public void subscribe(String... channels) {
		super.subscribe(channels);
	}
}
