package com.xrml.kuaican.net;

import java.util.HashMap;
import java.util.Map;

import com.xrml.kuaican.util.HttpUtils;
import com.xrml.kuaican.util.JSONUtil;

import android.os.Handler;
import android.os.Message;

public class NewAddressThread extends Thread {
	public static final String RESPONSE_OK="update_ok";
	public static final String RESPONSE_DATEBASE_ERROR="C732B67F7AD59A691E15B328A501508D";
	public static final String RESPONSE_ERROR="update_error";
	
	public static final int NETWORK_ERROR = 0;
	public static final int SUCCESS = 1;
	
	Handler handler;
	String orderPhone,userName,orderAddress,userId;
	
	public NewAddressThread(Handler handler,String orderPhone,String userName,String orderAddress, String userId ){
		this.handler=handler;
		this.orderPhone=orderPhone;
		this.userName=userName;
		this.orderAddress=orderAddress;
		this.userId=userId;
	}
	@Override
	public void run() {
		Map<String, String> rawParams = new HashMap<String, String>();
		rawParams.put("orderPhone", orderPhone);
		rawParams.put("userName", userName);
		rawParams.put("orderAddress", orderAddress);
		rawParams.put("userId", userId);
		
		String result = HttpUtils.queryStringForPost(
				HttpUtils.NEW_ADDRESS, rawParams);
		if (result == null) {
			handler.sendEmptyMessage(NETWORK_ERROR);
		} else {
			Map<String, Object> data = JSONUtil
					.newAddressResult(result);
			Message msg = Message.obtain();
			msg.what = SUCCESS;
			msg.obj = data;
			handler.sendMessage(msg);
		}
		
	}

}
