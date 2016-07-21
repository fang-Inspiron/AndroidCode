package com.qlf.plants.sort;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

public class SortModel {

	private Drawable img;
	private String name; // 显示的数据
	private String sortLetters; // 显示数据拼音的首字母
	private String genus_name;
	
	

	public String getGenus_name() {
		return genus_name;
	}

	public void setGenus_name(String genus_name) {
		this.genus_name = genus_name;
	}

	public Drawable getImg() {
		return img;
	}

	public void setImg(Drawable img) {
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