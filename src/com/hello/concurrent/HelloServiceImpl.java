package com.hello.concurrent;

public class HelloServiceImpl implements HelloService{
private static int count=0;
	@Override
	public Integer print() {
		System.out.println("------------"+(count++)+"----------");
		return count;
	}

}
