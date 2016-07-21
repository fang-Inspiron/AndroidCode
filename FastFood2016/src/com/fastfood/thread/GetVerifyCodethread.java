package com.fastfood.thread;

import java.util.HashMap;
import java.util.Map;

import com.fastfood.utils.HttpUtil;
import com.fastfood.utils.JsonUtil;

import android.os.Handler;
import android.os.Message;

public class GetVerifyCodethread extends Thread {
	
	private final static String PHONE_NUMBER = "USERC3B9D6D48C7DFAC5FC1A4EF32DDD2EC2";
	
	private String phoneNumber;
	private Handler handler;
	
	public static final int NETWORK_ERROR = 100;
	public static final int SUCCESS = 101;
	
	public GetVerifyCodethread(Handler handler,String phoneNumber) {
		this.handler = handler;
		this.phoneNumber = phoneNumber;
	}
	
	@Override
	public void run() {
		Map<String, String> rawParams = new HashMap<String, String>();
		rawParams.put(PHONE_NUMBER, phoneNumber);
		String result = HttpUtil.queryStringForPost(HttpUtil.VERIFICATION_CODE, rawParams);
		if (result==null) {
			handler.sendEmptyMessage(NETWORK_ERROR);
		} else {
			Map<String, Object> data = JsonUtil.getCode(result);
			Message msg = Message.obtain();
			msg.what = SUCCESS;
			msg.obj = data;
			handler.sendMessage(msg);
		}
	}
	
}
