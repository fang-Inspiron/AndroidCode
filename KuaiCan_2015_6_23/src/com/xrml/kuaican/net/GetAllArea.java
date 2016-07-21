package com.xrml.kuaican.net;

import java.util.HashMap;
import java.util.Map;

import android.os.Handler;
import android.os.Message;

import com.xrml.kuaican.util.HttpUtils;
import com.xrml.kuaican.util.JSONUtil;

public class GetAllArea extends Thread{
	public static final int NETWORK_ERROR = 500;
	public static final int SUCCESS = 501;

	private Handler handler;
	private String cityName;
	private String pageNo;
	

	public GetAllArea(Handler handler, String cityName, String pageNo) {
		super();
		this.handler = handler;
		this.cityName = cityName;
		this.pageNo = pageNo;
	}


	@Override
	public void run() {
		Map<String, String> rawParams = new HashMap<String, String>();
		rawParams.put("pageNo", pageNo);
		rawParams.put("cityName", cityName);
		String result = HttpUtils.queryStringForPost(HttpUtils.FIND_ALL_AREA,
				rawParams);
		if (result == null) {
			handler.sendEmptyMessage(NETWORK_ERROR);
		} else {
			Map<String, Object> data = JSONUtil.getAllArea(result);
			Message msg = Message.obtain();
			msg.what = SUCCESS;
			msg.obj = data;
			handler.sendMessage(msg);
		}

	}
}
