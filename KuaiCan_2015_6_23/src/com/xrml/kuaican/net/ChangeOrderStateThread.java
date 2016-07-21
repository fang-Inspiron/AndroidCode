package com.xrml.kuaican.net;

import java.util.HashMap;
import java.util.Map;

import com.xrml.kuaican.util.HttpUtils;
import com.xrml.kuaican.util.JSONUtil;

import android.os.Handler;
import android.os.Message;

public class ChangeOrderStateThread extends Thread {
	public static final String CHECK="USER418C5509E2171D55B0AEE5C2EA4442B5";
	
	public static final int NETWORK_ERROR = 110;
	public static final int SUCCESS = 111;
	
	public static final String RESPONSE_UPDATE_OK="update_ok";
	public static final String RESPONSE_UPDATE_ERROR="update_error";
	public static final String RESPONSE_CHECK_ERROR="F1F5FF7206C408B26AA1A2B63EDBAEF3";
	
	private Handler handler;
	private String orderId;
	private String orderState;
	private String checkStr;
	
	public ChangeOrderStateThread(Handler handler, String orderId,
			String orderState, String checkStr) {
		super();
		this.handler = handler;
		this.orderId = orderId;
		this.orderState = orderState;
		this.checkStr = checkStr;
	}

	@Override
	public void run() {
		Map<String, String> rawParams = new HashMap<String, String>();
		rawParams.put("orderId", orderId);
		rawParams.put("orderState", orderState);
		rawParams.put(CHECK, checkStr);
		String result = HttpUtils.queryStringForPost(
				HttpUtils.CHANGE_ORDER_STATE, rawParams);
		if (result == null) {
			handler.sendEmptyMessage(NETWORK_ERROR);
		} else {
			Map<String, Object> data = JSONUtil
					.changeOrderState(result);
			Message msg = Message.obtain();
			msg.what = SUCCESS;
			msg.obj = data;
			handler.sendMessage(msg);
		}
	}

}
