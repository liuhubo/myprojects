package com.hello.model;

public class Tran {
	private int id;
	private String detail;

	public Tran(int id, String detail) {
		this.id = id;
		this.detail = detail;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	@Override
	public String toString() {
		return "Tran [id=" + id + ", detail=" + detail + "]";
	}
	
}
