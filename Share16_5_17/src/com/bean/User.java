package com.bean;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;

public class User  extends BmobUser{
	
	private String userId;
	private String classify;
	private BmobFile headImg;
	private String signature;
	private String sex;
	
	public User() {
		// TODO Auto-generated constructor stub
		this.setTableName("user");
	}
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getClassify() {
		return classify;
	}
	public void setClassify(String classify) {
		this.classify = classify;
	}
	public BmobFile getHeadImg() {
		return headImg;
	}
	public void setHeadImg(BmobFile headImg) {
		this.headImg = headImg;
	}
	public String getSignature() {
		return signature;
	}
	public void setSignature(String signature) {
		this.signature = signature;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	
}
