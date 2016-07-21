package com.qlf.plants.thread;


import java.util.HashMap;
import java.util.Map;

import com.qlf.plants.utils.HttpUtils;
import com.qlf.plants.utils.JSONUtil;

import android.os.Handler;
import android.os.Message;

public class BUGTerminalThread extends Thread {

	public static final int NETWORK_ERROR = 234;
	public static final int SUCCESS = 222;

	private Handler handler;
	private int terminalId;
	private int type;
	private int time;
	private String certificate;
	public BUGTerminalThread() {
		// TODO Auto-generated constructor stub
	}


	public BUGTerminalThread(Handler handler, int terminalId, int type, int time,String certificate) {
		super();
		this.handler = handler;
		this.terminalId = terminalId;
		this.type = type;
		this.time = time;
		this.certificate = certificate;
	}




	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();
		Map<String, String> rawParams = new HashMap<String, String>();
		rawParams.put("certificate", certificate + "");
		
		String result = HttpUtils
				.queryStringForPost(HttpUtils.BU+""+terminalId+"/operate/"+type+"/"+time,null);
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
