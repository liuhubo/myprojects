package com.hello.test;

import com.hello.dao.DB;
import com.hello.dao.UserDao;
import com.hello.model.User;

public class TestMain {

	public static void main(String[] args) {
		System.out.println("user list zise:"+DB.users.size());
//		UserDao.addUser(new User(1L, "Jay", 30L, 1));
		System.out.println("after add user list zise:"+DB.users.size());
	}

}
