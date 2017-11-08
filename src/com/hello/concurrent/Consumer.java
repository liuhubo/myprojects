/**
 * 
 */
package com.group.concurrent;

import java.util.concurrent.BlockingQueue;

/**
 * @author liuhubo
 * 
 */
public class Consumer implements Runnable {
	private BlockingQueue queue;
    private int i=1;
    public Consumer(BlockingQueue queue) {
		this.queue = queue;
	}

	public BlockingQueue getQueue() {
		return queue;
	}

	public void setQueue(BlockingQueue queue) {
		this.queue = queue;
	}

	public void run() {
		try {
			for(;i<100;i++){
				System.out.println("consumer take element:"+queue.take());
				Thread.sleep(1000);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
