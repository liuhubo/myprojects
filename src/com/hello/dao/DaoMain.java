package com.hello.dao;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.hello.model.Tran;
import com.hello.service.TranIface;

public class DaoMain {

	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
		System.out.println(context);
//		BikeDao bikeDao = context.getBean(BikeDao.class);
//		PowerBikeOperationLockRecord params = new PowerBikeOperationLockRecord();
//		List<PowerBikeOperationLockRecord> list = bikeDao.getPowerBikeBatteryChangeRecord(params);
//		System.out.println(list);
		TranIface service = context.getBean(TranIface.class);
		for(int i=0;i<10;i++) {
		Tran tran = new Tran(i,"a");
		service.insertTran(tran);
		}
	}

}
