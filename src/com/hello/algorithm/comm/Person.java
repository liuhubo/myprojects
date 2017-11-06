/**
 * 
 */
package com.hello.algorithm.comm;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * @author liuhubo
 *
 */
public class Person implements Cloneable,Serializable{
private Long id;
private String name;
private Integer age;
private int gender;
private Human p;
public Person(Long id, String name, Integer age, int gender,Human p) {
	this.id = id;
	this.name = name;
	this.age = age;
	this.gender = gender;
	this.p=p;
}
public Long getId() {
	return id;
}
public void setId(Long id) {
	this.id = id;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public Integer getAge() {
	return age;
}
public void setAge(Integer age) {
	this.age = age;
}
public int getGender() {
	return gender;
}
public void setGender(int gender) {
	this.gender = gender;
}

public Human getP() {
	return p;
}
public void setP(Human p) {
	this.p = p;
}
@Override
public String toString() {
	return "Person [id=" + id + ", name=" + name + ", age=" + age + ", gender="
			+ gender +", person="+p+ "]";
}

/*@Override
public Person clone() throws CloneNotSupportedException{
	Person cloneP=(Person) super.clone();
	return cloneP;
}*/ //浅克隆，只克隆基本类型的值，引用类型只是简单的拷贝，引用类型变量与克隆前是同一个引用
@Override
public Person clone(){//序列化的对象都是拷贝的对象，原始对象仍然在jvm里，利用序列化完成对复杂对象的深拷贝
	try {
		ByteArrayOutputStream  bo=new ByteArrayOutputStream();
		ObjectOutputStream os=new ObjectOutputStream(bo);
		os.writeObject(this);
		ByteArrayInputStream bi=new ByteArrayInputStream(bo.toByteArray());
		ObjectInputStream oi=new ObjectInputStream(bi);
		Person cloneP=(Person) oi.readObject();
		return cloneP;
	} catch (IOException e) {
		e.printStackTrace();
	} catch (ClassNotFoundException e) {
		e.printStackTrace();
		return null;
	}
	return null;
}
}
