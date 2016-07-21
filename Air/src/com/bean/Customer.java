package com.bean;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class Customer {
	private String name;
	private String sex;
	private String IDnumber;
	private String phone;

	public Customer() {
	}

	public Customer(String name, String sex, String iDnumber, String phone) {
		super();
		this.name = name;
		this.sex = sex;
		IDnumber = iDnumber;
		this.phone = phone;
	}

	public String toURL() {
		String n = "";
		String s = "";
		try {
			n = URLEncoder.encode(name, "utf-8");
			s = URLEncoder.encode(sex, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "&name=" + n + "&sex=" + s + "&IDnumber=" + IDnumber
				+ "&phone=" + phone;
	}

	@Override
	public String toString() {
		return "Customer [name=" + name + ", sex=" + sex + ", IDnumber="
				+ IDnumber + ", phone=" + phone + "]";
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getIDnumber() {
		return IDnumber;
	}

	public void setIDnumber(String iDnumber) {
		IDnumber = iDnumber;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

}
