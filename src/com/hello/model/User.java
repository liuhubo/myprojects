/**
 * 
 */
package com.hello.model;

/**
 * @author liuhubo
 *
 */
public class User {
private Long userId;
private String userName;
private Long age;
private Integer gender;

public User(Long userId, String userName, Long age, Integer gender) {
	super();
	this.userId = userId;
	this.userName = userName;
	this.age = age;
	this.gender = gender;
}

public User() {
}

public Long getUserId() {
	return userId;
}
public void setUserId(Long userId) {
	this.userId = userId;
}
public String getUserName() {
	return userName;
}
public void setUserName(String userName) {
	this.userName = userName;
}
public Long getAge() {
	return age;
}
public void setAge(Long age) {
	this.age = age;
}
public Integer getGender() {
	return gender;
}
public void setGender(Integer gender) {
	this.gender = gender;
}

@Override
public String toString() {
	return "User [userId=" + userId + ", userName=" + userName + ", age=" + age + ", gender=" + gender + "]";
}
}
