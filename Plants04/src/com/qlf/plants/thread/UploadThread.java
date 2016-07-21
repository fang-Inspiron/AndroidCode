package com.qlf.plants.thread;

import java.util.HashMap;
import java.util.Map;

import com.qlf.plants.utils.HttpUtils;
import com.qlf.plants.utils.JSONUtil;


import android.os.Handler;
import android.os.Message;

public class UploadThread extends Thread{

	public static final int NETWORK_ERROR = 234;
	public static final int SUCCESS = 222;
	
	private Handler handler;
	private String userId;
	private String userImg;
	private String certificate;
	
	public UploadThread() {
		// TODO Auto-generated constructor stub
	}
	
	
	



	public UploadThread(Handler handler, String userId, String userImg,String certificate) {
		super();
		this.handler = handler;
		this.userId = userId;
		this.userImg = userImg;
		this.certificate = certificate;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();
		Map<String,String> rawParams = new HashMap<String,String>();
		rawParams.put("userId", userId+"");
		rawParams.put("userImg",userImg+"");
	//	rawParams.put("certificate",certificate+"");
		String result = HttpUtils.queryStringForPost(HttpUtils.UPLOAD_HEAD, rawParams);
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
