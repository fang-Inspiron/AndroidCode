package com.bean;

public class User {
	private String user;
	private String password;
	private int permission;
	
	public User() {
		// TODO Auto-generated constructor stub
	}

	public User(String user, String password, int permission) {
		super();
		this.user = user;
		this.password = password;
		this.permission = permission;
	}

	@Override
	public String toString() {
		return "User [user=" + user + ", password=" + password
				+ ", permission=" + permission + "]";
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getPermission() {
		return permission;
	}

	public void setPermission(int permission) {
		this.permission = permission;
	}
	
}
