package com.xrml.kuaican.net;

import java.util.HashMap;
import java.util.Map;

import com.xrml.kuaican.util.HttpUtils;
import com.xrml.kuaican.util.JSONUtil;

import android.os.Handler;
import android.os.Message;

public class ChangeAddressThread extends Thread{
	
	public static final int NETWORK_ERROR = 160;
	public static final int SUCCESS = 161;
	
	private Handler handler;
	private String orderPhone;
	private String userName;
	private String orderAddress;
	private String receiveId;
	
	public ChangeAddressThread(Handler handler, String orderPhone,
			String userName, String orderAddress, String receiveId) {
		super();
		this.handler = handler;
		this.orderPhone = orderPhone;
		this.userName = userName;
		this.orderAddress = orderAddress;
		this.receiveId = receiveId;
	}

	@Override
	public void run() {
		Map<String, String> rawParams = new HashMap<String, String>();
		rawParams.put("orderPhone", orderPhone);
		rawParams.put("userName", userName);
		rawParams.put("orderAddress", orderAddress);
		rawParams.put("receiveId", receiveId);
		String result = HttpUtils.queryStringForPost(HttpUtils.CHANGE_USER_ADDRESS, rawParams);
		if(result == null){
			handler.sendEmptyMessage(NETWORK_ERROR);
		}else{
			Map<String, Object> data = JSONUtil.getChangeUserAddress(result);
			Message msg = Message.obtain();
			msg.what = SUCCESS;
			msg.obj = data;
			handler.sendMessage(msg);
		}
	}
}
