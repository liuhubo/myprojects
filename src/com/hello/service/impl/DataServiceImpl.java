package com.hello.service.impl;

import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hello.service.DataServiceIface;
import com.hello.util.RedisClient;

@Service
public class DataServiceImpl implements DataServiceIface {
	@Autowired
	private RedisClient redicClient;

	@Override
	public void set(String k, String v) {
		exec(redicClient -> redicClient.set(k, v));
	}

	@Override
	public String get(String k) {
		return exec(redicClient -> redicClient.get(k));
	}

	@Override
	public String del(String k) {
		return exec(redicClient->redicClient.del(k));
	}
	
	public String exec(Function<RedisClient, String> action) {
		return action.apply(redicClient);
	}

	@Override
	public void set(String k, String v, long timeout) {
		exec(redicClient->redicClient.set(k, v, timeout));
	}

	@Override
	public void Rpublish(String channel,String msg) {
		exec(redicClient->redicClient.rPubMsg(channel, msg));
	}

}
