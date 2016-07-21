package com.xrml.kuaican.net;

import java.util.HashMap;
import java.util.Map;

import com.xrml.kuaican.util.HttpUtils;
import com.xrml.kuaican.util.JSONUtil;

import android.os.Handler;
import android.os.Message;

public class GetCodeThread extends Thread{
	private final static String PHONE_NUMBER = "USERC3B9D6D48C7DFAC5FC1A4EF32DDD2EC2";
	
	private String phoneNumber;
	private Handler handler;
	
	public static final int NETWORK_ERROR = 100;
	public static final int SUCCESS = 101;	
	
	public GetCodeThread(Handler handler,String phoneNumber){
		this.handler = handler;
		this.phoneNumber = phoneNumber;
	}
	@Override
	public void run() {
		Map<String, String> rawParams = new HashMap<String, String>();
		rawParams.put(PHONE_NUMBER, phoneNumber);
		String result = HttpUtils.queryStringForPost(HttpUtils.GET_CODE, rawParams);
		if(result == null){
			handler.sendEmptyMessage(NETWORK_ERROR);
		}else{
			Map<String, Object> data = JSONUtil.getCode(result);
			Message msg = Message.obtain();
			msg.what = SUCCESS;
			msg.obj = data.get("response");
			handler.sendMessage(msg);
		}
	}
}
