package com.qlf.plants.bean;

public class PosBean {

	private String terminalName;
	private int terminalId;
	private String sim;
	private String address;
	
	public PosBean() {
		// TODO Auto-generated constructor stub
	}
	
	public PosBean(String terminalName, int terminalId, String sim,
			String address) {
		super();
		this.terminalName = terminalName;
		this.terminalId = terminalId;
		this.sim = sim;
		this.address = address;
	}

	public String getTerminalName() {
		return terminalName;
	}
	public void setTerminalName(String terminalName) {
		this.terminalName = terminalName;
	}
	public int getTerminalId() {
		return terminalId;
	}
	public void setTerminalId(int terminalId) {
		this.terminalId = terminalId;
	}
	public String getSim() {
		return sim;
	}
	public void setSim(String sim) {
		this.sim = sim;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	
}
