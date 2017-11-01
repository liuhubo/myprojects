package com.hello.function;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

public class TaskMain {
	static ExecutorService threadPool = null;

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		PowerService service = new PowerServiceImpl();
		Object proxy = Proxy.newProxyInstance(service.getClass().getClassLoader(), service.getClass().getInterfaces(), new InvocationHandler() {
			@Override
			public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
				Object obj = method.invoke(service, args);
				return obj;
			}
		});
		System.out.println("开始时间:" + System.currentTimeMillis());
		Task<String> task = new Task<>();
		task.setTaskId(00001L);
		task.setProxyClazz(proxy);
		task.setMethod("getPowerBikeElectr");
		task.setParams(92L);
		Long now = System.currentTimeMillis();
		task.setNextTime(now + 12000L);
		task.setTaskInterval(12000L);
		task.setSleepTime(2);
		Future<String> fu = asyncExecute(threadPool -> threadPool.submit(task));
		while (!fu.isDone()) {
			Thread.currentThread().sleep(100);
		}
		System.out.println("future result:" + fu.get()+"Task Data:"+task.getData() + "|结束时间:" + System.currentTimeMillis());
		shutPool(threadPool -> threadPool.shutdownNow());
	}

	public static Future<String> asyncExecute(Function<ExecutorService, Future<String>> action) {
		if (threadPool == null) {
			threadPool = new ThreadPoolExecutor(2, 16, 15, TimeUnit.MINUTES, new ArrayBlockingQueue<Runnable>(1024), new RejectedExecutionHandler() {
				@Override
				public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
					throw new RejectedExecutionException("Task:" + r.toString() + " was rejected by " + executor.toString());
				}
			});
		}
		return action.apply(threadPool);
	}
	
	public static List<Runnable> shutPool(Function<ExecutorService,List<Runnable>> action) {
		if(threadPool!=null&&!threadPool.isTerminated()) {
			return action.apply(threadPool);
		}
		return null;
	}
	
}
