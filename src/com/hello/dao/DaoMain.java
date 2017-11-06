package com.hello.dao;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.hello.model.PowerBikeOperationLockRecord;

public class DaoMain {

	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
		System.out.println(context);
//		BikeDao bikeDao = context.getBean(BikeDao.class);
//		PowerBikeOperationLockRecord params = new PowerBikeOperationLockRecord();
//		List<PowerBikeOperationLockRecord> list = bikeDao.getPowerBikeBatteryChangeRecord(params);
//		System.out.println(list);
	}

}
