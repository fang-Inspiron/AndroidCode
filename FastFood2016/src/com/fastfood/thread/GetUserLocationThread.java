package com.fastfood.thread;

import java.util.HashMap;
import java.util.Map;

import com.fastfood.utils.HttpUtil;
import com.fastfood.utils.JsonUtil;

import android.os.Handler;
import android.os.Message;

public class GetUserLocationThread extends Thread {

	public static final int NETWORK_ERROR = 500;
	public static final int SUCCESS = 501;
	
	private String cityName;
	private int pageNo;
	private Handler handler;
	
	public GetUserLocationThread(String cityName, int pageNo, Handler handler) {
		super();
		this.cityName = cityName;
		this.pageNo = pageNo;
		this.handler = handler;
	}
	
	@Override
	public void run() {
		Map<String, String> rawParams = new HashMap<String, String>();
		rawParams.put("cityName", cityName);
		rawParams.put("pageNo", String.valueOf(pageNo));
		String result = HttpUtil.queryStringForPost(HttpUtil.SEARCH_LOCATION, rawParams);
		if (result==null) {
			handler.sendEmptyMessage(NETWORK_ERROR);
		} else {
			Map<String, Object> data = JsonUtil.getUserLocation(result);
			Message msg = Message.obtain();
			msg.what = SUCCESS;
			msg.obj = data;
			handler.sendMessage(msg);
		}
		super.run();
	}
	
}
