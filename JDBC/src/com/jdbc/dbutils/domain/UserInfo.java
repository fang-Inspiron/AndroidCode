package com.jdbc.dbutils.domain;

import java.io.Serializable;

public class UserInfo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int id;
	private String username;
	private String pswd;

	@Override
	public String toString() {
		return "UserInfo [id=" + id + ", username=" + username + ", pswd="
				+ pswd + "]";
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPswd() {
		return pswd;
	}

	public void setPswd(String pswd) {
		this.pswd = pswd;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	public UserInfo() {
		// TODO Auto-generated constructor stub
	}

}
