package com.xrml.kuaican.net;

import java.util.HashMap;
import java.util.Map;

import com.xrml.kuaican.util.HttpUtils;
import com.xrml.kuaican.util.JSONUtil;

import android.os.Handler;
import android.os.Message;

public class SendSuggessedThread extends Thread {
	
	public static final int NETWORK_ERROR = 190;
	public static final int SUCCESS = 191;
	
	private Handler handler;
	private String userPhone;
	private String suggestionStr;

	public SendSuggessedThread(Handler handler, String userPhone,
			String suggestionStr) {
		super();
		this.handler = handler;
		this.userPhone = userPhone;
		this.suggestionStr = suggestionStr;
	}

	@Override
	public void run() {
		Map<String, String> rawParams = new HashMap<String, String>();
		rawParams.put("userPhone", userPhone);
		rawParams.put("suggestionStr", suggestionStr);
		String result = HttpUtils.queryStringForPost(HttpUtils.USER_SUGGESSED,
				rawParams);
		if(result == null){
			handler.sendEmptyMessage(NETWORK_ERROR);
		}else{
			Map<String, Object> data = JSONUtil.getSuggessed(result);
			Message msg = Message.obtain();
			msg.what = SUCCESS;
			msg.obj = data;
			handler.sendMessage(msg);
		}
	}
}
