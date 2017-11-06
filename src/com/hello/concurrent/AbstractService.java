package com.hello.concurrent;

import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;

public abstract class AbstractService<T> implements Callable<T>{
private CountDownLatch latch;
	public AbstractService(CountDownLatch latch) {
	super();
	this.latch = latch;
}

	public T call(){
		try {
			return execute();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public abstract T execute() throws Exception;

	public CountDownLatch getLatch() {
		return latch;
	}

	public void setLatch(CountDownLatch latch) {
		this.latch = latch;
	}
		
}
