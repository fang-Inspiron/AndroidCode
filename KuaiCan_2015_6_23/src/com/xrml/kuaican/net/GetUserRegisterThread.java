package com.xrml.kuaican.net;

import java.util.HashMap;
import java.util.Map;

import com.xrml.kuaican.util.HttpUtils;
import com.xrml.kuaican.util.JSONUtil;

import android.os.Handler;
import android.os.Message;

public class GetUserRegisterThread extends Thread{

	private final static String PHONE_NUMBER = "USERC3B9D6D48C7DFAC5FC1A4EF32DDD2EC2";
	private final static String USER_PASS = "USER6D8DA6B0C968B9A6BDC35FA7E62367B0";
	private final static String CHECK_CODE = "C13367945D5D4C91047B3B50234AA7AB";
	private final static String CHECK_STR = "USER418C5509E2171D55B0AEE5C2EA4442B5";
	
	public static final int NETWORK_ERROR = 200;
	public static final int SUCCESS = 201;	
	
	private Handler handler;
	private String phoneNumber;
	private String userPass;
	private String checkCode;
	private String checkStr;
	
	public GetUserRegisterThread(Handler handler, String phoneNumber,
			String userPass, String checkCode, String checkStr) {
		super();
		this.handler = handler;
		this.phoneNumber = phoneNumber;
		this.userPass = userPass;
		this.checkCode = checkCode;
		this.checkStr = checkStr;
	}


	@Override
	public void run() {
		Map<String,String> rawParams = new HashMap<String, String>();
		rawParams.put(PHONE_NUMBER, phoneNumber);
		rawParams.put(USER_PASS, userPass);
		rawParams.put(CHECK_CODE, checkCode);
		rawParams.put(CHECK_STR, checkStr);
		String result = HttpUtils.queryStringForPost(HttpUtils.USER_REGISTER, rawParams);
		if(result == null){
			handler.sendEmptyMessage(NETWORK_ERROR);
		}else{
			Map<String,Object> data = JSONUtil.getRegistResult(result);
			Message msg = Message.obtain();
			msg.what = SUCCESS;
			msg.obj = data.get("response");
			handler.sendMessage(msg);
		}
	}
}
