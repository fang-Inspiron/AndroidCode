package com.bean;

import java.util.List;
import java.util.Map;

public class GoodsBean {
	private float freight;
	private String goodsAddtime;
	private int goodsCategoryId;
	private String goodsContent;
	private String goodsId;
	private int goodsCount;
	private String goodsImg;
	private String goodsName;
	private float goodsPrice;
	private int goodsSales;
	private String goodsState;
	private int isPeisong;
	private int pageNo;
	private float proChance;
	private int proCount;
	private int proFlag;
	private String proTime;
	private String searchKey;
	private String searchWay;
	private String businessPhone;
	private String keyWords;
	private String userId;
	private List<Map<String, Object>> gmList;
	private int sortWay;
	
	public GoodsBean() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "GoodsBean [freight=" + freight + ", goodsAddtime="
				+ goodsAddtime + ", goodsCategoryId=" + goodsCategoryId
				+ ", goodsContent=" + goodsContent + ", goodsId=" + goodsId
				+ ", goodsImg=" + goodsImg + ", goodsName=" + goodsName
				+ ", goodsPrice=" + goodsPrice + ", goodsSales=" + goodsSales
				+ ", goodsState=" + goodsState + ", isPeisong=" + isPeisong
				+ ", pageNo=" + pageNo + ", proChance=" + proChance
				+ ", proCount=" + proCount + ", proFlag=" + proFlag
				+ ", proTime=" + proTime + ", searchKey=" + searchKey
				+ ", searchWay=" + searchWay + ", businessPhone="
				+ businessPhone + ", keyWords=" + keyWords + ", userId="
				+ userId + ", gmList=" + gmList + ", sortWay=" + sortWay + "]";
	}

	public float getFreight() {
		return freight;
	}

	public void setFreight(float freight) {
		this.freight = freight;
	}

	public int getGoodsCount() {
		return goodsCount;
	}

	public void setGoodsCount(int goodsCount) {
		this.goodsCount = goodsCount;
	}
	
	public void addGoodsCount() {
		goodsCount++;
	}
	
	public void subGoodsCount() {
		if (goodsCount>1) {
			goodsCount--;
		}
	}

	public String getGoodsAddtime() {
		return goodsAddtime;
	}

	public void setGoodsAddtime(String goodsAddtime) {
		this.goodsAddtime = goodsAddtime;
	}

	public int getGoodsCategoryId() {
		return goodsCategoryId;
	}

	public void setGoodsCategoryId(int goodsCategoryId) {
		this.goodsCategoryId = goodsCategoryId;
	}

	public String getGoodsContent() {
		return goodsContent;
	}

	public void setGoodsContent(String goodsContent) {
		this.goodsContent = goodsContent;
	}

	public String getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}

	public String getGoodsImg() {
		return goodsImg;
	}

	public void setGoodsImg(String goodsImg) {
		this.goodsImg = goodsImg;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public float getGoodsPrice() {
		return goodsPrice;
	}

	public void setGoodsPrice(float goodsPrice) {
		this.goodsPrice = goodsPrice;
	}

	public int getGoodsSales() {
		return goodsSales;
	}

	public void setGoodsSales(int goodsSales) {
		this.goodsSales = goodsSales;
	}

	public String getGoodsState() {
		return goodsState;
	}

	public void setGoodsState(String goodsState) {
		this.goodsState = goodsState;
	}

	public int getIsPeisong() {
		return isPeisong;
	}

	public void setIsPeisong(int isPeisong) {
		this.isPeisong = isPeisong;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public float getProChance() {
		return proChance;
	}

	public void setProChance(float proChance) {
		this.proChance = proChance;
	}

	public int getProCount() {
		return proCount;
	}

	public void setProCount(int proCount) {
		this.proCount = proCount;
	}

	public int getProFlag() {
		return proFlag;
	}

	public void setProFlag(int proFlag) {
		this.proFlag = proFlag;
	}

	public String getProTime() {
		return proTime;
	}

	public void setProTime(String proTime) {
		this.proTime = proTime;
	}

	public String getSearchKey() {
		return searchKey;
	}

	public void setSearchKey(String searchKey) {
		this.searchKey = searchKey;
	}

	public String getSearchWay() {
		return searchWay;
	}

	public void setSearchWay(String searchWay) {
		this.searchWay = searchWay;
	}

	public String getBusinessPhone() {
		return businessPhone;
	}

	public void setBusinessPhone(String businessPhone) {
		this.businessPhone = businessPhone;
	}

	public String getKeyWords() {
		return keyWords;
	}

	public void setKeyWords(String keyWords) {
		this.keyWords = keyWords;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public List<Map<String, Object>> getGmList() {
		return gmList;
	}

	public void setGmList(List<Map<String, Object>> gmList) {
		this.gmList = gmList;
	}

	public int getSortWay() {
		return sortWay;
	}

	public void setSortWay(int sortWay) {
		this.sortWay = sortWay;
	}

}
