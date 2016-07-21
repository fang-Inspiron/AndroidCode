package com.xrml.kuaican.net;

import java.util.HashMap;
import java.util.Map;

import android.os.Handler;
import android.os.Message;

import com.xrml.kuaican.util.HttpUtils;
import com.xrml.kuaican.util.JSONUtil;

public class GetUserAddressThread extends Thread{

	public static final int NETWORK_ERROR = 140;
	public static final int SUCCESS = 141;
	private String userId;
	private Handler handler;
	@Override
	
	public void run() {
		Map<String,String> rawParams = new HashMap<String,String>();
		rawParams.put("userId", userId);
		String result = HttpUtils.queryStringForPost(HttpUtils.LOOK_USER_ADDRESS, rawParams);
		if(result == null){
			handler.sendEmptyMessage(NETWORK_ERROR);
		}else{
			Map<String,Object> data = JSONUtil.getUserAddressData(result);
			Message msg = Message.obtain();
			msg.what = SUCCESS;
			msg.obj = data;
			handler.sendMessage(msg);
		}
	}
	public GetUserAddressThread(Handler handler ,String userId) {
		this.handler = handler;
		this.userId = userId;
	}
}
