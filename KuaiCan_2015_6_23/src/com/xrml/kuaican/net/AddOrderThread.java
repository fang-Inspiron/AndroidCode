package com.xrml.kuaican.net;

import java.util.HashMap;
import java.util.Map;

import com.xrml.kuaican.util.HttpUtils;
import com.xrml.kuaican.util.JSONUtil;

import android.os.Handler;
import android.os.Message;

public class AddOrderThread extends Thread {

	public static final int NETWORK_ERROR = 900;
	public static final int SUCCESS = 901;
	private static final String CHECK_STR = "USER418C5509E2171D55B0AEE5C2EA4442B5";
	private Handler handler;
	private String userId;
	private String goodsCategoryId;
	private String goodsName;
	private String goodsPrice;
	private String goodsContent;
	private String goodsCount;
	@SuppressWarnings("unused")
	private String image;
	private String keyWords;
	private String checkStr;

	public AddOrderThread(Handler handler, String userId,
			String goodsCategoryId, String goodsName, String goodsPrice,
			String goodsContent, String goodsCount, String keyWords,
			String checkStr) {
		super();
		this.handler = handler;
		this.userId = userId;
		this.goodsCategoryId = goodsCategoryId;
		this.goodsName = goodsName;
		this.goodsPrice = goodsPrice;
		this.goodsContent = goodsContent;
		this.goodsCount = goodsCount;
		this.keyWords = keyWords;
		this.checkStr = checkStr;
	}

	@Override
	public void run() {
		Map<String, String> rawParams = new HashMap<String, String>();
		rawParams.put("userId", userId);
		rawParams.put("goodsCategoryId", goodsCategoryId);
		rawParams.put("goodsName", goodsName);
		rawParams.put("goodsPrice", goodsPrice);
		rawParams.put("goodsContent", goodsContent);
		rawParams.put("goodsCount", goodsCount);
		rawParams.put("keyWords", keyWords);
		rawParams.put(CHECK_STR, checkStr);
		String result = HttpUtils.queryStringForPost(HttpUtils.ADD_ORDER,
				rawParams);
		if (result == null) {
			handler.sendEmptyMessage(NETWORK_ERROR);
		} else {
			Map<String, Object> data = JSONUtil.getResponseResult(result);
			Message msg = Message.obtain();
			msg.obj = data;
			msg.what = SUCCESS;
			handler.sendMessage(msg);
		}
	}
}
