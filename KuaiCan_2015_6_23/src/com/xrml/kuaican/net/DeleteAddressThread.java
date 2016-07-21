package com.xrml.kuaican.net;

import java.util.HashMap;
import java.util.Map;

import com.xrml.kuaican.util.HttpUtils;
import com.xrml.kuaican.util.JSONUtil;

import android.os.Handler;
import android.os.Message;


public class DeleteAddressThread extends Thread{
	
	public static final int NETWORK_ERROR = 170;
	public static final int SUCCESS = 171;
	
	private Handler handler;
	private String receiveId;
	
	public DeleteAddressThread(Handler handler, String receiveId) {
		super();
		this.handler = handler;
		this.receiveId = receiveId;
	}

	@Override
	public void run() {
		Map<String, String> rawParams = new HashMap<String, String>();
		rawParams.put("receiveId", receiveId);
		String result = HttpUtils.queryStringForPost(HttpUtils.DELETE_USER_ADDRESS, rawParams);
		if(result == null){
			handler.sendEmptyMessage(NETWORK_ERROR);
		}else{
			Map<String, Object> data = JSONUtil.getDeletedUserAddress(result);
			Message msg = Message.obtain();
			msg.what = SUCCESS;
			msg.obj = data;
			handler.sendMessage(msg);
		}
	}
}
