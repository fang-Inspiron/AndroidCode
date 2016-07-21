package com.bean;

import java.util.List;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

public class Essay extends BmobObject {

	private Integer essayId;
	private String title;
	private String author;
	private String classify;
	private String time;
	private BmobFile themeImg;
	private Integer zanNum;
	private Integer careNum;
	private Integer shareNum;
	private String content;
	private List<BmobFile> listImg;
	private String comments;
	
	public Essay() {
		// TODO Auto-generated constructor stub
		this.setTableName("Essay");
	}
	
	public Integer getEssayId() {
		return essayId;
	}
	public void setEssayId(Integer essayId) {
		this.essayId = essayId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getClassify() {
		return classify;
	}
	public void setClassify(String classify) {
		this.classify = classify;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public BmobFile getThemeImg() {
		return themeImg;
	}
	public void setThemeImg(BmobFile themeImg) {
		this.themeImg = themeImg;
	}
	public Integer getZanNum() {
		return zanNum;
	}
	public void setZanNum(Integer zanNum) {
		this.zanNum = zanNum;
	}
	public Integer getCareNum() {
		return careNum;
	}
	public void setCareNum(Integer careNum) {
		this.careNum = careNum;
	}
	public Integer getShareNum() {
		return shareNum;
	}
	public void setShareNum(Integer shareNum) {
		this.shareNum = shareNum;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public List<BmobFile> getListImg() {
		return listImg;
	}
	public void setListImg(List<BmobFile> listImg) {
		this.listImg = listImg;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}

	@Override
	public String toString() {
		return "Essay [essayId=" + essayId + ", title=" + title + ", author="
				+ author + ", classify=" + classify + ", time=" + time
				+ ", themeImg=" + themeImg + ", zanNum=" + zanNum
				+ ", careNum=" + careNum + ", shareNum=" + shareNum
				+ ", content=" + content + ", listImg=" + listImg
				+ ", comments=" + comments + "]";
	}
	
}
