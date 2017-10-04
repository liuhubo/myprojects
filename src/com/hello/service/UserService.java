/**
 * 
 */
package com.hello.service;

import com.hello.dao.UserDao;
import com.hello.model.User;

/**
 * @author liuhubo
 *
 */
public class UserService {
	public static User getUserById(Long userId) {
		return UserDao.getUser(userId);
	}
	
	public static void deleteUser(Long userId) {
		UserDao.deleteUser(userId);
	}
}
