package com.hello.model;

public class Man {
private Long id;
private String name;
private Long age;
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
@Override
public String toString() {
	return "Man [id=" + id + ", name=" + name + ", age=" + age + "]";
}

}