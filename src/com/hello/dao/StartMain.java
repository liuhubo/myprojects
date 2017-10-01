package com.hello.dao;

import com.hello.model.User;

public class StartMain {

	public static void main(String[] args) {
		DB.users.add(new User(1L,"Jay",29L,1));
		System.out.println("Current Thread:"+Thread.currentThread().toString());
		System.out.println("Initialize size:"+DB.users.size());
		while(true) {
			Thread t1 = new Thread(new Runnable() {
				@Override
				public void run() {
					UserDao.addUser(new User(1L,"Jay",28L,1));	
				}
			});
			Thread t2= new Thread(new Runnable() {
				@Override
				public void run() {
					UserDao.deleteUser(1l);	
				}
			});
			System.out.println("Current user size:"+DB.users.size());
			t1.start();
			try {
				Thread.sleep(5000);
				System.out.println(">>> size:"+DB.users.size());
				t2.start();
				Thread.sleep(5000);
				
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
