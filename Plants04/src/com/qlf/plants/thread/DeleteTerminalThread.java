package com.qlf.plants.thread;

import java.util.HashMap;
import java.util.Map;

import com.qlf.plants.utils.HttpUtils;
import com.qlf.plants.utils.JSONUtil;

import android.os.Handler;
import android.os.Message;

public class DeleteTerminalThread extends Thread {

	public static final int NETWORK_ERROR = 234;
	public static final int SUCCESS = 222;
	public static final int DELETE_SUCCESS = 223;
	

	private Handler handler;
	private int terminalId;
	private String certificate;

	public DeleteTerminalThread() {
		// TODO Auto-generated constructor stub
	}

	public DeleteTerminalThread(Handler handler, int terminalId,String certificate) {
		super();
		this.handler = handler;
		this.terminalId = terminalId;
		this.certificate = certificate;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();
		String url = HttpUtils.ADD_TERMINAL+terminalId+"/delete?certificate="+certificate;
		String result = HttpUtils
				.queryStringForGet(url);
		if (result == null) {
			handler.sendEmptyMessage(NETWORK_ERROR);
		} else if (result == HttpUtils.NETWORK_ERROR) {
			handler.sendEmptyMessage(0x123);
		} else  {
			Map<String, Object> data = JSONUtil.getRegisterResult(result);
			Message msg = Message.obtain();
			msg.what = DELETE_SUCCESS;
			msg.obj = data;
			handler.sendMessage(msg);
		}
	}
}
