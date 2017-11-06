package com.hello.service.impl;

/**
 * required 加入当前transac,如果没有开启新transac
 *nested作为子transac加入当前transact,并取得savepoint,
 *如果失败则roll back到此savepoint,只能外部transact提交了嵌套的transac才commit
 */
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hello.dao.BikeDao;
import com.hello.model.Tran;
import com.hello.service.TranIface;
@Service
public class TranService implements TranIface {
	private static AtomicInteger insertCnt=new AtomicInteger(0);
	private static AtomicInteger updateCnt=new AtomicInteger(0);
	@Autowired
	private BikeDao bikeDao;

	@Transactional(rollbackFor = Throwable.class, propagation = Propagation.NESTED)
	@Override
	public void insertTran(Tran tran) {
		bikeDao.insertTran(tran);
		System.out.println(insertCnt.incrementAndGet() + "insert>>" + tran);
		updateTran(tran);
//		if (tran.getId() == 3) {
//			"".substring(0, 12);
//		}
	}

//	@Transactional(rollbackFor = Throwable.class, propagation = Propagation.REQUIRED)
	@Override
	public void updateTran(Tran tran) {
		bikeDao.updateTran(tran);
		System.out.println(updateCnt.incrementAndGet()+"update>>>"+tran);
		
		if(tran.getId()>=3) {
			"".substring(0, 2);
		}
	}

}
