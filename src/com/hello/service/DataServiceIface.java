package com.hello.service;

public interface DataServiceIface {
public void set(String k,String v);
public String get(String k);	
public String del(String k);
public void set(String k,String v,long timeout);
public void Rpublish(String channel,String msg);
}
