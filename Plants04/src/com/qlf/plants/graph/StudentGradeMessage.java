package com.qlf.plants.graph;

public class StudentGradeMessage {
	
	private String name;
	private double chinese;
	private double english;
	private double math;
	private double total;
	private int numTotal;
	private int numChinese;
	private int numMath;
	private int numEnglish;
	public StudentGradeMessage() {
		super();
	}
	public double getChinese() {
		return chinese;
	}
	public void setChinese(double chinese) {
		this.chinese = chinese;
	}
	public double getEnglish() {
		return english;
	}
	public void setEnglish(double english) {
		this.english = english;
	}
	public double getMath() {
		return math;
	}
	public void setMath(double math) {
		this.math = math;
	}
	public double getTotal() {
		return total;
	}
	public void setTotal(double total) {
		this.total = total;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getNumTotal() {
		return numTotal;
	}
	public void setNumTotal(int numTotal) {
		this.numTotal = numTotal;
	}
	public int getNumChinese() {
		return numChinese;
	}
	public void setNumChinese(int numChinese) {
		this.numChinese = numChinese;
	}
	public int getNumMath() {
		return numMath;
	}
	public void setNumMath(int numMath) {
		this.numMath = numMath;
	}
	public int getNumEnglish() {
		return numEnglish;
	}
	public void setNumEnglish(int numEnglish) {
		this.numEnglish = numEnglish;
	}
	
}
