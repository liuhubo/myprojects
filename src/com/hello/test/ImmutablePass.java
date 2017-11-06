package com.hello.test;
/**
 * Immutable 类传递可以认为是值传递，不会对原有状态有所改变
 * 
 * @author liuhubo
 *
 */
public class ImmutablePass {

	public static void main(String[] args) {
		String name = "Jack";
		changeName(name);
		System.out.println(name);
		Integer b = 20;
		changeB(b);
		System.out.println(b);
	}

	static void changeName(String name) {
		name = "rose";
		System.out.println("name changed:"+name);
	}
	
	static void changeB(Integer b) {
		b = 100;
		System.out.println("b changed:"+b);
	}
}
