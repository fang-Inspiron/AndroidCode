package com.xrml.kuaican.net;

import java.util.HashMap;
import java.util.Map;

import com.xrml.kuaican.util.HttpUtils;
import com.xrml.kuaican.util.JSONUtil;

import android.os.Handler;
import android.os.Message;

public class GetUserLoginThread extends Thread {

	private Handler handler;
	private String userPhongNumber;
	private String userPassWord;

	private final static String USER_PHONENUMBER = "USERC3B9D6D48C7DFAC5FC1A4EF32DDD2EC2";
	private final static String USER_PASSWORD = "USER6D8DA6B0C968B9A6BDC35FA7E62367B0";

	public static final int NETWORK_ERROR = 300;
	public static final int SUCCESS = 301;
//	public static final int FAILURE = 302;

	public GetUserLoginThread(Handler handler, String userPhongNumber,
			String userPassWord) {
		super();
		this.handler = handler;
		this.userPhongNumber = userPhongNumber;
		this.userPassWord = userPassWord;
	}

	@Override
	public void run() {
		Map<String, String> rawParams = new HashMap<String, String>();
		rawParams.put(USER_PHONENUMBER, userPhongNumber);
		rawParams.put(USER_PASSWORD, userPassWord);
		String result = HttpUtils.queryStringForPost(HttpUtils.USER_LOGIN,
				rawParams);
		if (result == null) {
			handler.sendEmptyMessage(NETWORK_ERROR);
		} else {
			Map<String, Object> data = new HashMap<String, Object>();
			data = JSONUtil.getLoginResult(result);
			Message msg = Message.obtain();
			msg.what = SUCCESS;
			msg.obj = data;
			handler.sendMessage(msg);
		}
	}
}
