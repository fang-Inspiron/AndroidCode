package com.xrml.kuaican.net;

import java.util.HashMap;
import java.util.Map;

import com.xrml.kuaican.util.HttpUtils;
import com.xrml.kuaican.util.JSONUtil;

import android.os.Handler;
import android.os.Message;

public class GetAllCityThread extends Thread {

	public static final int NETWORK_ERROR = 400;
	public static final int SUCCESS = 401;

	private Handler handler;
	private String pageNo;

	public GetAllCityThread(Handler handler, String pageNo) {
		super();
		this.handler = handler;
		this.pageNo = pageNo;
	}

	@Override
	public void run() {
		Map<String, String> rawParams = new HashMap<String, String>();
		rawParams.put("pageNo", pageNo);
		String result = HttpUtils.queryStringForPost(HttpUtils.FIND_ALL_CITY,
				rawParams);
		if (result == null) {
			handler.sendEmptyMessage(NETWORK_ERROR);
		} else {
			Map<String, Object> data = JSONUtil.getAllCity(result);
			Message msg = Message.obtain();
			msg.what = SUCCESS;
			msg.obj = data;
			handler.sendMessage(msg);
		}

	}
}
