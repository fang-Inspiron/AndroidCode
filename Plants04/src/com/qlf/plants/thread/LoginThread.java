package com.qlf.plants.thread;

import java.util.HashMap;
import java.util.Map;

import com.qlf.plants.utils.HttpUtils;
import com.qlf.plants.utils.JSONUtil;

import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

public class LoginThread extends Thread {

	public static final int NETWORK_ERROR = 234;
	public static final int SUCCESS = 222;

	private Handler handler;
	private String password;
	private String phone;

	public LoginThread() {
		// TODO Auto-generated constructor stub
	}

	public LoginThread(Handler handler, String password, String phone) {
		super();
		this.handler = handler;
		this.password = password;
		this.phone = phone;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();
		Map<String, String> rawParams = new HashMap<String, String>();
		rawParams.put("password", password + "");
		rawParams.put("phone", phone + "");
		String result = HttpUtils
				.queryStringForPost(HttpUtils.LOGIN, rawParams);
		if (result == null) {
			handler.sendEmptyMessage(NETWORK_ERROR);
		} else if (result == HttpUtils.NETWORK_ERROR) {
			handler.sendEmptyMessage(0x123);
		} else {
			Map<String, Object> data = JSONUtil.getLoginResult(result);
			Message msg = Message.obtain();
			msg.what = SUCCESS;
			msg.obj = data;
			handler.sendMessage(msg);
		}
	}
}
