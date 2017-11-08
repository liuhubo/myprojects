package com.group.concurrent;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
/**
 * ArrayBlockingQueue 是一个有界的阻塞队列，将对象放入数组里，是一个FIFO的存储队列
 * LinkedBlockingQueue 是一个无界链式结构的FIFO队列,队列最大 Integer.MAX_VALUE
 * @author liuhubo
 *
 */
public class BlockQueueTest {

	/**
	 * @param args
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws InterruptedException {
		final BlockingQueue queue ;//new ArrayBlockingQueue(1000);
//		Producer producer=new Producer(queue);
//		Consumer consumer=new Consumer(queue);
//		new Thread(producer).start();
//		new Thread(consumer).start();
		queue=new LinkedBlockingQueue();
		queue.put("liuhubo");
		queue.put("liujuan");
		queue.put("simayi");
		queue.put("guanyu");
		while(queue.size()>0){
			System.out.println(queue.take());
		}
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable(){
			public void run() {
				System.out.println("queue size:"+queue.size());
			}
		}));
	}

}
