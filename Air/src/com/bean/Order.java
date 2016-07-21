package com.bean;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class Order {
	private String orderNum;
	private String IDnumber;
	private String flightNum;
	private int ticketNum;
	private String purchaseDate;
	private String conductor;

	public Order() {
		// TODO Auto-generated constructor stub
	}

	public Order(String orderNum, String iDnumber, String flightNum,
			int ticketNum, String purchaseDate, String conductor) {
		super();
		this.orderNum = orderNum;
		IDnumber = iDnumber;
		this.flightNum = flightNum;
		this.ticketNum = ticketNum;
		this.purchaseDate = purchaseDate;
		this.conductor = conductor;
	}

	public String toURL() {
		String u="";
		try {
			u = URLEncoder.encode(conductor, "utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "&orderNum=" + orderNum + "&IDnumber=" + IDnumber
				+ "&flightNum=" + flightNum + "&ticketNum=" + ticketNum
				+ "&purchaseDate=" + purchaseDate + "&conductor=" + u;
	}
	
	@Override
	public String toString() {
		return "Order [orderNum=" + orderNum + ", IDnumber=" + IDnumber
				+ ", flightNum=" + flightNum + ", ticketNum=" + ticketNum
				+ ", purchaseDate=" + purchaseDate + ", conductor=" + conductor
				+ "]";
	}

	public String getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}

	public String getIDnumber() {
		return IDnumber;
	}

	public void setIDnumber(String iDnumber) {
		IDnumber = iDnumber;
	}

	public String getFlightNum() {
		return flightNum;
	}

	public void setFlightNum(String flightNum) {
		this.flightNum = flightNum;
	}

	public int getTicketNum() {
		return ticketNum;
	}

	public void setTicketNum(int ticketNum) {
		this.ticketNum = ticketNum;
	}

	public String getPurchaseDate() {
		return purchaseDate;
	}

	public void setPurchaseDate(String purchaseDate) {
		this.purchaseDate = purchaseDate;
	}

	public String getConductor() {
		return conductor;
	}

	public void setConductor(String conductor) {
		this.conductor = conductor;
	}

}
