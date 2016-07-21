package com.xrml.kuaican.net;

import java.util.HashMap;
import java.util.Map;

import com.xrml.kuaican.util.HttpUtils;
import com.xrml.kuaican.util.JSONUtil;

import android.os.Handler;
import android.os.Message;

/**
 * @author Administrator
 *	�õ����ͼƬ
 */
public class GetAdPageByCus extends Thread {
	public static final int NETWORK_ERROR = 100;
	public static final int SUCCESS = 101;
	private Handler handler;
	private int areaId;

	public GetAdPageByCus(Handler handler,int areaId) {
		super();
		this.handler = handler;
		this.areaId = areaId;
	}

	@Override
	public void run() {
		Map<String, String> rawParams = new HashMap<String, String>();
		rawParams.put("areaId", areaId + "");
		String result = HttpUtils.queryStringForPost(HttpUtils.GET_ADPAGE,
				rawParams);
		if(result == null){
			handler.sendEmptyMessage(NETWORK_ERROR);
		}else {
			Map<String,Object> data = JSONUtil.getAdPageResult(result);
			Message msg = Message.obtain();
			msg.what = SUCCESS;
			msg.obj = data;
			handler.sendMessage(msg);
		}
	}
}