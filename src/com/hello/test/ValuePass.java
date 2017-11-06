package com.hello.test;
/**
 * java值传递
 * 基本类型传递的只是值的拷贝，不会改变原来的值
 * @author liuhubo
 *
 */
public class ValuePass {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int a = 1;
		changeA(a);
		System.out.println("a:"+a);
		boolean b = false;
		changeB(b);
		System.out.println("b:"+b);
	}

	static void changeA(int a) {
		a = 5;
		System.err.println("a changed:"+a);
	}
	
	static void changeB(boolean b) {
		b = true;
		System.err.println("b changed:"+b);
	}
}
