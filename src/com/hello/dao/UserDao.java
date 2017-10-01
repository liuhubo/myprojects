/**
 * 
 */
package com.hello.dao;

import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import com.hello.model.User;

/**
 * @author liuhubo
 *
 */
public class UserDao {

	public static synchronized void addUser(User u) {
		if (u == null) {
			return;
		}
		DB.users.add(u);
		System.out.println("Thread "+Thread.currentThread()+" insert into Users 1 row :" + u);
	}
	
	public static User getUser(Long userId) {
			for(User u:DB.users) {
				if(u.getUserId().equals(userId)) {
					System.out.println("db hit user id:"+userId+"|"+u);
					return u;
				}
			}
			return null;
	}

	public static List<User> getUsers() {
		return DB.users;
	}
	
	public static synchronized void deleteUser(Long userId) {
		Iterator<User> it = DB.users.iterator();
		while(it.hasNext()) {
			User u = it.next();
				if(u.getUserId().equals(userId)) {
					it.remove();
					System.out.println("Thread "+Thread.currentThread()+" remove user:"+u);
				}
		}
	}
	
	public static synchronized void updateUser(User u) {
		ListIterator<User> it = DB.users.listIterator();
		while(it.hasNext()) {
			User user = it.next();
			if(u.getUserId().equals(user.getUserId())) {
				it.remove();
				it.add(u);
			}
		}
		
		
		DB.users.forEach(user->{
		
			
		});
	}
}
