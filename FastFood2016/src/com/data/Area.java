package com.data;

public class Area {
	private String currCity;//当前城市
	private String currAreaId;//当前区域Id
	private String currAreaName;//当前区域名称
	private String complainPhone;//投诉电话
	private String des;//订餐说明
	private String orderConsumeTime;//订餐时间
	public String getCurrAreaId() {
		return currAreaId;
	}
	public void setCurrAreaId(String currAreaId) {
		this.currAreaId = currAreaId;
	}
	public String getCurrAreaName() {
		return currAreaName;
	}
	public void setCurrAreaName(String currAreaName) {
		this.currAreaName = currAreaName;
	}
	public String getComplainPhone() {
		return complainPhone;
	}
	public void setComplainPhone(String complainPhone) {
		this.complainPhone = complainPhone;
	}
	public String getDes() {
		return des;
	}
	public void setDes(String des) {
		this.des = des;
	}
	public String getOrderConsumeTime() {
		return orderConsumeTime;
	}
	public void setOrderConsumeTime(String orderConsumeTime) {
		this.orderConsumeTime = orderConsumeTime;
	}
	public String getCurrCity() {
		return currCity;
	}
	public void setCurrCity(String currCity) {
		this.currCity = currCity;
	}
	
}
