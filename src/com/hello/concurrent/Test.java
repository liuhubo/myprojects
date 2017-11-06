/**
 * 
 */
package com.hello.concurrent;

import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @author liuhubo
 *
 */
public class Test {
	public static void main(String[] args) throws InterruptedException, ExecutionException, TimeoutException {
    final CountDownLatch latch=new CountDownLatch(3);		
    final HelloService service=new HelloServiceImpl();
    ExecutorService exe=Executors.newFixedThreadPool(3);
    Callable<Integer> service1=new AbstractService<Integer>(latch) {
		@Override
		public Integer execute() throws Exception {
			int i=service.print();
			latch.countDown();
			return i;
		}
	};
	Callable<Integer> service2=new AbstractService<Integer>(latch) {
		@Override
		public Integer execute() throws Exception {
			int i=service.print();
			latch.countDown();
			return i;
		}
	};
	Callable<Integer> service3=new AbstractService<Integer>(latch) {
		@Override
		public Integer execute() throws Exception {
			int i=service.print();
			latch.countDown();
			return i;
		}
	};
	Future<Integer> future1=exe.submit(service1);
	Future<Integer> future2=exe.submit(service2);
	Future<Integer> future3=exe.submit(service3);
	future1.get(1,TimeUnit.SECONDS);//get本身也是阻塞 和await效果一样
	future2.get(1,TimeUnit.SECONDS);
	future3.get(1,TimeUnit.SECONDS);
	exe.shutdown();
	//latch.await();//阻塞主线程
	System.out.println("main thread game over...");
	} 

	static int tryMethod(){
		try{
			System.out.println("Line 0");
			return 1/0;
		}catch(Exception e){
			System.out.println("Line 1");
		}finally{
			System.out.println("Line 2");
		}
		return 4;
	}
}
