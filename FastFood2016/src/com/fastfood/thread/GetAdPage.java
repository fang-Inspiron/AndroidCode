package com.fastfood.thread;

import java.util.HashMap;
import java.util.Map;

import com.fastfood.utils.HttpUtil;
import com.fastfood.utils.JsonUtil;

import android.os.Handler;
import android.os.Message;

public class GetAdPage extends Thread {

	public static final int NETWORK_ERROR = 120;
	public static final int SUCCESS = 121;
	
	private Handler handler;
	private int areaId;
	
	public GetAdPage(Handler handler, int areaId) {
		super();
		this.handler = handler;
		this.areaId = areaId;
	}
	
	@Override
	public void run() {
		Map<String, String> rawParams = new HashMap<String, String>();
		rawParams.put("areaId", String.valueOf(areaId));
		String result = HttpUtil.queryStringForPost(HttpUtil.GET_ADPAGE, rawParams);
		if (result==null) {
			handler.sendEmptyMessage(NETWORK_ERROR);
		} else {
			Map<String, Object> data = JsonUtil.getAdPageResult(result);
			Message msg = Message.obtain();
			msg.what = SUCCESS;
			msg.obj = data;
			handler.sendMessage(msg);
		}
	}
	
}
