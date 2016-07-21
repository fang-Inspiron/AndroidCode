package com.qlf.plants.thread;


import java.util.HashMap;
import java.util.Map;

import com.qlf.plants.utils.HttpUtils;
import com.qlf.plants.utils.JSONUtil;

import android.os.Handler;
import android.os.Message;

public class AddTerminalThread extends Thread {

	public static final int NETWORK_ERROR = 234;
	public static final int SUCCESS = 222;

	private Handler handler;
	private int terminalId;
	private String terminalName;
	private String address;
	private String sim;
	private String certificate;
	private int userId;

	public AddTerminalThread() {
		// TODO Auto-generated constructor stub
	}

	public AddTerminalThread(Handler handler, int terminalId,
			String terminalName, String address, String sim, int userId,String certificate) {
		super();
		this.handler = handler;
		this.terminalId = terminalId;
		this.terminalName = terminalName;
		this.address = address;
		this.sim = sim;
		this.userId = userId;
		this.certificate = certificate;
	}


	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();
		Map<String, String> rawParams = new HashMap<String, String>();
		rawParams.put("userId", userId + "");
		rawParams.put("terminalName", terminalName + "");
		rawParams.put("address", address + "");
		rawParams.put("sim", sim + "");
		rawParams.put("certificate", certificate + "");
		String result = HttpUtils
				.queryStringForPost(HttpUtils.ADD_TERMINAL, rawParams);
		System.out.println(result);
		if (result == null) {
			handler.sendEmptyMessage(NETWORK_ERROR);
		} else {
			Map<String, Object> data = JSONUtil.getAddTerminalResult(result);
			Message msg = Message.obtain();
			msg.what = SUCCESS;
			msg.obj = data;
			handler.sendMessage(msg);
		}
	}
}
