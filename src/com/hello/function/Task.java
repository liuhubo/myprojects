package com.hello.function;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicLong;

public class Task<T> implements Callable<T>{
	private Long taskId;
	private Long taskInterval;
	private Long nextTime;
	private boolean isDone = false;
	private boolean isSucc=false;
	private Object proxyClazz;
	private String method;
	private Object params;
	private Object returnValue;
	private T data;
	private Integer sleepTime;
	private String [] paramsType = null;
	@Override
	public T call() throws Exception {
		if (isDone) {
			System.out.println("previous done,will not execute again!");
			return this.data;
		}
		while (!isDone) {
			Long now = System.currentTimeMillis();
			if (now >= nextTime) {
				System.out.println("Run time now:"+now);
				Method [] ms = proxyClazz.getClass().getMethods();
				Method method = null;
				for(Method m:ms) {
					if(m.getName().equals(this.method)) {
						method=m;
						Class [] cc= method.getParameterTypes();;
						paramsType = new String[cc.length];
						for(int i=0;i<cc.length;i++) {
							try {
								paramsType[i] = Class.forName(cc[i].getName()).getName();
							} catch (ClassNotFoundException e) {
								e.printStackTrace();
							}
						}
						List<String> list = Arrays.asList(paramsType);
						if(!list.contains(params.getClass().getName())) {
							throw new Exception("参数类型不匹配!接口类型:"+list+"传入类型:"+params.getClass().getName());
						}
						break;
					}
				}
				try {
					Object returnObj = method.invoke(proxyClazz, params);
					this.isSucc=true;
					this.returnValue = returnObj;
					this.setData((T)returnObj);
				} catch (IllegalAccessException e) {
					e.printStackTrace();
					this.isSucc=false;
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
					this.isSucc=false;
				} catch (InvocationTargetException e) {
					e.printStackTrace();
					this.isSucc=false;
				}finally {
					this.isDone=true;
					System.out.println(">>Is Done:"+isDone);
					System.out.println(">>Done Result:"+isSucc);
				}
			}else {
				try {
					long ss=taskInterval/sleepTime;
					System.out.println("too early to execute req,slepp:"+ss+"ms");
					Thread.currentThread().sleep(ss);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
		}
		return this.getData();
	}
	public Long getTaskId() {
		return taskId;
	}
	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}
	public Long getTaskInterval() {
		return taskInterval;
	}
	public void setTaskInterval(Long taskInterval) {
		this.taskInterval = taskInterval;
	}
	public Long getNextTime() {
		return nextTime;
	}
	public void setNextTime(Long nextTime) {
		this.nextTime = nextTime;
	}
	public boolean isDone() {
		return isDone;
	}
	public void setDone(boolean isDone) {
		this.isDone = isDone;
	}
	public Object getProxyClazz() {
		return proxyClazz;
	}
	public void setProxyClazz(Object proxyClazz) {
		this.proxyClazz = proxyClazz;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public Object getParams() {
		return params;
	}
	public void setParams(Object params) {
		this.params = params;
	}
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}
	public Integer getSleepTime() {
		return sleepTime;
	}
	public void setSleepTime(Integer sleepTime) {
		this.sleepTime = sleepTime;
	}
	public boolean isSucc() {
		return isSucc;
	}
	public void setSucc(boolean isSucc) {
		this.isSucc = isSucc;
	}
	public String[] getParamsType() {
		return paramsType;
	}
	public void setParamsType(String[] paramsType) {
		this.paramsType = paramsType;
	}

}
