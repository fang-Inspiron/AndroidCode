package com.xrml.kuaican.net;

import java.util.HashMap;
import java.util.Map;

import android.os.Handler;
import android.os.Message;

import com.xrml.kuaican.util.HttpUtils;
import com.xrml.kuaican.util.JSONUtil;

public class SearchGoodsThread extends Thread {

	private Handler handler;
	private String mAreaID;
	private String mSearchWay;
	private String mSearchKey;

	public static final int NETWORK_ERROR = 7000;
	public static final int SUCCESS = 7001;
	public static final int NO_GOODS = 7003;

	public SearchGoodsThread(Handler handler, String mAreaID,
			String mSearchWay, String mSearchKey) {
		super();
		this.handler = handler;
		this.mAreaID = mAreaID;
		this.mSearchWay = mSearchWay;
		this.mSearchKey = mSearchKey;
	}

	@Override
	public void run() {
		Map<String, String> rawParams = new HashMap<String, String>();
		rawParams.put("areaId", mAreaID);
		rawParams.put("searchWay", mSearchWay);
		rawParams.put("searchKey", mSearchKey);
		System.out.println("goodsCategoryId--> " + mAreaID + "\nsearchWay--> "
				+ mSearchWay + "\nsearchKey--> " + mSearchKey);
		String result = HttpUtils.queryStringForPost(HttpUtils.GET_GOODS_LIST,
				rawParams);
		if (result == null) {
			handler.sendEmptyMessage(NETWORK_ERROR);
		} else  if(result.length() < 30){
			Message msg = Message.obtain();
			msg.what = NO_GOODS;
			handler.sendMessage(msg);
		}else{
			Map<String,Object> data = JSONUtil.getGoodsListByCus(result);
			Message msg = Message.obtain();
			msg.what = SUCCESS;
			msg.obj = data;
			handler.sendMessage(msg);
		}
	}
}
