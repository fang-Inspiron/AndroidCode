package com.xrml.kuaican.net;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;

import android.os.Handler;
import android.os.Message;

import com.xrml.kuaican.util.HttpUtils;
import com.xrml.kuaican.util.JSONUtil;

public class ShoppingCartThread extends Thread {

	private Handler handler;
	@SuppressWarnings("unused")
	private int areaId;
	private List<Map<String, Object>> goodsInfo;
	// 提交成功
	public static final String SUBMIT_OK = "24D340D7072786998EE2BC4532B1834A";
	// 对不起，有些商品不能配送
	public static final String SOMEGOODSNOT_NOT = "A5D1CDD1906AB3EBA74F4CE1577F66B7";
	// 请提前半小时预定
	public static final String PLEASEEARLY_NOT = "CD49CE843BDB2E7DED123FE1775A783D";
	// 登录过期，请重试
	public static final String LOGIN_NOT = "USER418C5509E2171D55B0AEE5C2EA4442B5";
	// 网络错误，请重试
	// public static final String NETWORK_ERROR =
	// "C732B67F7AD59A691E15B328A501508D";
	public static final int NETWORK_ERROR = 0;
	public static final int SUCCESS = 1;

	public ShoppingCartThread(Handler handler, int areaId,
			List<Map<String, Object>> ls) {
		this.handler = handler;
		this.areaId = areaId;
		this.goodsInfo = ls;
	}

	@Override
	public void run() {
		Map<String, String> rawParams = new HashMap<String, String>();
		JSONArray jsonArray = new JSONArray(goodsInfo);
		for (int i = 0; i < jsonArray.length(); i++) {
		}
		String strb = "";
		if (goodsInfo.size() > 1) {
			int i;
			strb += "[";
			for (i = 0; i < goodsInfo.size() - 1; i++) {

				strb += "{" + "'goodsId':" + goodsInfo.get(i).get("goodsId")
						+ "," + "'goodsCount':"
						+ goodsInfo.get(i).get("goodsCount") + "}" + ",";

			}
			strb += "{" + "'goodsId':" + goodsInfo.get(i).get("goodsId") + ","
					+ "'goodsCount':" + goodsInfo.get(i).get("goodsCount")
					+ "}" + "]";
		} else 
			strb = "[" + "{" + "'goodsId':" + goodsInfo.get(0).get("goodsId")
					+ "," + "'goodsCount':"
					+ goodsInfo.get(0).get("goodsCount") + "}" + "]";
		rawParams.put("cartListStr", strb + "");
		String result = HttpUtils.queryStringForPost(HttpUtils.SEND_GOODS_CART,
				rawParams);
		if (result == null||result.equals("")) {
			System.out.println("ERROR");
			handler.sendEmptyMessage(NETWORK_ERROR);
		} else if (result == HttpUtils.NETWORK_ERROR) {
			System.out.println("ERROR");
			handler.sendEmptyMessage(NETWORK_ERROR);
		} else {
			Map<String, Object> data = JSONUtil.getDataShoppingCart(result);
			Message msg = Message.obtain();
			msg.what = SUCCESS;
			msg.obj = data;
			handler.sendMessage(msg);
		}
	}

}
