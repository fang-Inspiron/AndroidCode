package com.qlf.plants.thread;

import java.util.HashMap;
import java.util.Map;

import com.qlf.plants.utils.HttpUtils;
import com.qlf.plants.utils.JSONUtil;


import android.os.Handler;
import android.os.Message;

public class RegisterThread extends Thread{

	public static final int NETWORK_ERROR = 234;
	public static final int SUCCESS = 222;
	
	private Handler handler;
	private String username;
	private String password;
	private String email;
	private String phone;
	
	public RegisterThread() {
		// TODO Auto-generated constructor stub
	}
	
	
	
	public RegisterThread(Handler handler, String username, String password,
			String email, String phone) {
		super();
		this.handler = handler;
		this.username = username;
		this.password = password;
		this.email = email;
		this.phone = phone;
	}



	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();
		Map<String,String> rawParams = new HashMap<String,String>();
		rawParams.put("userName", username+"");
		rawParams.put("email", email+"");
		rawParams.put("phone", phone+"");
		rawParams.put("password", password+"");
		rawParams.put("userImg", "");
		String result = HttpUtils.queryStringForPost(HttpUtils.REGISTER, rawParams);
		if(result == null){
			handler.sendEmptyMessage(NETWORK_ERROR);
		}else{
			Map<String,Object> data = JSONUtil.getRegisterResult(result);
			Message msg = Message.obtain();
			msg.what = SUCCESS;
			msg.obj = data;
			handler.sendMessage(msg);
		}
	}
}
