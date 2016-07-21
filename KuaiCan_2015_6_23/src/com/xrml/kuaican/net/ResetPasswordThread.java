package com.xrml.kuaican.net;

import java.util.HashMap;
import java.util.Map;

import com.xrml.kuaican.util.HttpUtils;
import com.xrml.kuaican.util.JSONUtil;

import android.os.Handler;
import android.os.Message;

public class ResetPasswordThread extends Thread{

	private final static String PHONE_NUMBER = "USERC3B9D6D48C7DFAC5FC1A4EF32DDD2EC2";
	
	public static final int NETWORK_ERROR = 200;
	public static final int SUCCESS = 201;	
	
	private Handler handler;
	private String phoneNumber;
	private String clientCode;
	
	public ResetPasswordThread(Handler handler, String phoneNumber,
			String clientCode) {
		super();
		this.handler = handler;
		this.phoneNumber = phoneNumber;
		this.clientCode = clientCode;
	}


	@Override
	public void run() {
		Map<String,String> rawParams = new HashMap<String, String>();
		rawParams.put(PHONE_NUMBER, phoneNumber);
		rawParams.put("clientCode", clientCode);
		String result = HttpUtils.queryStringForPost(HttpUtils.RESET_PSD, rawParams);
		if(result == null){
			handler.sendEmptyMessage(NETWORK_ERROR);
		}else{
			Map<String,Object> data = JSONUtil.getRegistResult(result);
			Message msg = Message.obtain();
			msg.what = SUCCESS;
			msg.obj = data.get("response").toString();
			handler.sendMessage(msg);
		}
	}
}
