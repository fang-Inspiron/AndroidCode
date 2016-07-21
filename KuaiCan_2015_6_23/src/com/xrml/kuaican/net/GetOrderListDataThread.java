package com.xrml.kuaican.net;

import java.util.HashMap;
import java.util.Map;

import com.xrml.kuaican.util.HttpUtils;
import com.xrml.kuaican.util.JSONUtil;

import android.os.Handler;
import android.os.Message;

public class GetOrderListDataThread extends Thread {
	public static final int NETWORK_ERROR = 100;
	public static final int SUCCESS = 101; 
	
	private Handler handler;
	private String userId;
	private String checkStr;
	private int orderState;
	private int pageNo;

	public GetOrderListDataThread(Handler handler, String userId,
			String checkStr, int orderState, int pageNo) {
		super();
		this.handler = handler;
		this.userId = userId;
		this.checkStr = checkStr;
		this.orderState = orderState;
		this.pageNo = pageNo;
	}

	@Override
	public void run() {
		Map<String, String> rawParams = new HashMap<String, String>();
		rawParams.put("userId", userId);
		rawParams.put("orderState", orderState + "");
		rawParams.put("pageNo", pageNo + "");
		rawParams.put("USER418C5509E2171D55B0AEE5C2EA4442B5", checkStr);
		String result = HttpUtils.queryStringForPost(HttpUtils.GEt_ORDER_LIST,
				rawParams);
		if(result == null){
			handler.sendEmptyMessage(NETWORK_ERROR);
		}else{
			Map<String, Object> data = JSONUtil.getOrderListResult(result);
			Message msg = Message.obtain();
			msg.what = SUCCESS;
			msg.obj = data;
			handler.sendMessage(msg);
		}
	}
}
