package com.fastfood.thread;

import java.util.HashMap;
import java.util.Map;

import com.fastfood.utils.HttpUtil;
import com.fastfood.utils.JsonUtil;

import android.os.Handler;
import android.os.Message;

public class LoginThread extends Thread {

	public  final static String USER_PHONENUMBER = "USERC3B9D6D48C7DFAC5FC1A4EF32DDD2EC2";
	public final static String USER_PASSWORD = "USER6D8DA6B0C968B9A6BDC35FA7E62367B0";
	public final static String RETURN_CHECK_CODE = "USER418C5509E2171D55B0AEE5C2EA4442B5";
	public final static String RETURN_CHECK_VALUE = "26223A5B90699DF51950E01FD5C10EAB";
	public final static String RETURN_USER_ID = "USER418C5509E2171D55B0AEE5C2EA4442B5";
	public final static String RETURN_USER_VALUE = "26223A5B90699DF51950E01FD5C10EAB";
	
	
	public static final int NETWORK_ERROR = 300;
	public static final int SUCCESS = 301;
	
	private Handler handler;
	private String userPhongNumber;
	private String userPassWord;
	
	public LoginThread(Handler handler, String userPhongNumber,
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
		String result = HttpUtil.queryStringForPost(HttpUtil.USER_LOGIN, rawParams);
		if (result==null) {
			handler.sendEmptyMessage(NETWORK_ERROR);
		} else {
			Map<String, Object> data = new HashMap<String, Object>();
			data = JsonUtil.getLoginResult(result);
			Message msg = Message.obtain();
			msg.what = SUCCESS;
			msg.obj = data;
			handler.sendMessage(msg);
		}
	}
}
