package com.qlf.plants.thread;

import java.util.HashMap;
import java.util.Map;

import com.qlf.plants.utils.HttpUtils;
import com.qlf.plants.utils.JSONUtil;

import android.os.Handler;
import android.os.Message;

public class GetTerminalThread extends Thread {

	public static final int NETWORK_ERROR = 234;
	public static final int SUCCESS = 222;

	private Handler handler;
	private int userId;
	private String certificate;

	public GetTerminalThread() {
		// TODO Auto-generated constructor stub
	}

	public GetTerminalThread(Handler handler, int userId,String certificate) {
		super();
		this.handler = handler;
		this.userId = userId;
		this.certificate = certificate;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();
		String url = HttpUtils.ADD_TERMINAL+"/"+userId+"/list?certificate="+certificate;
		String result = HttpUtils
				.queryStringForGet(url);
		System.out.println(result);
		if (result == null) {
			handler.sendEmptyMessage(NETWORK_ERROR);
		} else if (result == HttpUtils.NETWORK_ERROR) {
			handler.sendEmptyMessage(0x123);
		} else  {
			Map<String, Object> data = JSONUtil.getTerminalList(result);
			Message msg = Message.obtain();
			msg.what = SUCCESS;
			msg.obj = data;
			handler.sendMessage(msg);
		}
	}
}
