package com.hello.service.impl;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
/**
 * required 加入当前transac,如果没有开启新transac
 *nested作为子transac加入当前transact,并取得savepoint,
<<<<<<< HEAD
 *如果失败则roll back到此savepoint,只能外部transact提交了嵌套的transac才commit
=======
 *如果失败则roll back到此savepoint,只能外部transact提交了嵌套的transac才commit,同样适用于roll bak操作
 *如果嵌套transac异常,外部有二种处理方式，1.内部以及回到savepoint,无脏数据，外部捕获内部exp，并做一些补偿操作,可以用一些特殊场景
 *2.外部不做任何处理，根据自己配置决定commit还是roll bak
 *
>>>>>>> feat:transaction code
 */
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hello.dao.BikeDao;
import com.hello.model.Tran;
import com.hello.service.TranIface;

import scala.concurrent.ExecutionContextExecutorService;
@Service
public class TranService implements TranIface {
	private static AtomicInteger insertCnt=new AtomicInteger(0);
	private static AtomicInteger updateCnt=new AtomicInteger(0);
	private ExecutorService service = new ThreadPoolExecutor(4, 8, 30, TimeUnit.SECONDS, new ArrayBlockingQueue<>(1024),new ThreadFactory() {
		SecurityManager manage = System.getSecurityManager();
		ThreadGroup group = manage!=null?manage.getThreadGroup():Thread.currentThread().getThreadGroup();
		String namePrefix = "pool_"+Thread.currentThread().getId()+"_thread";
		@Override
		public Thread newThread(Runnable r) {
			 Thread t = new Thread(group, r, namePrefix, 0);
			 if(t.isDaemon()) {
				 t.setDaemon(false);
			 }
			 if(t.getPriority()!=Thread.NORM_PRIORITY) {
				 t.setPriority(Thread.NORM_PRIORITY);
			 }
			return t;
		}
	});
	@Autowired
	private BikeDao bikeDao;

	@Transactional(rollbackFor = Throwable.class, propagation = Propagation.NESTED)
	@Override
	public void insertTran(Tran tran) {
		bikeDao.insertTran(tran);
		System.out.println(insertCnt.incrementAndGet() + "insert>>" + tran);
		try {
		updateTran(tran)	;	
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
//		if (tran.getId() == 3) {
//			"".substring(0, 12);
//		}
	}

	@Transactional(rollbackFor = Throwable.class, propagation = Propagation.NESTED)
	@Override
	public void updateTran(Tran tran) {
		try {
		bikeDao.updateTran(tran);
		System.out.println(updateCnt.incrementAndGet()+"update>>>"+tran);
		
		if(tran.getId()>=3) {
			"".substring(0, 2);
		}
		}catch(Exception e) {
			throw new RuntimeException(e);
		}
	}

}
