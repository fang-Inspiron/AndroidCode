package com.bean;

public class Flight {
	private String flightNum;
	private String leaveCity;
	private String arriveCity;
	private String leaveDate;
	private int price;
	
	public Flight() {
		// TODO Auto-generated constructor stub
	}

	public Flight(String flightNum, String leaveCity, String arriveCity,
			String leaveDate, int price) {
		super();
		this.flightNum = flightNum;
		this.leaveCity = leaveCity;
		this.arriveCity = arriveCity;
		this.leaveDate = leaveDate;
		this.price = price;
	}

	@Override
	public String toString() {
		return "Flight [flightNum=" + flightNum + ", leaveCity=" + leaveCity
				+ ", arriveCity=" + arriveCity + ", leaveDate=" + leaveDate
				+ ", price=" + price + ", remark=" +  "]";
	}

	public String getFlightNum() {
		return flightNum;
	}

	public void setFlightNum(String flightNum) {
		this.flightNum = flightNum;
	}

	public String getLeaveCity() {
		return leaveCity;
	}

	public void setLeaveCity(String leaveCity) {
		this.leaveCity = leaveCity;
	}

	public String getArriveCity() {
		return arriveCity;
	}

	public void setArriveCity(String arriveCity) {
		this.arriveCity = arriveCity;
	}

	public String getLeaveDate() {
		return leaveDate;
	}

	public void setLeaveDate(String leaveDate) {
		this.leaveDate = leaveDate;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

}
