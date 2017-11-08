/**
 * 
 */
package com.group.concurrent;

import java.util.concurrent.BlockingQueue;

/**
 * @author liuhubo
 * 
 */
public class Producer implements Runnable {
	private BlockingQueue queue;
    private int i=1;
    public Producer(BlockingQueue queue) {
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
				System.out.println("producer add up to:"+i);
				queue.add(i);
				Thread.sleep(1000);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
