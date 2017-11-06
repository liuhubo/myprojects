package com.hello.test;
/**
 * java引用传递-传的是引用，共享同一个内存空间
 * 修改的话原始对象也会跟着改
 * @author liuhubo
 *
 */
public class ReferencePass {
	private static class Person {
		private int id;
		private String name;

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		@Override
		public String toString() {
			return "Person [id=" + id + ", name=" + name + "]";
		}
		
	}
	
	public static void main(String[] args) {
		Person p = new Person();
		p.setId(1);
		p.setName("Jack");
		changePerson(p);
		System.out.println(p);
	}
	
	static void changePerson(Person p) {
		p.setId(2);
		p.setName("Rose");
		System.out.println("person changed:" + p);
	}
}
