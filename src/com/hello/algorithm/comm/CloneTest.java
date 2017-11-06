package com.hello.algorithm.comm;

public class CloneTest {

	/**
	 * @param args
	 * 有些场景需要保留原始对象的状态，需要用到深克隆
	 */
	public static void main(String[] args) throws CloneNotSupportedException {
		Human h=new Human("China","Asia");
		Person p = new Person(1L, "Jack", 24, 1,h);
		Person p1 = null;
		p1 = p.clone();
		System.out.println("p:"+p1);
		System.out.println("p1:"+p1);
		System.out.println("P HashCode:" + p.hashCode());
		System.out.println("P1 HashCode:" + p1.hashCode());
		System.out.println("p==p1:" + (p == p1));
		System.out.println("P.human HashCode:"+p.getP().hashCode());
		System.out.println("P1.human HashCode:"+p1.getP().hashCode());
	}

}
