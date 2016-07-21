package com.bean;

public class Customer {
	private String name;
	private String sex;
	private String IDnumber;
	private String phone;
	
	public Customer() {
		// TODO Auto-generated constructor stub
	}
	
	public Customer(String name, String sex, String iDnumber, String phone) {
		super();
		this.name = name;
		this.sex = sex;
		IDnumber = iDnumber;
		this.phone = phone;
	}

	@Override
	public String toString() {
		return "Customer [name=" + name + ", sex=" + sex + ", IDnumber="
				+ IDnumber + ", phone=" + phone + ", remark=" + "]";
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
