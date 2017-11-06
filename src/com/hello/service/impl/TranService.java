package com.hello.service.impl;

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

	@Transactional(rollbackFor = Throwable.class, propagation = Propagation.REQUIRED)
	@Override
	public void insertTran(Tran tran) {
	    String s ="";
		for(int i=0;i<10;i++) {
			bikeDao.insertTran(tran);
			System.out.println(insertCnt.incrementAndGet()+"insert>>"+tran);
			updateTran(tran);
			if(i==3) {
				s=s.substring(0, 12);
			}
		}
	}

	@Transactional(rollbackFor = Throwable.class, propagation = Propagation.REQUIRED)
	@Override
	public void updateTran(Tran tran) {
		bikeDao.updateTran(tran);
		System.out.println(updateCnt.incrementAndGet()+"update>>>"+tran);
	}

}
