package com.fastfood.thread;

import java.util.HashMap;
import java.util.Map;

import com.fastfood.utils.HttpUtil;
import com.fastfood.utils.JsonUtil;

import android.os.Handler;
import android.os.Message;

public class GetGoodsListThread extends Thread {

	public static final int SUCCESS = 701;
	public static final int NETWORK_ERROR = 700;
	
	private Handler handler;
	private int goodsCategoryId;
	private String userId;
	private String searchWay;
	private int areaId;
	private String searchKey;
	private int sortWay;
	private int pageNo;
	
	public GetGoodsListThread(Handler handler, int goodsCategoryId,
			String userId, String searchWay, int areaId, String searchKey,
			int sortWay, int pageNo) {
		super();
		this.handler = handler;
		this.goodsCategoryId = goodsCategoryId;
		this.userId = userId;
		this.searchWay = searchWay;
		this.areaId = areaId;
		this.searchKey = searchKey;
		this.sortWay = sortWay;
		this.pageNo = pageNo;
	}
	
	@Override
	public void run() {
		Map<String, String> rawParams = new HashMap<String, String>();
		rawParams.put("goodsCategoryId", String.valueOf(goodsCategoryId));
		rawParams.put("userId", userId);
		rawParams.put("searchWay", searchKey);
		rawParams.put("areaId", String.valueOf(areaId));
		rawParams.put("searchKey", searchKey);
		rawParams.put("sortWay", String.valueOf(sortWay));
		rawParams.put("pageNo", String.valueOf(pageNo));
		String result = HttpUtil.queryStringForPost(HttpUtil.GET_GOODS, rawParams);
		if (result==null) {
			handler.sendEmptyMessage(NETWORK_ERROR);
		} else {
			Map<String, Object> data = new HashMap<String, Object>();
			data = JsonUtil.getGoodsListByCus(result);
			Message msg = Message.obtain();
			msg.what = SUCCESS;
			msg.obj = data;
			handler.sendMessage(msg);
		}
		
		
		super.run();
	}
	
	
	
}
