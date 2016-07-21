package com.qlf.plants.bean;

import android.graphics.Bitmap;

public class SortModel {

	private Bitmap img;
	private String name; // 显示的数据
	private String sortLetters; // 显示数据拼音的首字母

	public Bitmap getImg() {
		return img;
	}

	public void setImg(Bitmap img) {
		this.img = img;
	}

	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSortLetters() {
		return sortLetters;
	}

	public void setSortLetters(String sortLetters) {
		this.sortLetters = sortLetters;
	}
}